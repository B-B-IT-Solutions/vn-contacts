package cz.prm.controllers;

import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.NoteUtils.note;
import static cz.prm.utils.NoteUtils.noteDto;
import static cz.prm.utils.NoteUtils.notes;
import static cz.prm.utils.NoteUtils.notesQueryDto;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.assertions.NoteAssertions.assertNote;
import static cz.prm.utils.assertions.NoteAssertions.assertNotesQuery;
import static cz.prm.utils.assertions.NoteAssertions.assertPage;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.prm.controllers.mappers.NoteMapper;
import cz.prm.domain.note.Note;
import cz.prm.domain.note.query.NotesQuery;
import cz.prm.services.note.NoteService;
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
        var dto = noteDto();
        var contactId = randomLong();
        controller.createContactNote(contactId, dto);
        verify(noteService).createContactNote(eq(contactId), noteCapt.capture());
        var note = noteCapt.getValue();
        assertNote(note, dto);
    }

    @Test
    void createReferralNote() {
        var dto = noteDto();
        var referralId = randomLong();
        controller.createReferralNote(referralId, dto);
        verify(noteService).createReferralNote(eq(referralId), noteCapt.capture());
        var note = noteCapt.getValue();
        assertNote(note, dto);
    }

    @Test
    void updateNote() {
        var dto = noteDto();
        controller.updateNote(dto.getNoteId(), dto);
        verify(noteService).updateNote(eq(dto.getNoteId()), noteCapt.capture());
        var note = noteCapt.getValue();
        assertNote(note, dto);
    }

    @Test
    void deleteNote() {
        var noteId = randomLong();
        controller.deleteNote(noteId);
        verify(noteService).deleteNote(noteId);
    }
}