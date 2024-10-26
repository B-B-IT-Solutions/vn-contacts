package cz.prm.utils;

import static cz.prm.utils.ComponentTestUtils.randomLong;
import static cz.prm.utils.ComponentTestUtils.uuid;
import static cz.prm.utils.TestUtils.randomInt;
import static java.lang.String.format;
import static org.assertj.core.util.Lists.newArrayList;

import cz.prm.controllers.dto.common.PaginationDto;
import cz.prm.controllers.dto.reminder.ReminderDto;
import cz.prm.controllers.dto.reminder.query.RemindersQueryDto;
import cz.prm.domain.reminder.Recurrence;
import cz.prm.domain.reminder.Reminder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.dmfs.rfc5545.recur.Freq;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRule.Part;

public class ReminderComponentTestUtils {

    private static final String DATE_FORMAT_PATTERN = "yyyyMMdd";

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT_PATTERN);

    public static List<Reminder> reminders() {
        return newArrayList(reminder(), reminder(), reminder());
    }

    public static Reminder reminder() {
        return reminder(randomLong());
    }

    public static Reminder reminder(long contactId) {
        var reminder = new Reminder();
        reminder.setContactId(contactId);
        reminder.setTitle(format("Title%s", uuid()));
        reminder.setDescription(format("Description%s", uuid()));
        reminder.setRecurrence(recurrence());
        return reminder;
    }

    public static ReminderDto reminderDto(long contactId) {
        var dto = new ReminderDto();
        dto.setContactId(contactId);
        dto.setTitle(uuid());
        dto.setDescription(uuid());
        dto.setRecurrence(uuid());
        return dto;
    }

    public static Recurrence recurrence() {
        var rrule = recurrenceRule();
        var startDate = DATE_FORMATTER.format(new Date());
        var value = format("DTSTART:%s\nRRULE:%s", startDate, rrule);
        return new Recurrence(value);
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

    public static RemindersQueryDto remindersQueryDto() {
        var query = new RemindersQueryDto();
        query.setPagination(new PaginationDto());
        return query;
    }
}
