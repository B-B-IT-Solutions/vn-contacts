package cz.prm.business.settings;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;

import cz.prm.business.BusinessComponentTestBase;
import cz.prm.controllers.dto.customizations.contact.ContactCustomizationsDto;
import cz.prm.controllers.dto.customizations.note.NoteCustomizationsDto;
import cz.prm.controllers.dto.settings.AccountSettingsDto;
import cz.prm.controllers.dto.settings.notifications.NotificationSettingsDto;
import cz.prm.utils.ComponentTestUser;
import io.restassured.common.mapper.TypeRef;

public class SettingsComponentTestBase extends BusinessComponentTestBase {

    protected static String SETTINGS_BASE_URL = "settings";
    protected static String ACCOUNT_SETTINGS_URL = SETTINGS_BASE_URL + "/account";
    protected static String CONTACT_SETTINGS_URL = SETTINGS_BASE_URL + "/contact";
    protected static String NOTE_SETTINGS_URL = SETTINGS_BASE_URL + "/note";
    protected static String NOTIFICATION_SETTINGS_URL = SETTINGS_BASE_URL + "/notification";

    protected AccountSettingsDto user1GetAccountSettings() {
        return getAccountSettings(USER_1);
    }

    protected AccountSettingsDto user2GetAccountSettings() {
        return getAccountSettings(USER_2);
    }

    protected AccountSettingsDto user3GetAccountSettings() {
        return getAccountSettings(USER_3);
    }

    protected ContactCustomizationsDto user1GetContactSettings() {
        return getContactSettings(USER_1);
    }

    protected ContactCustomizationsDto user2GetContactSettings() {
        return getContactSettings(USER_2);
    }

    protected ContactCustomizationsDto user3GetContactSettings() {
        return getContactSettings(USER_3);
    }

    protected NoteCustomizationsDto user1GetNoteSettings() {
        return getNoteSettings(USER_1);
    }

    protected NoteCustomizationsDto user2GetNoteSettings() {
        return getNoteSettings(USER_2);
    }

    protected NoteCustomizationsDto user3GetNoteSettings() {
        return getNoteSettings(USER_3);
    }

    protected NotificationSettingsDto user1GetNotificationSettings() {
        return getNotificationSettings(USER_1);
    }

    protected NotificationSettingsDto user2GetNotificationSettings() {
        return getNotificationSettings(USER_2);
    }

    protected NotificationSettingsDto user3GetNotificationSettings() {
        return getNotificationSettings(USER_3);
    }

    protected void user1UpdateContactSettings(ContactCustomizationsDto dto) {
        updateContactSettings(dto, USER_1);
    }

    protected void user2UpdateContactSettings(ContactCustomizationsDto dto) {
        updateContactSettings(dto, USER_2);
    }

    protected void user3UpdateContactSettings(ContactCustomizationsDto dto) {
        updateContactSettings(dto, USER_3);
    }

    protected void user1UpdateNoteSettings(NoteCustomizationsDto dto) {
        updateNoteSettings(dto, USER_1);
    }

    protected void user2UpdateNoteSettings(NoteCustomizationsDto dto) {
        updateNoteSettings(dto, USER_2);
    }

    protected void user3UpdateNoteSettings(NoteCustomizationsDto dto) {
        updateNoteSettings(dto, USER_3);
    }

    protected void user1UpdateNotificationSettings(NotificationSettingsDto dto) {
        updateNotificationSettings(dto, USER_1);
    }

    protected void user2UpdateNotificationSettings(NotificationSettingsDto dto) {
        updateNotificationSettings(dto, USER_2);
    }

    protected void user3UpdateNotificationSettings(NotificationSettingsDto dto) {
        updateNotificationSettings(dto, USER_3);
    }

    protected AccountSettingsDto getAccountSettings(ComponentTestUser user) {
        var typeRef = new TypeRef<AccountSettingsDto>() {
        };
        return getOne(ACCOUNT_SETTINGS_URL, user, typeRef);
    }

    protected ContactCustomizationsDto getContactSettings(ComponentTestUser user) {
        var typeRef = new TypeRef<ContactCustomizationsDto>() {
        };
        return getOne(CONTACT_SETTINGS_URL, user, typeRef);
    }

    protected NoteCustomizationsDto getNoteSettings(ComponentTestUser user) {
        var typeRef = new TypeRef<NoteCustomizationsDto>() {
        };
        return getOne(NOTE_SETTINGS_URL, user, typeRef);
    }

    protected NotificationSettingsDto getNotificationSettings(ComponentTestUser user) {
        var typeRef = new TypeRef<NotificationSettingsDto>() {
        };
        return getOne(NOTIFICATION_SETTINGS_URL, user, typeRef);
    }

    protected void updateContactSettings(ContactCustomizationsDto dto, ComponentTestUser user) {
        put(CONTACT_SETTINGS_URL, user, dto);
    }

    protected void updateNoteSettings(NoteCustomizationsDto dto, ComponentTestUser user) {
        put(NOTE_SETTINGS_URL, user, dto);
    }

    protected void updateNotificationSettings(NotificationSettingsDto dto, ComponentTestUser user) {
        put(NOTIFICATION_SETTINGS_URL, user, dto);
    }
}
