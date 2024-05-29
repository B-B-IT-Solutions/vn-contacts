package cz.prm.business.notes;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static cz.prm.utils.ComponentTestUtils.randomLong;
import static cz.prm.utils.ComponentTestUtils.uuid;
import static cz.prm.utils.NoteComponentTestUtils.noteDto;
import static cz.prm.utils.NoteComponentTestUtils.notesQueryDto;
import static cz.prm.utils.assertions.NoteComponentTestAssertions.assertNote;
import static cz.prm.utils.assertions.NoteComponentTestAssertions.assertNotes;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class NoteComponentTest extends NoteComponentTestBase {

    @Test
    void getNotesDataAccess() {
        var queryDto = notesQueryDto();
        var contactId = randomLong();
        var pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user2GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        contactId = randomLong();
        var user1Notes = createNotes(USER_1, contactId);
        pageDto = user1GetNotes(contactId, queryDto);
        assertNotes(user1Notes, pageDto);

        pageDto = user2GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        contactId = randomLong();
        var user2Notes = createNotes(USER_2, contactId);
        pageDto = user2GetNotes(contactId, queryDto);
        assertNotes(user2Notes, pageDto);

        pageDto = user1GetNotes(contactId, queryDto);
        assertNotes(user1Notes, pageDto);

        pageDto = user3GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        contactId = randomLong();
        var user3Notes = createNotes(USER_3, contactId);
        pageDto = user3GetNotes(contactId, queryDto);
        assertNotes(user3Notes, pageDto);

        pageDto = user1GetNotes(contactId, queryDto);
        assertNotes(user1Notes, pageDto);

        pageDto = user2GetNotes(contactId, queryDto);
        assertNotes(user2Notes, pageDto);
    }

    @Test
    void getNote() {
        var note = createNote(USER_1);
        var noteId = note.getNoteId();

        var noteDto = user1GetNote(noteId);
        assertNote(note, noteDto);
        user2GetNoteExpectNotFound(noteId);
        user3GetNoteExpectNotFound(noteId);

        note = createNote(USER_2);
        noteId = note.getNoteId();
        noteDto = user2GetNote(noteId);
        assertNote(note, noteDto);
        user1GetNoteExpectNotFound(noteId);
        user3GetNoteExpectNotFound(noteId);

        note = createNote(USER_3);
        noteId = note.getNoteId();
        noteDto = user3GetNote(noteId);
        assertNote(note, noteDto);
        user1GetNoteExpectNotFound(noteId);
        user2GetNoteExpectNotFound(noteId);
    }

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
