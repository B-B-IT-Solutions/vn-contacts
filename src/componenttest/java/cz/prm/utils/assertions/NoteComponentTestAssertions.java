package cz.prm.utils.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.note.NoteDto;
import cz.prm.domain.note.Note;
import java.util.List;
import java.util.Objects;

public class NoteComponentTestAssertions {

    public static void assertNotes(List<Note> notes, PageDto<NoteDto> pageDto) {
        assertNotes(notes, pageDto.getContent());
    }

    public static void assertNotes(List<Note> notes, List<NoteDto> dtos) {
        assertThat(notes).isNotEmpty().hasSameSizeAs(dtos);
        notes.forEach(u1 -> {
            var u2 = dtos.stream().filter(u -> Objects.equals(u1.getContactId(), u.getContactId())).findFirst().get();
            assertNote(u1, u2);
        });
    }

    public static void assertNote(Note note, NoteDto noteDto) {
        assertThat(note.getNoteId()).isEqualTo(noteDto.getNoteId());
        assertThat(note.getContactId()).isEqualTo(noteDto.getContactId());
        assertThat(note.getText()).isEqualTo(noteDto.getText());
        assertThat(note.getLastEditDate()).isEqualTo(noteDto.getLastEditDate());
        assertThat(note.getCreationDate()).isEqualTo(noteDto.getCreationDate());
    }
}
