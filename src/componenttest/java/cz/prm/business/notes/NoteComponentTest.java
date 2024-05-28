package cz.prm.business.notes;

import static cz.prm.utils.NoteComponentTestUtils.noteDto;
import static cz.prm.utils.assertions.NoteComponentTestAssertions.assertNote;

import org.junit.jupiter.api.Test;

public class NoteComponentTest extends NoteComponentTestBase {

    @Test
    void createNote() {
        var toCreateDto = noteDto();
        user1CreateNote(toCreateDto);
        var note = getNoteFromDb(toCreateDto);
        var noteId = note.getNoteId();

        var createdDto = user1GetNote(noteId);
        assertNote(note, createdDto);
        user2GetNoteExpectNotFound(noteId);
        user3GetNoteExpectNotFound(noteId);

        toCreateDto = noteDto();
        user2CreateNote(toCreateDto);
        note = getNoteFromDb(toCreateDto);
        noteId = note.getNoteId();

        createdDto = user2GetNote(noteId);
        assertNote(note, createdDto);
        user1GetNoteExpectNotFound(noteId);
        user3GetNoteExpectNotFound(noteId);

        toCreateDto = noteDto();
        user3CreateNote(toCreateDto);
        note = getNoteFromDb(toCreateDto);
        noteId = note.getNoteId();

        createdDto = user3GetNote(noteId);
        assertNote(note, createdDto);
        user1GetNoteExpectNotFound(noteId);
        user2GetNoteExpectNotFound(noteId);
    }
}
