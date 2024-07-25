package cz.prm.repositories.settings;

import static cz.prm.domain.settings.querydsl.QAccountSettings.accountSettings;
import static cz.prm.domain.settings.querydsl.QContactSettings.contactSettings;
import static cz.prm.security.SecurityContextUtils.getUser;

import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Component;

@Component
public class SettingsPredicates {

    private static final long ACCOUNT_SETTINGS_ID = 1L;

    public Predicate accountSettings() {
        return accountSettings.settingsId.eq(ACCOUNT_SETTINGS_ID);
    }

    public Predicate userSettings() {
        var user = getUser();
        return contactSettings.owner.username.eq(user.getUsername());
    }
}