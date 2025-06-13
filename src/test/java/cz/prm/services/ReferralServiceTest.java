package cz.prm.services;

import static cz.prm.utils.ReminderUtils.reminder;
import static cz.prm.utils.ReminderUtils.reminders;
import static cz.prm.utils.ReminderUtils.remindersQuery;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.assertions.ReminderAssertions.assertPage;
import static cz.prm.utils.assertions.ReminderAssertions.assertReminder;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.querydsl.core.BooleanBuilder;
import cz.prm.domain.referral.Referral;
import cz.prm.repositories.referral.ReferralPredicates;
import cz.prm.repositories.referral.ReferralRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class ReferralServiceTest {

    @Mock
    private ReferralRepository repository;
    @Mock
    private ReferralPredicates predicates;
    @Captor
    private ArgumentCaptor<Referral> reminderCapt;

    private ReferralService referralService;

    @BeforeEach
    void setUp() {
        referralService = new ReferralService(repository, predicates);
    }

    @Test
    void getReminders() {
        var reminders = reminders();
        var page = new PageImpl(reminders);
        var query = remindersQuery();
        var contactId = randomLong();
        var predicate = new BooleanBuilder();

        when(predicates.byContactId(contactId)).thenReturn(predicate);
        when(repository.findAll(eq(predicate), any(PageRequest.class))).thenReturn(page);
        var result = referralService.getReminders(contactId, query);
        assertPage(result, page);
    }

    @Test
    void getReminder() {
        var reminder = reminder();
        var predicate = new BooleanBuilder();
        when(predicates.byReminderId(reminder.getReminderId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(reminder));
        var result = referralService.getReminder(reminder.getReminderId());
        assertReminder(result, reminder);
    }

    @Test
    void getReminder_EntityNotFound() {
        var reminder = reminder();
        var predicate = new BooleanBuilder();
        when(predicates.byReminderId(reminder.getReminderId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> referralService.getReminder(reminder.getReminderId()));
    }

    @Test
    void createReminder() {
        var reminder = reminder();
        referralService.createReminder(reminder);
        verify(repository).save(reminder);
    }

    @Test
    void updateReminder() {
        var reminderIdDb = reminder();
        var updatedReminder = reminder();
        var predicate = new BooleanBuilder();
        when(predicates.byReminderId(reminderIdDb.getReminderId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(reminderIdDb));

        referralService.updateReminder(reminderIdDb.getReminderId(), updatedReminder);
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
        assertThrows(EntityNotFoundException.class, () -> referralService.updateReminder(reminderIdDb.getReminderId(), updatedReminder));
    }

    @Test
    void deleteReminder() {
        var reminderIdDb = reminder();
        var reminderId = reminderIdDb.getReminderId();
        var predicate = new BooleanBuilder();
        when(predicates.byReminderId(reminderId)).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(reminderIdDb));

        referralService.deleteReminder(reminderId);
        verify(repository).deleteById(reminderId);
    }

    @Test
    void deleteReminder_EntityNotFound() {
        var reminderIdDb = reminder();
        var reminderId = reminderIdDb.getReminderId();
        var predicate = new BooleanBuilder();
        when(predicates.byReminderId(reminderId)).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> referralService.deleteReminder(reminderId));
    }

    @Test
    void deleteByContactId() {
        var reminders = reminders();
        var contactId = randomLong();
        var predicate = new BooleanBuilder();
        when(predicates.byContactId(eq(contactId))).thenReturn(predicate);
        when(repository.findAll(predicate)).thenReturn(reminders);

        referralService.deleteByContactId(contactId);
        verify(repository).deleteAll(reminders);
    }

    private void assertFieldsUpdated(Referral referralIdDb, Referral updatedReferral, Referral savedReferral) {
        assertThat(referralIdDb.getReminderId()).isEqualTo(savedReferral.getReminderId());
        assertThat(referralIdDb.getContactId()).isEqualTo(savedReferral.getContactId());
        assertThat(referralIdDb.getOwner()).isEqualTo(savedReferral.getOwner());
        assertThat(referralIdDb.getCreationDate()).isEqualTo(savedReferral.getCreationDate());
        assertThat(savedReferral.getTitle()).isEqualTo(updatedReferral.getTitle());
        assertThat(savedReferral.getDescription()).isEqualTo(updatedReferral.getDescription());
        assertThat(savedReferral.getRecurrence()).isEqualTo(updatedReferral.getRecurrence());
    }
}