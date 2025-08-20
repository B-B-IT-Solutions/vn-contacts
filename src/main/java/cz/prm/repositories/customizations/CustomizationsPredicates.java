package cz.prm.repositories.customizations;

import static cz.prm.domain.customizations.contact.querydsl.QContactSettings.contactSettings;
import static cz.prm.domain.settings.note.querydsl.QNoteSettings.noteSettings;
import static cz.prm.security.SecurityContextUtils.getUser;

import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Component;

@Component
public class CustomizationsPredicates {

    public Predicate contactSettings() {
        var user = getUser();
        return contactSettings.owner.username.eq(user.getUsername());
    }

    public Predicate noteSettings() {
        var user = getUser();
        return noteSettings.owner.username.eq(user.getUsername());
    }
}