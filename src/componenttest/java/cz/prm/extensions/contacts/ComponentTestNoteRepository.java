package cz.prm.extensions.contacts;

import cz.prm.domain.contacts.note.Note;
import cz.prm.repositories.contacts.note.NoteRepository;
import org.springframework.context.annotation.Primary;

@Primary
public interface ComponentTestNoteRepository extends NoteRepository {

    Note getByText(String text);
}
