package cz.prm.utils.data.contacts;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.data.contacts.RecurrenceUtils.recurrence;

import cz.prm.domain.contacts.reminder.Reminder;
import java.util.List;

public class ReminderUtils {

    public static List<Reminder> reminders() {
        return newArrayList(reminder(), reminder(), reminder());
    }

    public static Reminder reminder() {
        var reminder = new Reminder();
        reminder.setReminderId(randomLong());
        reminder.setRecurrence(recurrence());
        return reminder;
    }
}
