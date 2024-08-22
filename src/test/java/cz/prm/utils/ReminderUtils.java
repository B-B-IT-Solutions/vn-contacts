package cz.prm.utils;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.CommonUtils.pagination;
import static cz.prm.utils.CommonUtils.paginationDto;
import static cz.prm.utils.CommonUtils.user;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.TestUtils.uuid;
import static java.time.Instant.now;

import cz.prm.controllers.dto.reminder.ReminderDto;
import cz.prm.controllers.dto.reminder.query.RemindersQueryDto;
import cz.prm.domain.reminder.Reminder;
import cz.prm.domain.reminder.query.RemindersQuery;
import java.util.List;

public class ReminderUtils {

    public static List<Reminder> reminders() {
        return newArrayList(reminder(), reminder(), reminder());
    }

    public static Reminder reminder() {
        var reminder = new Reminder();
        reminder.setReminderId(randomLong());
        reminder.setContactId(randomLong());
        reminder.setTitle(uuid());
        reminder.setDescription(uuid());
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
        reminder.setLastEditDate(now());
        reminder.setCreationDate(now());
        return reminder;
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
