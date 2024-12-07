package cz.prm.repositories.note;

import static cz.prm.domain.note.querydsl.QNote.note;
import static cz.prm.repositories.common.query.PredicateCriteriaUtils.applyCriteria;
import static cz.prm.security.SecurityContextUtils.getUser;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import cz.prm.domain.note.query.NotesFilter;
import org.springframework.stereotype.Component;

@Component
public class NotePredicates {

    public Predicate byNoteId(Long noteId) {
        var predicate = dataAccessPredicate();
        return predicate.and(note.noteId.eq(noteId));
    }

    public Predicate byContactId(Long contactId, NotesFilter filter) {
        var predicate = notes(filter);
        return predicate.and(note.contactId.eq(contactId));
    }

    private BooleanExpression notes(NotesFilter filter) {
        var predicate = dataAccessPredicate();
        return predicate.and(filterPredicates(filter));
    }

    private BooleanExpression dataAccessPredicate() {
        var user = getUser();
        return note.owner.username.eq(user.getUsername());
    }

    private BooleanBuilder filterPredicates(NotesFilter filter) {
        var predicate = new BooleanBuilder();
        if (filter.isGlobalFilter()) {
            predicate.or(note.title.containsIgnoreCase(filter.getGlobalFilter()));
            predicate.or(note.text.containsIgnoreCase(filter.getGlobalFilter()));
        }
        if (filter.isTitle()) {
            applyCriteria(predicate, note.title, filter.getTitle());
        }
        if (filter.isCategories()) {
            filter.getCategories().forEach((c) -> {
                predicate.or(note.categories.contains(c));
            });
        }
        return predicate;
    }
}