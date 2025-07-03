package cz.prm.utils.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.note.NoteDto;
import cz.prm.domain.note.Note;
import java.util.List;
import java.util.Objects;

public class NoteComponentTestAssertions {

    public static void assertContactNotes(List<Note> notes, PageDto<NoteDto> pageDto) {
        assertContactNotes(notes, pageDto.getContent());
    }

    public static void assertReferralNotes(List<Note> notes, PageDto<NoteDto> pageDto) {
        assertReferralNotes(notes, pageDto.getContent());
    }

    public static void assertContactNotes(List<Note> notes, List<NoteDto> dtos) {
        assertThat(notes).isNotEmpty().hasSameSizeAs(dtos);
        notes.forEach(n1 -> {
            var n2 = dtos.stream().filter(n -> Objects.equals(n1.getNoteId(), n.getNoteId())).findFirst().get();
            assertContactNote(n1, n2);
        });
    }

    public static void assertReferralNotes(List<Note> notes, List<NoteDto> dtos) {
        assertThat(notes).isNotEmpty().hasSameSizeAs(dtos);
        notes.forEach(n1 -> {
            var n2 = dtos.stream().filter(n -> Objects.equals(n1.getNoteId(), n.getNoteId())).findFirst().get();
            assertReferralNote(n1, n2);
        });
    }

    public static void assertContactNote(Note note, NoteDto noteDto) {
        assertNote(note, noteDto);
        assertThat(note.getContactId()).isNotNull();
        assertThat(note.getReferralId()).isNull();
    }

    public static void assertReferralNote(Note note, NoteDto noteDto) {
        assertNote(note, noteDto);
        assertThat(note.getContactId()).isNull();
        assertThat(note.getReferralId()).isNotNull();
    }

    public static void assertNote(Note note, NoteDto noteDto) {
        assertThat(note.getNoteId()).isEqualTo(noteDto.getNoteId());
        assertThat(note.getCategories()).isNotEmpty().containsExactlyElementsOf(noteDto.getCategories());
        assertThat(note.getText()).isEqualTo(noteDto.getText());
        assertThat(note.getLastEditDate()).isNotNull();
        assertThat(note.getCreationDate()).isNotNull();
    }
}
