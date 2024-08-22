package cz.prm.business.reminders;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import cz.prm.business.BusinessComponentTestBase;
import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.reminder.ReminderDto;
import cz.prm.controllers.dto.reminder.query.RemindersQueryDto;
import cz.prm.utils.ComponentTestUser;
import io.restassured.common.mapper.TypeRef;

public class ReminderComponentTestBase extends BusinessComponentTestBase {

    protected static String REMINDERS_BASE_URL = "reminders";
    protected static String CONTACT_REMINDERS_URL = REMINDERS_BASE_URL + "/contact/%s";
    protected static String REMINDER_URL = REMINDERS_BASE_URL + "/reminder";
    protected static String REMINDER_BY_ID_URL = REMINDER_URL + "/%s";

    protected void user1CreateReminder(ReminderDto dto) {
        createReminder(dto, USER_1);
    }

    protected void user2CreateReminder(ReminderDto dto) {
        createReminder(dto, USER_2);
    }

    protected void user3CreateReminder(ReminderDto dto) {
        createReminder(dto, USER_3);
    }

    protected void user1UpdateReminder(Long reminderId, ReminderDto dto) {
        updateReminder(reminderId, dto, USER_1);
    }

    protected void user2UpdateReminder(Long reminderId, ReminderDto dto) {
        updateReminder(reminderId, dto, USER_2);
    }

    protected void user3UpdateReminder(Long reminderId, ReminderDto dto) {
        updateReminder(reminderId, dto, USER_3);
    }

    protected void user1DeleteReminder(Long reminderId) {
        deleteReminder(reminderId, USER_1);
    }

    protected void user2DeleteReminder(Long reminderId) {
        deleteReminder(reminderId, USER_2);
    }

    protected void user3DeleteReminder(Long reminderId) {
        deleteReminder(reminderId, USER_3);
    }

    protected PageDto<ReminderDto> user1GetReminders(Long contactId, RemindersQueryDto queryDto) {
        return getRemindersPage(contactId, queryDto, USER_1);
    }

    protected PageDto<ReminderDto> user2GetReminders(Long contactId, RemindersQueryDto queryDto) {
        return getRemindersPage(contactId, queryDto, USER_2);
    }

    protected PageDto<ReminderDto> user3GetReminders(Long contactId, RemindersQueryDto queryDto) {
        return getRemindersPage(contactId, queryDto, USER_3);
    }

    protected ReminderDto user1GetReminder(Long reminderId) {
        return getReminder(reminderId, USER_1);
    }

    protected ReminderDto user2GetReminder(Long reminderId) {
        return getReminder(reminderId, USER_2);
    }

    protected ReminderDto user3GetReminder(Long reminderId) {
        return getReminder(reminderId, USER_3);
    }

    protected void createReminder(ReminderDto dto, ComponentTestUser user) {
        post(REMINDER_URL, user, dto);
    }

    protected void updateReminder(Long reminderId, ReminderDto dto, ComponentTestUser user) {
        var url = format(REMINDER_BY_ID_URL, reminderId);
        put(url, user, dto);
    }

    protected void deleteReminder(Long reminderId, ComponentTestUser user) {
        var url = format(REMINDER_BY_ID_URL, reminderId);
        delete(url, user);
    }

    protected PageDto<ReminderDto> getRemindersPage(Long contactId, RemindersQueryDto queryDto, ComponentTestUser user) {
        var baseURl = format(CONTACT_REMINDERS_URL, contactId);
        var url = appendQueryToUrl(baseURl, queryDto);
        var typeRef = new TypeRef<PageDto<ReminderDto>>() {
        };
        return getPage(url, user, typeRef);
    }

    protected ReminderDto getReminder(Long reminderId, ComponentTestUser user) {
        var url = format(REMINDER_BY_ID_URL, reminderId);
        var typeRef = new TypeRef<ReminderDto>() {
        };
        return getOne(url, user, typeRef);
    }

    protected void user1UpdateReminderExpectNotFound(Long reminderId, ReminderDto dto) {
        updateReminderExpectNotFound(reminderId, dto, USER_1);
    }

    protected void user2UpdateReminderExpectNotFound(Long reminderId, ReminderDto dto) {
        updateReminderExpectNotFound(reminderId, dto, USER_2);
    }

    protected void user3UpdateReminderExpectNotFound(Long reminderId, ReminderDto dto) {
        updateReminderExpectNotFound(reminderId, dto, USER_3);
    }

    protected void user1DeleteReminderExpectNotFound(Long reminderId) {
        deleteReminderExpectNotFound(reminderId, USER_1);
    }

    protected void user2DeleteReminderExpectNotFound(Long reminderId) {
        deleteReminderExpectNotFound(reminderId, USER_2);
    }

    protected void user3DeleteReminderExpectNotFound(Long reminderId) {
        deleteReminderExpectNotFound(reminderId, USER_3);
    }

    protected void user1GetReminderExpectNotFound(Long reminderId) {
        getReminderExpectNotFound(reminderId, USER_1);
    }

    protected void user2GetReminderExpectNotFound(Long reminderId) {
        getReminderExpectNotFound(reminderId, USER_2);
    }

    protected void user3GetReminderExpectNotFound(Long reminderId) {
        getReminderExpectNotFound(reminderId, USER_3);
    }

    protected void updateReminderExpectNotFound(Long reminderId, ReminderDto dto, ComponentTestUser user) {
        var url = format(REMINDER_BY_ID_URL, reminderId);
        putExpectNotFound(url, user, dto);
    }

    protected void deleteReminderExpectNotFound(Long reminderId, ComponentTestUser user) {
        var url = format(REMINDER_BY_ID_URL, reminderId);
        deleteExpectNotFound(url, user);
    }

    protected void getReminderExpectNotFound(Long reminderId, ComponentTestUser user) {
        var url = format(REMINDER_BY_ID_URL, reminderId);
        getExpectNotFound(url, user);
    }

    protected String appendQueryToUrl(String url, RemindersQueryDto queryDto) {
        var sb = new StringBuilder(url);
        var pagination = toUrlPaginationParams(queryDto.getPagination());
        var sort = toUrlSortParams(queryDto.getSort());

        if (isNotBlank(pagination) || isNotBlank(sort)) {
            sb.append("?");
            sb.append(pagination);
            sb.append(sort);
        }
        return sb.toString();
    }
}
