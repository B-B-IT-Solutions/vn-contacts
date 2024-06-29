package cz.prm.repositories.settings;

import cz.prm.domain.settings.Settings;
import cz.prm.repositories.customisations.executors.PrmQuerydslPredicateExecutor;
import cz.prm.repositories.customisations.repositories.RefreshAwareRepository;

public interface SettingsRepository extends RefreshAwareRepository<Settings, Long>, PrmQuerydslPredicateExecutor<Settings> {

}
