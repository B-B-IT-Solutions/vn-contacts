package cz.prm.utils.assertions.contacts;

import static cz.prm.utils.assertions.contacts.RecurrenceAssertions.assertRecurrence;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.common.query.Page;
import cz.prm.domain.contacts.reminder.Reminder;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.PageImpl;

public class ReminderAssertions {

    public static void assertPage(Page<Reminder> page1, PageImpl<Reminder> page2) {
        assertThat(page1.getTotalPages()).isEqualTo(page2.getTotalPages());
        assertThat(page1.getNumberOfElements()).isEqualTo(page2.getNumberOfElements());
        assertThat(page1.getTotalElements()).isEqualTo(page2.getTotalElements());
        assertThat(page1.getPageSize()).isEqualTo(page2.getSize());
        assertThat(page1.getPageNumber()).isEqualTo(page2.getNumber());
        assertReminders(page1.getContent(), page2.getContent());
    }

    public static void assertReminders(List<Reminder> reminders1, List<Reminder> reminders2) {
        assertThat(reminders1).isNotEmpty().hasSameSizeAs(reminders2);
        reminders1.forEach(c1 -> {
            var c2 = reminders2.stream().filter(u -> Objects.equals(c1.getReminderId(), u.getReminderId())).findFirst().get();
            assertReminder(c1, c2);
        });
    }

    public static void assertReminder(Reminder reminder1, Reminder reminder2) {
        assertThat(reminder1.getReminderId()).isEqualTo(reminder2.getReminderId());
        assertRecurrence(reminder1.getRecurrence(), reminder2.getRecurrence());
    }
}
