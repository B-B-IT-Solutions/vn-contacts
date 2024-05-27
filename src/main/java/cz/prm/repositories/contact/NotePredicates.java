package cz.prm.repositories.contact;

import static cz.prm.domain.contact.querydsl.QNote.note;
import static cz.prm.security.SecurityContextUtils.getUsername;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Component;

@Component
public class NotePredicates {

    public Predicate notes() {
        return dataAccessPredicate();
    }

    public Predicate byContactId(Long userId) {
        var predicate = dataAccessPredicate();
        return predicate.and(note.contactId.eq(userId));
    }

    private BooleanExpression dataAccessPredicate() {
        var username = getUsername();
        return note.owner.eq(username);
    }
}