package cz.prm.repositories.settings;

import static cz.prm.domain.settings.querydsl.QContactSettings.contactSettings;
import static cz.prm.domain.settings.querydsl.QGeneralSettings.generalSettings;
import static cz.prm.security.SecurityContextUtils.getUser;

import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Component;

@Component
public class SettingsPredicates {

    private static final long GENERAL_SETTINGS_ID = 1L;

    public Predicate generalSettings() {
        return generalSettings.settingsId.eq(GENERAL_SETTINGS_ID);
    }

    public Predicate userSettings() {
        var user = getUser();
        return contactSettings.owner.username.eq(user.getUsername());
    }
}