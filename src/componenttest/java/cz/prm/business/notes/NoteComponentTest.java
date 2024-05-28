package cz.prm.business.notes;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static cz.prm.utils.ComponentTestUtils.uuid;
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

    @Test
    void updateNote() {
        var note = createNote(USER_1);
        var noteId = note.getNoteId();
        var updateDto = user1GetNote(noteId);

        updateDto.setText(uuid());
        user1UpdateNote(noteId, updateDto);
        note = getNoteFromDb(updateDto);
        assertNote(note, updateDto);

        user2UpdateNoteExpectNotFound(noteId, updateDto);
        user3UpdateNoteExpectNotFound(noteId, updateDto);

        note = createNote(USER_2);
        noteId = note.getNoteId();
        updateDto = user2GetNote(noteId);

        updateDto.setText(uuid());
        user2UpdateNote(noteId, updateDto);
        note = getNoteFromDb(updateDto);
        assertNote(note, updateDto);

        user1UpdateNoteExpectNotFound(noteId, updateDto);
        user3UpdateNoteExpectNotFound(noteId, updateDto);

        note = createNote(USER_3);
        noteId = note.getNoteId();
        updateDto = user3GetNote(noteId);

        updateDto.setText(uuid());
        user3UpdateNote(noteId, updateDto);
        note = getNoteFromDb(updateDto);
        assertNote(note, updateDto);

        user1UpdateNoteExpectNotFound(noteId, updateDto);
        user2UpdateNoteExpectNotFound(noteId, updateDto);
    }
}
