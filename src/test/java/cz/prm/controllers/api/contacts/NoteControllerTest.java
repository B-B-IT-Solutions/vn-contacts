package cz.prm.controllers.api.contacts;

import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.MockitoUtils.returnParamAnswer;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.assertions.contacts.NoteAssertions.assertNote;
import static cz.prm.utils.assertions.contacts.NoteAssertions.assertNotesQuery;
import static cz.prm.utils.assertions.contacts.NoteAssertions.assertPage;
import static cz.prm.utils.data.contacts.NoteUtils.note;
import static cz.prm.utils.data.contacts.NoteUtils.noteDto;
import static cz.prm.utils.data.contacts.NoteUtils.notes;
import static cz.prm.utils.data.contacts.NoteUtils.notesQueryDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.prm.controllers.mapper.contacts.NoteMapper;
import cz.prm.domain.contacts.note.Note;
import cz.prm.domain.contacts.note.query.NotesQuery;
import cz.prm.services.contacts.note.NoteService;
import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NoteControllerTest {

    @Mock
    private NoteService noteService;
    @Captor
    private ArgumentCaptor<Note> noteCapt;
    @Captor
    private ArgumentCaptor<NotesQuery> cQueryCapt;

    private NoteMapper mapper = MapperUtils.getNoteMapper();
    private NoteController controller;

    @BeforeEach
    void setUp() {
        controller = new NoteController(noteService, mapper);
    }

    @Test
    void getContactNotes() {
        var page = page(notes());
        var queryDto = notesQueryDto();
        var contactId = randomLong();
        when(noteService.getContactNotes(eq(contactId), any(NotesQuery.class))).thenReturn(page);

        var result = controller.getContactNotes(contactId, queryDto);
        assertPage(page, result);
        verify(noteService).getContactNotes(eq(contactId), cQueryCapt.capture());
        var query = cQueryCapt.getValue();
        assertNotesQuery(query, queryDto);
    }

    @Test
    void getReferralNotes() {
        var page = page(notes());
        var queryDto = notesQueryDto();
        var referralId = randomLong();
        when(noteService.getReferralNotes(eq(referralId), any(NotesQuery.class))).thenReturn(page);

        var result = controller.getReferralNotes(referralId, queryDto);
        assertPage(page, result);
        verify(noteService).getReferralNotes(eq(referralId), cQueryCapt.capture());
        var query = cQueryCapt.getValue();
        assertNotesQuery(query, queryDto);
    }

    @Test
    void getNote() {
        var note = note();
        var noteId = note.getNoteId();
        when(noteService.getNote(noteId)).thenReturn(note);
        var result = controller.getNote(noteId);
        assertNote(note, result);
    }

    @Test
    void createContactNote() {
        var addedDto = noteDto();
        var contactId = randomLong();
        when(noteService.createContactNote(eq(contactId), any(Note.class))).thenAnswer(returnParamAnswer(1));

        var responseDto = controller.createContactNote(contactId, addedDto);
        verify(noteService).createContactNote(eq(contactId), noteCapt.capture());
        var note = noteCapt.getValue();
        assertNote(note, addedDto);
        assertNote(note, responseDto);
    }

    @Test
    void createReferralNote() {
        var addedDto = noteDto();
        var referralId = randomLong();
        when(noteService.createReferralNote(eq(referralId), any(Note.class))).thenAnswer(returnParamAnswer(1));

        var responseDto = controller.createReferralNote(referralId, addedDto);
        verify(noteService).createReferralNote(eq(referralId), noteCapt.capture());
        var note = noteCapt.getValue();
        assertNote(note, addedDto);
        assertNote(note, responseDto);
    }

    @Test
    void updateNote() {
        var updatedDto = noteDto();
        var noteId = updatedDto.getNoteId();
        when(noteService.updateNote(eq(noteId), any(Note.class))).thenAnswer(returnParamAnswer(1));

        var responseDto = controller.updateNote(noteId, updatedDto);
        verify(noteService).updateNote(eq(noteId), noteCapt.capture());
        var note = noteCapt.getValue();
        assertNote(note, updatedDto);
        assertNote(note, responseDto);
    }

    @Test
    void deleteNote() {
        var noteId = randomLong();
        controller.deleteNote(noteId);
        verify(noteService).deleteNote(noteId);
    }
}