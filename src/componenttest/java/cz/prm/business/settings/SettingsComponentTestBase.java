package cz.prm.business.settings;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;

import cz.prm.business.BusinessComponentTestBase;
import cz.prm.controllers.dto.settings.GeneralSettingsDto;
import cz.prm.controllers.dto.settings.UserSettingsDto;
import cz.prm.utils.ComponentTestUser;
import io.restassured.common.mapper.TypeRef;

public class SettingsComponentTestBase extends BusinessComponentTestBase {

    protected static String SETTINGS_BASE_URL = "settings";
    protected static String GENERAL_SETTINGS_URL = SETTINGS_BASE_URL + "/general";
    protected static String USER_SETTINGS_URL = SETTINGS_BASE_URL + "/user";

    protected GeneralSettingsDto user1GetGeneralSettings() {
        return getGeneralSettings(USER_1);
    }

    protected GeneralSettingsDto user2GetGeneralSettings() {
        return getGeneralSettings(USER_2);
    }

    protected GeneralSettingsDto user3GetGeneralSettings() {
        return getGeneralSettings(USER_3);
    }

    protected void user1UpdateUserSettings(UserSettingsDto dto) {
        updateUserSettings(dto, USER_1);
    }

    protected void user2UpdateUserSettings(UserSettingsDto dto) {
        updateUserSettings(dto, USER_2);
    }

    protected void user3UpdateUserSettings(UserSettingsDto dto) {
        updateUserSettings(dto, USER_3);
    }

    protected UserSettingsDto user1GetUserSettings() {
        return getUserSettings(USER_1);
    }

    protected UserSettingsDto user2GetUserSettings() {
        return getUserSettings(USER_2);
    }

    protected UserSettingsDto user3GetUserSettings() {
        return getUserSettings(USER_3);
    }

    protected GeneralSettingsDto getGeneralSettings(ComponentTestUser user) {
        var typeRef = new TypeRef<GeneralSettingsDto>() {
        };
        return getOne(GENERAL_SETTINGS_URL, user, typeRef);
    }

    protected void updateUserSettings(UserSettingsDto dto, ComponentTestUser user) {
        put(USER_SETTINGS_URL, user, dto);
    }

    protected UserSettingsDto getUserSettings(ComponentTestUser user) {
        var typeRef = new TypeRef<UserSettingsDto>() {
        };
        return getOne(USER_SETTINGS_URL, user, typeRef);
    }
}
