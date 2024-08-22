package cz.prm.controllers;

import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.ReminderUtils.reminder;
import static cz.prm.utils.ReminderUtils.reminderDto;
import static cz.prm.utils.ReminderUtils.reminders;
import static cz.prm.utils.ReminderUtils.remindersQueryDto;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.assertions.ReminderAssertions.assertPage;
import static cz.prm.utils.assertions.ReminderAssertions.assertReminder;
import static cz.prm.utils.assertions.ReminderAssertions.assertRemindersQuery;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.prm.controllers.mappers.ReminderMapper;
import cz.prm.domain.reminder.Reminder;
import cz.prm.domain.reminder.query.RemindersQuery;
import cz.prm.services.ReminderService;
import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReminderControllerTest {

    @Mock
    private ReminderService reminderService;
    @Captor
    private ArgumentCaptor<Reminder> reminderCapt;
    @Captor
    private ArgumentCaptor<RemindersQuery> cQueryCapt;

    private ReminderMapper mapper = MapperUtils.getReminderMapper();
    private ReminderController controller;

    @BeforeEach
    void setUp() {
        controller = new ReminderController(reminderService, mapper);
    }

    @Test
    void getReminders() {
        var page = page(reminders());
        var queryDto = remindersQueryDto();
        var contactId = randomLong();
        when(reminderService.getReminders(eq(contactId), any(RemindersQuery.class))).thenReturn(page);

        var result = controller.getReminders(contactId, queryDto);
        assertPage(page, result);
        verify(reminderService).getReminders(eq(contactId), cQueryCapt.capture());
        var query = cQueryCapt.getValue();
        assertRemindersQuery(query, queryDto);
    }

    @Test
    void getReminder() {
        var reminder = reminder();
        var reminderId = reminder.getReminderId();
        when(reminderService.getReminder(reminderId)).thenReturn(reminder);
        var result = controller.getReminder(reminderId);
        assertReminder(reminder, result);
    }

    @Test
    void createReminder() {
        var dto = reminderDto();
        controller.createReminder(dto);
        verify(reminderService).createReminder(reminderCapt.capture());
        var reminder = reminderCapt.getValue();
        assertReminder(reminder, dto);
    }

    @Test
    void updateReminder() {
        var dto = reminderDto();
        controller.updateReminder(dto.getReminderId(), dto);
        verify(reminderService).updateReminder(eq(dto.getReminderId()), reminderCapt.capture());
        var reminder = reminderCapt.getValue();
        assertReminder(reminder, dto);
    }

    @Test
    void deleteReminder() {
        var reminderId = randomLong();
        controller.deleteReminder(reminderId);
        verify(reminderService).deleteReminder(reminderId);
    }
}