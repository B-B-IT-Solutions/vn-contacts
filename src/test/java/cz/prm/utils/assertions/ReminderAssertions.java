package cz.prm.utils.assertions;

import static cz.prm.utils.assertions.CommonAssertions.assertQuery;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.reminder.ReminderDto;
import cz.prm.controllers.dto.reminder.query.RemindersQueryDto;
import cz.prm.domain.common.query.Page;
import cz.prm.domain.reminder.Reminder;
import cz.prm.domain.reminder.query.RemindersQuery;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.PageImpl;

public class ReminderAssertions {

    public static void assertPage(Page<Reminder> page, PageDto<ReminderDto> pageDto) {
        assertThat(page.getTotalPages()).isEqualTo(pageDto.getTotalPages());
        assertThat(page.getNumberOfElements()).isEqualTo(pageDto.getNumberOfElements());
        assertThat(page.getTotalElements()).isEqualTo(pageDto.getTotalElements());
        assertThat(page.getPageSize()).isEqualTo(pageDto.getPageSize());
        assertThat(page.getPageNumber()).isEqualTo(pageDto.getPageNumber());
        assertThat(page.getContent()).isNotEmpty().hasSameSizeAs(pageDto.getContent());
        page.getContent().forEach(contact -> {
            var dto = pageDto.getContent().stream().filter(c -> Objects.equals(contact.getContactId(), c.getContactId())).findFirst().get();
            assertReminder(contact, dto);
        });
    }

    public static void assertPage(Page<Reminder> page1, PageImpl<Reminder> page2) {
        assertThat(page1.getTotalPages()).isEqualTo(page2.getTotalPages());
        assertThat(page1.getNumberOfElements()).isEqualTo(page2.getNumberOfElements());
        assertThat(page1.getTotalElements()).isEqualTo(page2.getTotalElements());
        assertThat(page1.getPageSize()).isEqualTo(page2.getSize());
        assertThat(page1.getPageNumber()).isEqualTo(page2.getNumber());
        assertThat(page1.getContent()).isNotEmpty().hasSameSizeAs(page2.getContent());
        page1.getContent().forEach(reminder1 -> {
            var reminder2 = page2.getContent().stream().filter(c -> Objects.equals(reminder1.getContactId(), c.getContactId())).findFirst().get();
            assertReminder(reminder1, reminder2);
        });
    }

    public static void assertReminders(List<Reminder> reminders1, List<Reminder> reminders2) {
        assertThat(reminders1).isNotEmpty().hasSameSizeAs(reminders2);
        reminders1.forEach(c1 -> {
            var c2 = reminders2.stream().filter(u -> Objects.equals(c1.getContactId(), u.getContactId())).findFirst().get();
            assertReminder(c1, c2);
        });
    }

    public static void assertRemindersDto(List<Reminder> reminders, List<ReminderDto> dtos) {
        assertThat(reminders).isNotEmpty().hasSameSizeAs(dtos);
        reminders.forEach(u1 -> {
            var u2 = dtos.stream().filter(u -> Objects.equals(u1.getContactId(), u.getContactId())).findFirst().get();
            assertReminder(u1, u2);
        });
    }

    public static void assertReminder(Reminder reminder1, Reminder reminder2) {
        assertThat(reminder1.getReminderId()).isEqualTo(reminder2.getReminderId());
        assertThat(reminder1.getContactId()).isEqualTo(reminder2.getContactId());
        assertThat(reminder1.getDescription()).isEqualTo(reminder2.getDescription());
        assertThat(reminder1.getRecurrence()).isEqualTo(reminder2.getRecurrence());
        assertThat(reminder1.getLastEditDate()).isEqualTo(reminder2.getLastEditDate());
        assertThat(reminder1.getCreationDate()).isEqualTo(reminder2.getCreationDate());
        assertThat(reminder1.getOwner()).isEqualTo(reminder2.getOwner());
    }

    public static void assertReminder(Reminder reminder, ReminderDto dto) {
        assertThat(reminder.getReminderId()).isEqualTo(dto.getReminderId());
        assertThat(reminder.getContactId()).isEqualTo(dto.getContactId());
        assertThat(reminder.getTitle()).isEqualTo(dto.getTitle());
        assertThat(reminder.getDescription()).isEqualTo(dto.getDescription());
        assertThat(reminder.getRecurrence().getValue()).isNotBlank().isEqualTo(dto.getRecurrence());
        assertThat(reminder.getLastEditDate()).isEqualTo(dto.getLastEditDate());
        assertThat(reminder.getCreationDate()).isEqualTo(dto.getCreationDate());
    }

    public static void assertRemindersQuery(RemindersQuery query, RemindersQueryDto dto) {
        assertQuery(query, dto);
    }
}
