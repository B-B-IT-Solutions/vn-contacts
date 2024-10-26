package cz.prm.utils;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.CommonUtils.pagination;
import static cz.prm.utils.CommonUtils.paginationDto;
import static cz.prm.utils.CommonUtils.user;
import static cz.prm.utils.TestUtils.randomInt;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.TestUtils.uuid;
import static java.time.Instant.now;

import cz.prm.controllers.dto.reminder.ReminderDto;
import cz.prm.controllers.dto.reminder.query.RemindersQueryDto;
import cz.prm.domain.reminder.Recurrence;
import cz.prm.domain.reminder.Reminder;
import cz.prm.domain.reminder.query.RemindersQuery;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.dmfs.rfc5545.recur.Freq;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRule.Part;

public class ReminderUtils {

    private static final String DATE_FORMAT_PATTERN = "yyyyMMdd";

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT_PATTERN);

    public static List<Reminder> reminders() {
        return newArrayList(reminder(), reminder(), reminder());
    }

    public static Reminder reminder() {
        var reminder = new Reminder();
        reminder.setReminderId(randomLong());
        reminder.setContactId(randomLong());
        reminder.setTitle(uuid());
        reminder.setDescription(uuid());
        reminder.setRecurrence(recurrence());
        reminder.setLastEditDate(now());
        reminder.setCreationDate(now());
        reminder.setOwner(user());
        return reminder;
    }

    public static ReminderDto reminderDto() {
        var reminder = new ReminderDto();
        reminder.setReminderId(randomLong());
        reminder.setContactId(randomLong());
        reminder.setTitle(uuid());
        reminder.setDescription(uuid());
        reminder.setRecurrence(uuid());
        reminder.setLastEditDate(now());
        reminder.setCreationDate(now());
        return reminder;
    }

    public static Recurrence recurrence() {
        var rrule = recurrenceRule();
        var startDate = DATE_FORMATTER.format(new Date());
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

    public static RemindersQuery remindersQuery() {
        var query = new RemindersQuery();
        query.setPagination(pagination());
        query.setSort(uuid());
        return query;
    }

    public static RemindersQueryDto remindersQueryDto() {
        var query = new RemindersQueryDto();
        query.setPagination(paginationDto());
        query.setSort(uuid());
        return query;
    }
}
