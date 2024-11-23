package cz.prm.utils.assertions;

import static cz.prm.utils.assertions.CommonAssertions.assertQuery;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.note.NoteDto;
import cz.prm.controllers.dto.note.query.NotesQueryDto;
import cz.prm.domain.common.query.Page;
import cz.prm.domain.note.Note;
import cz.prm.domain.note.query.NotesQuery;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.PageImpl;

public class NoteAssertions {

    public static void assertPage(Page<Note> page, PageDto<NoteDto> pageDto) {
        assertThat(page.getTotalPages()).isEqualTo(pageDto.getTotalPages());
        assertThat(page.getNumberOfElements()).isEqualTo(pageDto.getNumberOfElements());
        assertThat(page.getTotalElements()).isEqualTo(pageDto.getTotalElements());
        assertThat(page.getPageSize()).isEqualTo(pageDto.getPageSize());
        assertThat(page.getPageNumber()).isEqualTo(pageDto.getPageNumber());
        assertThat(page.getContent()).isNotEmpty().hasSameSizeAs(pageDto.getContent());
        page.getContent().forEach(contact -> {
            var dto = pageDto.getContent().stream().filter(c -> Objects.equals(contact.getContactId(), c.getContactId())).findFirst().get();
            assertNote(contact, dto);
        });
    }

    public static void assertPage(Page<Note> page1, PageImpl<Note> page2) {
        assertThat(page1.getTotalPages()).isEqualTo(page2.getTotalPages());
        assertThat(page1.getNumberOfElements()).isEqualTo(page2.getNumberOfElements());
        assertThat(page1.getTotalElements()).isEqualTo(page2.getTotalElements());
        assertThat(page1.getPageSize()).isEqualTo(page2.getSize());
        assertThat(page1.getPageNumber()).isEqualTo(page2.getNumber());
        assertThat(page1.getContent()).isNotEmpty().hasSameSizeAs(page2.getContent());
        page1.getContent().forEach(contact1 -> {
            var contact2 = page2.getContent().stream().filter(c -> Objects.equals(contact1.getContactId(), c.getContactId())).findFirst().get();
            assertNote(contact1, contact2);
        });
    }

    public static void assertNotes(List<Note> notes1, List<Note> notes2) {
        assertThat(notes1).isNotEmpty().hasSameSizeAs(notes2);
        notes1.forEach(c1 -> {
            var c2 = notes2.stream().filter(u -> Objects.equals(c1.getContactId(), u.getContactId())).findFirst().get();
            assertNote(c1, c2);
        });
    }

    public static void assertNotesDto(List<Note> notes, List<NoteDto> dtos) {
        assertThat(notes).isNotEmpty().hasSameSizeAs(dtos);
        notes.forEach(u1 -> {
            var u2 = dtos.stream().filter(u -> Objects.equals(u1.getContactId(), u.getContactId())).findFirst().get();
            assertNote(u1, u2);
        });
    }

    public static void assertNote(Note note1, Note note2) {
        assertThat(note1.getNoteId()).isEqualTo(note2.getNoteId());
        assertThat(note1.getContactId()).isEqualTo(note2.getContactId());
        assertThat(note1.getCategories()).isEqualTo(note2.getCategories());
        assertThat(note1.getText()).isEqualTo(note2.getText());
        assertThat(note1.getLastEditDate()).isEqualTo(note2.getLastEditDate());
        assertThat(note1.getCreationDate()).isEqualTo(note2.getCreationDate());
        assertThat(note1.getOwner()).isEqualTo(note2.getOwner());
    }

    public static void assertNote(Note note, NoteDto dto) {
        assertThat(note.getNoteId()).isEqualTo(dto.getNoteId());
        assertThat(note.getContactId()).isEqualTo(dto.getContactId());
        assertThat(note.getTitle()).isEqualTo(dto.getTitle());
        assertThat(note.getCategories()).isEqualTo(dto.getCategories());
        assertThat(note.getText()).isEqualTo(dto.getText());
        assertThat(note.getLastEditDate()).isEqualTo(dto.getLastEditDate());
        assertThat(note.getCreationDate()).isEqualTo(dto.getCreationDate());
    }

    public static void assertNotesQuery(NotesQuery query, NotesQueryDto dto) {
        assertQuery(query, dto);
    }
}
