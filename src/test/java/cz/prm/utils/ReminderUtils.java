package cz.prm.utils;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.TestUtils.randomInt;
import static cz.prm.utils.TestUtils.randomLong;

import cz.prm.domain.reminder.Recurrence;
import cz.prm.domain.reminder.Reminder;
import java.util.List;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.Freq;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRule.Part;

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

    public static Recurrence recurrence() {
        var rrule = recurrenceRule();
        var startDate = DateTime.now();
        var value = String.format("DTSTART:%s\nRRULE:%s", startDate, rrule);
        return recurrence(value);
    }

    public static Recurrence recurrence(String value) {
        var r = new Recurrence();
        r.setRecurrenceId(randomLong());
        r.setValue(value);
        return r;
    }

    public static RecurrenceRule recurrenceRule() {
        try {
            var rrule = new RecurrenceRule(Freq.DAILY);
            rrule.setByPart(Part.BYMONTH, randomInt());
            rrule.setByPart(Part.BYMONTHDAY, randomInt());
            rrule.setCount(randomInt());
            return rrule;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
