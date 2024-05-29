package cz.prm.repositories.contact;

import cz.prm.domain.note.Note;
import cz.prm.repositories.customisations.executors.PrmQuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long>, PrmQuerydslPredicateExecutor<Note> {

}
