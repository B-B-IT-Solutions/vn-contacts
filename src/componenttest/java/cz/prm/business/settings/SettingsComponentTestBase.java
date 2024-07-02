package cz.prm.business.settings;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;

import cz.prm.business.BusinessComponentTestBase;
import cz.prm.controllers.dto.settings.SettingsDto;
import cz.prm.utils.ComponentTestUser;
import io.restassured.common.mapper.TypeRef;

public class SettingsComponentTestBase extends BusinessComponentTestBase {

    protected static String SETTINGS_BASE_URL = "settings";
    protected static String USER_SETTINGS_URL = SETTINGS_BASE_URL + "/user";

    protected SettingsDto user1GetUserSettings() {
        return getUserSettings(USER_1);
    }

    protected SettingsDto user2GetUserSettings() {
        return getUserSettings(USER_2);
    }

    protected SettingsDto user3GetUserSettings() {
        return getUserSettings(USER_3);
    }

    protected SettingsDto getUserSettings(ComponentTestUser user) {
        var typeRef = new TypeRef<SettingsDto>() {
        };
        return getOne(USER_SETTINGS_URL, user, typeRef);
    }
}
