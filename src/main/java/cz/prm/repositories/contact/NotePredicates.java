package cz.prm.repositories.contact;

import static cz.prm.domain.contact.querydsl.QNote.note;
import static cz.prm.security.SecurityContextUtils.getUser;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Component;

@Component
public class NotePredicates {

    public Predicate notes() {
        return dataAccessPredicate();
    }

    public Predicate byNoteId(Long noteId) {
        var predicate = dataAccessPredicate();
        return predicate.and(note.noteId.eq(noteId));
    }

    public Predicate byContactId(Long contactId) {
        var predicate = dataAccessPredicate();
        return predicate.and(note.contactId.eq(contactId));
    }

    private BooleanExpression dataAccessPredicate() {
        var user = getUser();
        return note.owner.username.eq(user.getUsername());
    }
}