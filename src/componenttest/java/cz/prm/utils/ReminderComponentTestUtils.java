package cz.prm.utils;

import static cz.prm.utils.ComponentTestUtils.randomLong;
import static cz.prm.utils.ComponentTestUtils.uuid;
import static java.lang.String.format;
import static org.assertj.core.util.Lists.newArrayList;

import cz.prm.controllers.dto.common.PaginationDto;
import cz.prm.controllers.dto.reminder.ReminderDto;
import cz.prm.controllers.dto.reminder.query.RemindersQueryDto;
import cz.prm.domain.reminder.Reminder;
import java.util.List;

public class ReminderComponentTestUtils {

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
        reminder.setDescription(format("Text%s", uuid()));
        return reminder;
    }

    public static ReminderDto reminderDto(long contactId) {
        var dto = new ReminderDto();
        dto.setContactId(contactId);
        dto.setTitle(uuid());
        dto.setDescription(uuid());
        return dto;
    }

    public static RemindersQueryDto remindersQueryDto() {
        var query = new RemindersQueryDto();
        query.setPagination(new PaginationDto());
        return query;
    }
}
