package cz.prm.business.settings;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;

import cz.prm.business.BusinessComponentTestBase;
import cz.prm.controllers.dto.settings.AccountSettingsDto;
import cz.prm.controllers.dto.settings.contact.ContactSettingsDto;
import cz.prm.controllers.dto.settings.note.NoteSettingsDto;
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

    protected ContactSettingsDto user1GetContactSettings() {
        return getContactSettings(USER_1);
    }

    protected ContactSettingsDto user2GetContactSettings() {
        return getContactSettings(USER_2);
    }

    protected ContactSettingsDto user3GetContactSettings() {
        return getContactSettings(USER_3);
    }

    protected NoteSettingsDto user1GetNoteSettings() {
        return getNoteSettings(USER_1);
    }

    protected NoteSettingsDto user2GetNoteSettings() {
        return getNoteSettings(USER_2);
    }

    protected NoteSettingsDto user3GetNoteSettings() {
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

    protected void user1UpdateContactSettings(ContactSettingsDto dto) {
        updateContactSettings(dto, USER_1);
    }

    protected void user2UpdateContactSettings(ContactSettingsDto dto) {
        updateContactSettings(dto, USER_2);
    }

    protected void user3UpdateContactSettings(ContactSettingsDto dto) {
        updateContactSettings(dto, USER_3);
    }

    protected void user1UpdateNoteSettings(NoteSettingsDto dto) {
        updateNoteSettings(dto, USER_1);
    }

    protected void user2UpdateNoteSettings(NoteSettingsDto dto) {
        updateNoteSettings(dto, USER_2);
    }

    protected void user3UpdateNoteSettings(NoteSettingsDto dto) {
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

    protected ContactSettingsDto getContactSettings(ComponentTestUser user) {
        var typeRef = new TypeRef<ContactSettingsDto>() {
        };
        return getOne(CONTACT_SETTINGS_URL, user, typeRef);
    }

    protected NoteSettingsDto getNoteSettings(ComponentTestUser user) {
        var typeRef = new TypeRef<NoteSettingsDto>() {
        };
        return getOne(NOTE_SETTINGS_URL, user, typeRef);
    }

    protected NotificationSettingsDto getNotificationSettings(ComponentTestUser user) {
        var typeRef = new TypeRef<NotificationSettingsDto>() {
        };
        return getOne(NOTIFICATION_SETTINGS_URL, user, typeRef);
    }

    protected void updateContactSettings(ContactSettingsDto dto, ComponentTestUser user) {
        put(CONTACT_SETTINGS_URL, user, dto);
    }

    protected void updateNoteSettings(NoteSettingsDto dto, ComponentTestUser user) {
        put(NOTE_SETTINGS_URL, user, dto);
    }

    protected void updateNotificationSettings(NotificationSettingsDto dto, ComponentTestUser user) {
        put(NOTIFICATION_SETTINGS_URL, user, dto);
    }
}
