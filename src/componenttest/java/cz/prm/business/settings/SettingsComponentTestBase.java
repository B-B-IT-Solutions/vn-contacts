package cz.prm.business.settings;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;

import cz.prm.business.BusinessComponentTestBase;
import cz.prm.controllers.dto.settings.AccountSettingsDto;
import cz.prm.controllers.dto.settings.notifications.NotificationSettingsDto;
import cz.prm.utils.ComponentTestUser;
import io.restassured.common.mapper.TypeRef;

public class SettingsComponentTestBase extends BusinessComponentTestBase {

    protected static String SETTINGS_BASE_URL = "settings";
    protected static String ACCOUNT_SETTINGS_URL = SETTINGS_BASE_URL + "/account";
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

    protected NotificationSettingsDto user1GetNotificationSettings() {
        return getNotificationSettings(USER_1);
    }

    protected NotificationSettingsDto user2GetNotificationSettings() {
        return getNotificationSettings(USER_2);
    }

    protected NotificationSettingsDto user3GetNotificationSettings() {
        return getNotificationSettings(USER_3);
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

    protected NotificationSettingsDto getNotificationSettings(ComponentTestUser user) {
        var typeRef = new TypeRef<NotificationSettingsDto>() {
        };
        return getOne(NOTIFICATION_SETTINGS_URL, user, typeRef);
    }

    protected void updateNotificationSettings(NotificationSettingsDto dto, ComponentTestUser user) {
        put(NOTIFICATION_SETTINGS_URL, user, dto);
    }
}
