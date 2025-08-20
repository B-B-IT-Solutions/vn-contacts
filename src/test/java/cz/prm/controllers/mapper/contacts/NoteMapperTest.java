package cz.prm.controllers.mapper.contacts;

import static cz.prm.utils.CommonUtils.DEFAULT_PAGE_SIZE;
import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.assertions.NoteAssertions.assertNote;
import static cz.prm.utils.assertions.NoteAssertions.assertNotesQuery;
import static cz.prm.utils.assertions.NoteAssertions.assertPage;
import static cz.prm.utils.data.contacts.NoteUtils.note;
import static cz.prm.utils.data.contacts.NoteUtils.noteDto;
import static cz.prm.utils.data.contacts.NoteUtils.notes;
import static cz.prm.utils.data.contacts.NoteUtils.notesQueryDto;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.contacts.note.query.NotesQueryDto;
import cz.prm.domain.contacts.note.query.NotesQuery;
import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.Test;

class NoteMapperTest {

    public static final String DEFAULT_NOTES_SORT = "desc(creationDate)";

    private NoteMapper mapper = MapperUtils.getNoteMapper();

    @Test
    void toPageDto() {
        var page = page(notes());
        var dtos = mapper.toPageDto(page);
        assertPage(page, dtos);
    }

    @Test
    void toNoteDto() {
        var note = note();
        var dto = mapper.toNoteDto(note);
        assertNote(note, dto);
    }

    @Test
    void toNote() {
        var dto = noteDto();
        var note = mapper.toNote(dto);
        assertNote(note, dto);
    }

    @Test
    void toNotesQuery() {
        var dto = notesQueryDto();
        var query = mapper.toNotesQuery(dto);
        assertNotesQuery(query, dto);
    }

    @Test
    void toNullSafeNotesQueryNullQuery() {
        var query = mapper.toNullSafeNotesQuery(null);
        assertNullSafeNoteQuery(query);
    }

    @Test
    void toNullSafeNotesQueryNotNullQuery() {
        var dto = notesQueryDto();
        var query = mapper.toNullSafeNotesQuery(dto);
        assertNotesQuery(query, dto);
    }

    @Test
    void toNullSafeNotesQueryNullPagination() {
        var dto = new NotesQueryDto();
        dto.setPagination(null);
        var query = mapper.toNullSafeNotesQuery(dto);
        assertNullSafeNoteQuery(query);
    }

    @Test
    void afterNotesQuery() {
        var target = new NotesQuery();
        target.setPagination(null);
        target.setFilter(null);
        mapper.afterNotesQuery(null, target);
        assertNullSafeNoteQuery(target);
    }

    private void assertNullSafeNoteQuery(NotesQuery query) {
        assertThat(query.getPagination()).isNotNull();
        assertThat(query.getFilter()).isNotNull();
        assertThat(query.getSort()).isEqualTo(DEFAULT_NOTES_SORT);
        var pagination = query.getPagination();
        assertThat(pagination.getPageNumber()).isZero();
        assertThat(pagination.getPageSize()).isEqualTo(DEFAULT_PAGE_SIZE);
    }
}