package cz.prm.repositories.contacts.note;

import cz.prm.domain.contacts.note.Note;
import cz.prm.repositories.extensions.executors.PrmQuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long>, PrmQuerydslPredicateExecutor<Note> {

}
