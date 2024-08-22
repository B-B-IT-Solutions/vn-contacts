package cz.prm.business.reminders;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static cz.prm.utils.ComponentTestUtils.randomLong;
import static cz.prm.utils.ComponentTestUtils.uuid;
import static cz.prm.utils.ReminderComponentTestUtils.reminderDto;
import static cz.prm.utils.ReminderComponentTestUtils.remindersQueryDto;
import static cz.prm.utils.assertions.ReminderComponentTestAssertions.assertReminder;
import static cz.prm.utils.assertions.ReminderComponentTestAssertions.assertReminders;
import static java.util.Collections.sort;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.reminder.ReminderDto;
import org.junit.jupiter.api.Test;

public class ReminderComponentTest extends ReminderComponentTestBase {

    @Test
    void getRemindersDataAccess() {
        var queryDto = remindersQueryDto();
        var contactId = randomLong();
        var pageDto = user1GetReminders(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user2GetReminders(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetReminders(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var reminders = createReminders(USER_1);
        var reminder = reminders.get(0);
        contactId = reminder.getContactId();

        pageDto = user1GetReminders(contactId, queryDto);
        assertReminders(reminders, pageDto);

        pageDto = user2GetReminders(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetReminders(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user2Reminders = createReminders(USER_2);
        reminder = user2Reminders.get(0);
        contactId = reminder.getContactId();

        pageDto = user2GetReminders(contactId, queryDto);
        assertReminders(user2Reminders, pageDto);

        pageDto = user1GetReminders(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetReminders(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user3Reminders = createReminders(USER_3);
        reminder = user3Reminders.get(0);
        contactId = reminder.getContactId();

        pageDto = user3GetReminders(contactId, queryDto);
        assertReminders(user3Reminders, pageDto);

        pageDto = user1GetReminders(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user2GetReminders(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();
    }

    @Test
    void getRemindersPagination() {
        var queryDto = remindersQueryDto();
        var contactId = randomLong();
        var pageDto = user1GetReminders(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isZero();
        assertThat(pageDto.getTotalElements()).isZero();
        assertThat(pageDto.getPageSize()).isEqualTo(50);
        assertThat(pageDto.getContent()).isEmpty();

        var reminders = createReminders(USER_1, 21);
        var reminder = reminders.get(0);
        contactId = reminder.getContactId();

        pageDto = user1GetReminders(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(1);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(50);
        assertThat(pageDto.getContent()).hasSize(21);

        var pagination = queryDto.getPagination();
        pagination.setPageSize(5);
        pageDto = user1GetReminders(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(5);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(5);
        assertThat(pageDto.getContent()).hasSize(5);

        pagination.setPageNumber(1);
        pagination.setPageSize(10);
        pageDto = user1GetReminders(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(3);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(10);
        assertThat(pageDto.getContent()).hasSize(10);

        pagination.setPageNumber(2);
        pagination.setPageSize(10);
        pageDto = user1GetReminders(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(3);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(10);
        assertThat(pageDto.getContent()).hasSize(1);
    }

    @Test
    void getRemindersSorting() {
        var queryDto = remindersQueryDto();
        var contactId = randomLong();
        var pageDto = user1GetReminders(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var reminders = createReminders(USER_1, 21);
        var reminder = reminders.get(0);
        contactId = reminder.getContactId();

        queryDto = remindersQueryDto();
        queryDto.setSort(null);
        pageDto = user1GetReminders(contactId, queryDto);
        var actual = pageDto.getContent();
        var expected = newArrayList(actual);
        sort(expected, comparing(ReminderDto::getCreationDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto = remindersQueryDto();
        queryDto.setSort("asc(lastEditDate)");
        pageDto = user1GetReminders(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(ReminderDto::getLastEditDate));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(lastEditDate)");
        pageDto = user1GetReminders(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(ReminderDto::getLastEditDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("asc(creationDate)");
        pageDto = user1GetReminders(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(ReminderDto::getCreationDate));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(creationDate)");
        pageDto = user1GetReminders(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(ReminderDto::getCreationDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("asc(contactId)");
        pageDto = user1GetReminders(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(ReminderDto::getContactId));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(contactId)");
        pageDto = user1GetReminders(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(ReminderDto::getContactId).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);
    }

    @Test
    void getReminder() {
        var reminder = createReminder(USER_1);
        var reminderId = reminder.getReminderId();

        var reminderDto = user1GetReminder(reminderId);
        assertReminder(reminder, reminderDto);
        user2GetReminderExpectNotFound(reminderId);
        user3GetReminderExpectNotFound(reminderId);

        reminder = createReminder(USER_2);
        reminderId = reminder.getReminderId();
        reminderDto = user2GetReminder(reminderId);
        assertReminder(reminder, reminderDto);
        user1GetReminderExpectNotFound(reminderId);
        user3GetReminderExpectNotFound(reminderId);

        reminder = createReminder(USER_3);
        reminderId = reminder.getReminderId();
        reminderDto = user3GetReminder(reminderId);
        assertReminder(reminder, reminderDto);
        user1GetReminderExpectNotFound(reminderId);
        user2GetReminderExpectNotFound(reminderId);
    }

    @Test
    void createReminder() {
        var contact = createContact(USER_1);
        var toCreateDto = reminderDto(contact.getContactId());
        user1CreateReminder(toCreateDto);
        var reminder = getReminderFromDb(toCreateDto);
        var reminderId = reminder.getReminderId();

        var createdDto = user1GetReminder(reminderId);
        assertReminder(reminder, createdDto);
        user2GetReminderExpectNotFound(reminderId);
        user3GetReminderExpectNotFound(reminderId);

        contact = createContact(USER_2);
        toCreateDto = reminderDto(contact.getContactId());
        user2CreateReminder(toCreateDto);
        reminder = getReminderFromDb(toCreateDto);
        reminderId = reminder.getReminderId();

        createdDto = user2GetReminder(reminderId);
        assertReminder(reminder, createdDto);
        user1GetReminderExpectNotFound(reminderId);
        user3GetReminderExpectNotFound(reminderId);

        contact = createContact(USER_3);
        toCreateDto = reminderDto(contact.getContactId());
        user3CreateReminder(toCreateDto);
        reminder = getReminderFromDb(toCreateDto);
        reminderId = reminder.getReminderId();

        createdDto = user3GetReminder(reminderId);
        assertReminder(reminder, createdDto);
        user1GetReminderExpectNotFound(reminderId);
        user2GetReminderExpectNotFound(reminderId);
    }

    @Test
    void updateReminder() {
        var reminder = createReminder(USER_1);
        var reminderId = reminder.getReminderId();
        var updateDto = user1GetReminder(reminderId);

        updateDto.setDescription(uuid());
        user1UpdateReminder(reminderId, updateDto);
        reminder = getReminderFromDb(updateDto);
        assertReminder(reminder, updateDto);

        user2UpdateReminderExpectNotFound(reminderId, updateDto);
        user3UpdateReminderExpectNotFound(reminderId, updateDto);

        reminder = createReminder(USER_2);
        reminderId = reminder.getReminderId();
        updateDto = user2GetReminder(reminderId);

        updateDto.setDescription(uuid());
        user2UpdateReminder(reminderId, updateDto);
        reminder = getReminderFromDb(updateDto);
        assertReminder(reminder, updateDto);

        user1UpdateReminderExpectNotFound(reminderId, updateDto);
        user3UpdateReminderExpectNotFound(reminderId, updateDto);

        reminder = createReminder(USER_3);
        reminderId = reminder.getReminderId();
        updateDto = user3GetReminder(reminderId);

        updateDto.setDescription(uuid());
        user3UpdateReminder(reminderId, updateDto);
        reminder = getReminderFromDb(updateDto);
        assertReminder(reminder, updateDto);

        user1UpdateReminderExpectNotFound(reminderId, updateDto);
        user2UpdateReminderExpectNotFound(reminderId, updateDto);
    }

    @Test
    void deleteReminder() {
        var reminder = createReminder(USER_1);
        var reminderId = reminder.getReminderId();
        var reminderDto = user1GetReminder(reminderId);
        assertReminder(reminder, reminderDto);

        user2DeleteReminderExpectNotFound(reminderId);
        user3DeleteReminderExpectNotFound(reminderId);
        user1DeleteReminder(reminderId);
        user1GetReminderExpectNotFound(reminderId);

        reminder = createReminder(USER_2);
        reminderId = reminder.getReminderId();
        reminderDto = user2GetReminder(reminderId);
        assertReminder(reminder, reminderDto);

        user1DeleteReminderExpectNotFound(reminderId);
        user3DeleteReminderExpectNotFound(reminderId);
        user2DeleteReminder(reminderId);
        user2GetReminderExpectNotFound(reminderId);

        reminder = createReminder(USER_3);
        reminderId = reminder.getReminderId();
        reminderDto = user3GetReminder(reminderId);
        assertReminder(reminder, reminderDto);

        user1DeleteReminderExpectNotFound(reminderId);
        user2DeleteReminderExpectNotFound(reminderId);
        user3DeleteReminder(reminderId);
        user3GetReminderExpectNotFound(reminderId);
    }
}
