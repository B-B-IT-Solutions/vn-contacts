package cz.prm.custom;

import cz.prm.domain.note.Note;
import cz.prm.repositories.note.NoteRepository;
import org.springframework.context.annotation.Primary;

@Primary
public interface ComponentTestNoteRepository extends NoteRepository {

    Note getByText(String text);
}
