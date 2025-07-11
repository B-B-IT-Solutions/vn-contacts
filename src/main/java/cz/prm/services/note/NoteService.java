package cz.prm.services.note;

import static cz.prm.domain.common.PageRequests.getPageRequest;
import static java.lang.String.format;

import cz.prm.domain.common.query.Page;
import cz.prm.domain.note.Note;
import cz.prm.domain.note.query.NotesFilter;
import cz.prm.domain.note.query.NotesQuery;
import cz.prm.repositories.note.NotePredicates;
import cz.prm.repositories.note.NoteRepository;
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

    public Page<Note> getContactNotes(Long contactId, NotesQuery query) {
        var pageRequest = getPageRequest(query.getPagination(), query.resolveSort());
        var predicate = predicates.byContactId(contactId, query.getFilter());
        var page = repository.findAll(predicate, pageRequest);
        return new Page<>(page);
    }

    public Page<Note> getReferralNotes(Long referralId, NotesQuery query) {
        var pageRequest = getPageRequest(query.getPagination(), query.resolveSort());
        var predicate = predicates.byReferralId(referralId, query.getFilter());
        var page = repository.findAll(predicate, pageRequest);
        return new Page<>(page);
    }

    public Note getNote(Long noteId) {
        return getNoteById(noteId);
    }

    public Note createContactNote(Long contactId, Note note) {
        note.setContactId(contactId);
        return repository.save(note);
    }

    public Note createReferralNote(Long referralId, Note note) {
        note.setReferralId(referralId);
        return repository.save(note);
    }

    public Note updateNote(Long noteId, Note updatedNote) {
        var savedNote = getNoteById(noteId);
        updateNoteFields(savedNote, updatedNote);
        return repository.save(savedNote);
    }

    public void deleteNote(Long noteId) {
        var savedNote = getNoteById(noteId);
        repository.deleteById(savedNote.getNoteId());
    }

    public void deleteByContactId(Long contactId) {
        var predicate = predicates.byContactId(contactId, new NotesFilter());
        var notes = repository.findAll(predicate);
        repository.deleteAll(notes);
    }

    private void updateNoteFields(Note savedNote, Note updatedNote) {
        savedNote.setCategories(updatedNote.getCategories());
        savedNote.setText(updatedNote.getText());
    }

    private Note getNoteById(Long noteId) {
        var predicate = predicates.byNoteId(noteId);
        var optional = repository.findOne(predicate);
        return optional.orElseThrow(entityNotFoundSupplier(noteId));
    }

    private Supplier<EntityNotFoundException> entityNotFoundSupplier(Long userId) {
        return () -> new EntityNotFoundException(format("Note for given id=[%s] not found!", userId));
    }
}
