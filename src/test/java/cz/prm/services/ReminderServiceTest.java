package cz.prm.services;

import static cz.prm.utils.ReminderUtils.reminder;
import static cz.prm.utils.ReminderUtils.reminders;
import static cz.prm.utils.assertions.ReminderAssertions.assertReminder;
import static cz.prm.utils.assertions.ReminderAssertions.assertReminders;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.querydsl.core.BooleanBuilder;
import cz.prm.domain.reminder.Reminder;
import cz.prm.repositories.reminder.ReminderPredicates;
import cz.prm.repositories.reminder.ReminderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReminderServiceTest {

    @Mock
    private ReminderRepository repository;
    @Mock
    private ReminderPredicates predicates;
    @Captor
    private ArgumentCaptor<Reminder> reminderCapt;

    private ReminderService reminderService;

    @BeforeEach
    void setUp() {
        reminderService = new ReminderService(repository, predicates);
    }

    @Test
    void getReminders() {
        var reminders = reminders();
        var predicate = new BooleanBuilder();

        when(predicates.reminders()).thenReturn(predicate);
        when(repository.findAll(eq(predicate))).thenReturn(reminders);
        var result = reminderService.getReminders();
        assertReminders(result, reminders);
    }

    @Test
    void getReminder() {
        var reminder = reminder();
        var predicate = new BooleanBuilder();
        when(predicates.byReminderId(reminder.getReminderId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(reminder));
        var result = reminderService.getReminder(reminder.getReminderId());
        assertReminder(result, reminder);
    }

    @Test
    void getReminder_EntityNotFound() {
        var reminder = reminder();
        var predicate = new BooleanBuilder();
        when(predicates.byReminderId(reminder.getReminderId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> reminderService.getReminder(reminder.getReminderId()));
    }

    @Test
    void createReminder() {
        var reminder = reminder();
        reminderService.createReminder(reminder);
        verify(repository).save(reminder);
    }

    @Test
    void updateReminder() {
        var reminderIdDb = reminder();
        var updatedReminder = reminder();
        var predicate = new BooleanBuilder();
        when(predicates.byReminderId(reminderIdDb.getReminderId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(reminderIdDb));

        reminderService.updateReminder(reminderIdDb.getReminderId(), updatedReminder);
        verify(repository).save(reminderCapt.capture());
        var savedReminder = reminderCapt.getValue();
        assertFieldsUpdated(reminderIdDb, updatedReminder, savedReminder);
    }

    @Test
    void updateReminder_EntityNotFound() {
        var reminderIdDb = reminder();
        var updatedReminder = reminder();
        var predicate = new BooleanBuilder();
        when(predicates.byReminderId(reminderIdDb.getReminderId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> reminderService.updateReminder(reminderIdDb.getReminderId(), updatedReminder));
    }

    @Test
    void deleteReminder() {
        var reminderIdDb = reminder();
        var reminderId = reminderIdDb.getReminderId();
        var predicate = new BooleanBuilder();
        when(predicates.byReminderId(reminderId)).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(reminderIdDb));

        reminderService.deleteReminder(reminderId);
        verify(repository).deleteById(reminderId);
    }

    @Test
    void deleteReminder_EntityNotFound() {
        var reminderIdDb = reminder();
        var reminderId = reminderIdDb.getReminderId();
        var predicate = new BooleanBuilder();
        when(predicates.byReminderId(reminderId)).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> reminderService.deleteReminder(reminderId));
    }

    private void assertFieldsUpdated(Reminder reminderIdDb, Reminder updatedReminder, Reminder savedReminder) {
        assertThat(reminderIdDb.getReminderId()).isEqualTo(savedReminder.getReminderId());
        assertThat(reminderIdDb.getRecurrence()).isEqualTo(updatedReminder.getRecurrence());
    }
}