package cz.prm.custom;

import cz.prm.repositories.customizations.NoteSettingsRepository;
import org.springframework.context.annotation.Primary;

@Primary
public interface ComponentTestNoteSettingsRepository extends NoteSettingsRepository {

}
