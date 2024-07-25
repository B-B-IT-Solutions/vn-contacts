package cz.prm.business.settings;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;

import cz.prm.business.BusinessComponentTestBase;
import cz.prm.controllers.dto.settings.AccountSettingsDto;
import cz.prm.controllers.dto.settings.ContactSettingsDto;
import cz.prm.utils.ComponentTestUser;
import io.restassured.common.mapper.TypeRef;

public class SettingsComponentTestBase extends BusinessComponentTestBase {

    protected static String SETTINGS_BASE_URL = "settings";
    protected static String ACCOUNT_SETTINGS_URL = SETTINGS_BASE_URL + "/account";
    protected static String CONTACT_SETTINGS_URL = SETTINGS_BASE_URL + "/contact";

    protected AccountSettingsDto user1GetAccountSettings() {
        return getAccountSettings(USER_1);
    }

    protected AccountSettingsDto user2GetAccountSettings() {
        return getAccountSettings(USER_2);
    }

    protected AccountSettingsDto user3GetAccountSettings() {
        return getAccountSettings(USER_3);
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

    protected ContactSettingsDto user1GetContactSettings() {
        return getContactSettings(USER_1);
    }

    protected ContactSettingsDto user2GetContactSettings() {
        return getContactSettings(USER_2);
    }

    protected ContactSettingsDto user3GetContactSettings() {
        return getContactSettings(USER_3);
    }

    protected AccountSettingsDto getAccountSettings(ComponentTestUser user) {
        var typeRef = new TypeRef<AccountSettingsDto>() {
        };
        return getOne(ACCOUNT_SETTINGS_URL, user, typeRef);
    }

    protected void updateContactSettings(ContactSettingsDto dto, ComponentTestUser user) {
        put(CONTACT_SETTINGS_URL, user, dto);
    }

    protected ContactSettingsDto getContactSettings(ComponentTestUser user) {
        var typeRef = new TypeRef<ContactSettingsDto>() {
        };
        return getOne(CONTACT_SETTINGS_URL, user, typeRef);
    }
}
