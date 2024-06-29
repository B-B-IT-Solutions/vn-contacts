package cz.prm.repositories.settings;

import static cz.prm.domain.settings.querydsl.QSettings.settings;
import static cz.prm.security.SecurityContextUtils.getUser;

import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Component;

@Component
public class SettingsPredicates {

    public Predicate settings() {
        var user = getUser();
        return settings.owner.username.eq(user.getUsername());
    }
}