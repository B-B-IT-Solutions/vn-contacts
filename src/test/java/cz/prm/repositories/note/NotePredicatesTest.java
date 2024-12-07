package cz.prm.repositories.note;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.CommonUtils.user;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.note.query.NotesFilter;
import cz.prm.security.SecurityContextUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class NotePredicatesTest {

    private NotePredicates predicates;

    @BeforeEach
    void setUp() {
        predicates = new NotePredicates();
    }

    @Test
    void byNoteId() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var query = predicates.byNoteId(10L);
            var expectedString = format("note.owner.username = %s && note.noteId = 10", user.getUsername());
            assertThat(query).hasToString(expectedString);
        }
    }

    @Test
    void byContactIdNoFilters() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            var filter = new NotesFilter();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var query = predicates.byContactId(11L, filter);
            var expectedString = format("note.owner.username = %s && note.contactId = 11", user.getUsername());
            assertThat(query).hasToString(expectedString);
        }
    }

    @Test
    void byContactIdWithFilters() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            var filter = new NotesFilter();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var predicate = predicates.byContactId(15L, filter);
            var expectedString = format("note.owner.username = %s && note.contactId = 15", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setGlobalFilter("searchText_01");
            predicate = predicates.byContactId(16L, filter);
            expectedString = format("note.owner.username = %s && (containsIc(note.title,searchText_01) || containsIc"
                + "(note.text,searchText_01)) && note.contactId = 16", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setGlobalFilter("searchText_02");
            predicate = predicates.byContactId(17L, filter);
            expectedString = format("note.owner.username = %s && (containsIc(note.title,searchText_02) || containsIc"
                + "(note.text,searchText_02)) && note.contactId = 17", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setGlobalFilter(null);
            filter.setCategories(newArrayList("category_1", "category_2", "category_3"));
            predicate = predicates.byContactId(17L, filter);
            expectedString = format(
                "note.owner.username = %s && (category_1 in note.categories || category_2 in note.categories || category_3 in note.categories) && "
                    + "note.contactId = 17", user.getUsername());
            assertThat(predicate).hasToString(expectedString);
        }
    }
}