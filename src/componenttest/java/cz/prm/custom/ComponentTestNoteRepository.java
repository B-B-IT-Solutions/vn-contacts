package cz.prm.custom;

import cz.prm.domain.note.Note;
import cz.prm.repositories.contact.NoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;

@Primary
@Transactional
public interface ComponentTestNoteRepository extends NoteRepository {

    Note getByText(String text);
}
