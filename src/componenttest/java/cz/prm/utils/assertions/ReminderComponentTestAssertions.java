package cz.prm.utils.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.reminder.ReminderDto;
import cz.prm.domain.reminder.Reminder;
import java.util.List;
import java.util.Objects;

public class ReminderComponentTestAssertions {

    public static void assertReminders(List<Reminder> reminders, PageDto<ReminderDto> pageDto) {
        assertReminders(reminders, pageDto.getContent());
    }

    public static void assertReminders(List<Reminder> reminders, List<ReminderDto> dtos) {
        assertThat(reminders).isNotEmpty().hasSameSizeAs(dtos);
        reminders.forEach(n1 -> {
            var n2 = dtos.stream().filter(n -> Objects.equals(n1.getReminderId(), n.getReminderId())).findFirst().get();
            assertReminder(n1, n2);
        });
    }

    public static void assertReminder(Reminder reminder, ReminderDto reminderDto) {
        assertThat(reminder.getReminderId()).isEqualTo(reminderDto.getReminderId());
        assertThat(reminder.getContactId()).isEqualTo(reminderDto.getContactId());
        assertThat(reminder.getTitle()).isEqualTo(reminderDto.getTitle());
        assertThat(reminder.getDescription()).isEqualTo(reminderDto.getDescription());
        assertThat(reminder.getRecurrence()).isEqualTo(reminderDto.getRecurrence());
        assertThat(reminder.getLastEditDate()).isNotNull();
        assertThat(reminder.getCreationDate()).isNotNull();
    }
}
