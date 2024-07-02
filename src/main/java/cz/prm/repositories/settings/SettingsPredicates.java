package cz.prm.repositories.settings;

import static cz.prm.domain.settings.querydsl.QUserSettings.userSettings;
import static cz.prm.security.SecurityContextUtils.getUser;

import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Component;

@Component
public class SettingsPredicates {

    public Predicate userSettings() {
        var user = getUser();
        return userSettings.owner.username.eq(user.getUsername());
    }
}