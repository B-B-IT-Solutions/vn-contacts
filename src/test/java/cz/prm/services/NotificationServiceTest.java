package cz.prm.services;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.ReminderUtils.reminder;
import static cz.prm.utils.ReminderUtils.remindersQuery;
import static cz.prm.utils.TestUtils.randomLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import cz.prm.domain.reminder.query.RemindersQuery;
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
        var contactId = randomLong();
        var reminder1 = reminder();
        reminder1.setRecurrence("DTSTART:20241005T062200Z\\nRRULE:FREQ=YEARLY;BYMONTH=1;BYMONTHDAY=1;UNTIL=20241024T062200");
        var reminder2 = reminder();
        var reminder3 = reminder();
        var reminders = newArrayList(reminder1, reminder2, reminder3);
        var page = page(reminders);

        when(reminderService.getReminders(eq(contactId), any(RemindersQuery.class))).thenReturn(page);
        notificationService.getNotifications(contactId);
    }
}