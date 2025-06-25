package cz.prm.services;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.RecurrenceUtils.recurrence;
import static cz.prm.utils.ReminderUtils.reminder;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private ReminderService reminderService;

    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        notificationService = new NotificationService(reminderService);
    }

    @Test
    void getNotifications() {
        var reminder1 = reminder();
        var recurrence = recurrence();
        reminder1.setRecurrence(recurrence);
        var reminder2 = reminder();
        var reminder3 = reminder();
        var reminders = newArrayList(reminder1, reminder2, reminder3);

        when(reminderService.getReminders()).thenReturn(reminders);
        notificationService.getNotifications();
    }
}