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
import cz.prm.services.contact.NoteService;
import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotesControllerTest {

    @Mock
    private NoteService noteService;
    @Captor
    private ArgumentCaptor<Note> noteCapt;
    @Captor
    private ArgumentCaptor<NotesQuery> cQueryCapt;

    private NoteMapper mapper = MapperUtils.getNoteMapper();
    private NotesController controller;

    @BeforeEach
    void setUp() {
        controller = new NotesController(noteService, mapper);
    }

    @Test
    void getNotes() {
        var page = page(notes());
        var queryDto = notesQueryDto();
        var contactId = randomLong();
        when(noteService.getNotes(eq(contactId), any(NotesQuery.class))).thenReturn(page);

        var result = controller.getNotes(contactId, queryDto);
        assertPage(page, result);
        verify(noteService).getNotes(eq(contactId), cQueryCapt.capture());
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
    void createNote() {
        var dto = noteDto();
        controller.createNote(dto);
        verify(noteService).createNote(noteCapt.capture());
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
}