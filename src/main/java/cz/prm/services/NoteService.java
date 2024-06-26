package cz.prm.services;

import static cz.prm.domain.common.PageRequests.getPageRequest;
import static java.lang.String.format;

import cz.prm.domain.common.query.Page;
import cz.prm.domain.note.Note;
import cz.prm.domain.note.query.NotesQuery;
import cz.prm.repositories.contact.NotePredicates;
import cz.prm.repositories.contact.NoteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class NoteService {

    private NoteRepository repository;
    private NotePredicates predicates;

    public NoteService(NoteRepository repository, NotePredicates predicates) {
        this.repository = repository;
        this.predicates = predicates;
    }

    public Page<Note> getNotes(Long contactId, NotesQuery query) {
        var pageRequest = getPageRequest(query.getPagination(), query.resolveSort());
        var predicate = predicates.byContactId(contactId);
        var page = repository.findAll(predicate, pageRequest);
        return new Page<>(page);
    }

    public Note getNote(Long noteId) {
        return getNoteById(noteId);
    }

    public void createNote(Note note) {
        repository.save(note);
    }

    public void updateNote(Long noteId, Note updatedNote) {
        var savedNote = getNoteById(noteId);
        updateNoteFields(savedNote, updatedNote);
        repository.save(savedNote);
    }

    public void deleteNote(Long noteId) {
        var savedNote = getNoteById(noteId);
        repository.deleteById(savedNote.getNoteId());
    }

    private void updateNoteFields(Note savedNote, Note updatedNote) {
        savedNote.setText(updatedNote.getText());
    }

    private Note getNoteById(Long noteId) {
        var predicate = predicates.byNoteId(noteId);
        var optional = repository.findOne(predicate);
        return optional.orElseThrow(entityNotFoundSupplier(noteId));
    }

    private Supplier<EntityNotFoundException> entityNotFoundSupplier(Long userId) {
        return () -> new EntityNotFoundException(format("Contact for given id=[%s] not found!", userId));
    }
}
