package cz.prm.repositories.customizations;

import cz.prm.domain.customizations.note.NoteSettings;
import cz.prm.repositories.extensions.executors.PrmQuerydslPredicateExecutor;
import cz.prm.repositories.extensions.repositories.RefreshAwareRepository;

public interface NoteSettingsRepository extends RefreshAwareRepository<NoteSettings, Long>, PrmQuerydslPredicateExecutor<NoteSettings> {

}
