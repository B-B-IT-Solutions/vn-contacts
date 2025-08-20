package cz.prm.repositories.settings;

import cz.prm.domain.settings.note.NoteSettings;
import cz.prm.repositories.extensions.executors.PrmQuerydslPredicateExecutor;
import cz.prm.repositories.extensions.repositories.RefreshAwareRepository;

public interface NoteSettingsRepository extends RefreshAwareRepository<NoteSettings, Long>, PrmQuerydslPredicateExecutor<NoteSettings> {

}
