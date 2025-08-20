package cz.prm.repositories.customizations;

import static cz.prm.domain.customizations.contact.querydsl.QContactCustomizations.contactCustomizations;
import static cz.prm.domain.customizations.note.querydsl.QNoteCustomizations.noteCustomizations;
import static cz.prm.security.SecurityContextUtils.getUser;

import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Component;

@Component
public class CustomizationsPredicates {

    public Predicate contactSettings() {
        var user = getUser();
        return contactCustomizations.owner.username.eq(user.getUsername());
    }

    public Predicate noteSettings() {
        var user = getUser();
        return noteCustomizations.owner.username.eq(user.getUsername());
    }
}