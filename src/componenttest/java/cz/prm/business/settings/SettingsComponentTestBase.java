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
    protected static String SETTINGS_URL = SETTINGS_BASE_URL;

    protected SettingsDto user1GetSettings() {
        return getSettings(USER_1);
    }

    protected SettingsDto user2GetSettings() {
        return getSettings(USER_2);
    }

    protected SettingsDto user3GetSettings() {
        return getSettings(USER_3);
    }

    protected SettingsDto getSettings(ComponentTestUser user) {
        var typeRef = new TypeRef<SettingsDto>() {
        };
        return getOne(SETTINGS_URL, user, typeRef);
    }
}
