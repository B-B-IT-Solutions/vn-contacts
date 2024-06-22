package cz.prm.services;

import static cz.prm.utils.NoteUtils.note;
import static cz.prm.utils.NoteUtils.notes;
import static cz.prm.utils.NoteUtils.notesQuery;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.assertions.NoteAssertions.assertNote;
import static cz.prm.utils.assertions.NoteAssertions.assertPage;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.querydsl.core.BooleanBuilder;
import cz.prm.domain.note.Note;
import cz.prm.repositories.contact.NotePredicates;
import cz.prm.repositories.contact.NoteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository repository;
    @Mock
    private NotePredicates predicates;
    @Captor
    private ArgumentCaptor<Note> noteCapt;

    private NoteService noteService;

    @BeforeEach
    void setUp() {
        noteService = new NoteService(repository, predicates);
    }

    @Test
    void getNotes() {
        var notes = notes();
        var page = new PageImpl(notes);
        var query = notesQuery();
        var contactId = randomLong();
        var predicate = new BooleanBuilder();

        when(predicates.byContactId(contactId)).thenReturn(predicate);
        when(repository.findAll(eq(predicate), any(PageRequest.class))).thenReturn(page);
        var result = noteService.getNotes(contactId, query);
        assertPage(result, page);
    }

    @Test
    void getNote() {
        var note = note();
        var predicate = new BooleanBuilder();
        when(predicates.byNoteId(note.getNoteId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(note));
        var result = noteService.getNote(note.getNoteId());
        assertNote(result, note);
    }

    @Test
    void getNote_EntityNotFound() {
        var note = note();
        var predicate = new BooleanBuilder();
        when(predicates.byNoteId(note.getNoteId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> noteService.getNote(note.getNoteId()));
    }

    @Test
    void createNote() {
        var note = note();
        noteService.createNote(note);
        verify(repository).save(note);
    }

    @Test
    void updateNote() {
        var noteIdDb = note();
        var updatedNote = note();
        var predicate = new BooleanBuilder();
        when(predicates.byNoteId(noteIdDb.getNoteId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(noteIdDb));

        noteService.updateNote(noteIdDb.getNoteId(), updatedNote);
        verify(repository).save(noteCapt.capture());
        var savedNote = noteCapt.getValue();
        assertFieldsUpdated(noteIdDb, updatedNote, savedNote);
    }

    @Test
    void updateNote_EntityNotFound() {
        var noteIdDb = note();
        var updatedNote = note();
        var predicate = new BooleanBuilder();
        when(predicates.byNoteId(noteIdDb.getNoteId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> noteService.updateNote(noteIdDb.getNoteId(), updatedNote));
    }

    private static void assertFieldsUpdated(Note noteIdDb, Note updatedNote, Note savedNote) {
        assertThat(noteIdDb.getNoteId()).isEqualTo(savedNote.getNoteId());
        assertThat(noteIdDb.getContactId()).isEqualTo(savedNote.getContactId());
        assertThat(noteIdDb.getOwner()).isEqualTo(savedNote.getOwner());
        assertThat(noteIdDb.getCreationDate()).isEqualTo(savedNote.getCreationDate());
        assertThat(savedNote.getText()).isEqualTo(updatedNote.getText());
        assertThat(savedNote.getOrder()).isEqualTo(updatedNote.getOrder());
    }
}