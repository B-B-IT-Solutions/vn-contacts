package cz.prm.repositories.settings;

import cz.prm.domain.settings.UserSettings;
import cz.prm.repositories.customisations.executors.PrmQuerydslPredicateExecutor;
import cz.prm.repositories.customisations.repositories.RefreshAwareRepository;

public interface UserSettingsRepository extends RefreshAwareRepository<UserSettings, Long>, PrmQuerydslPredicateExecutor<UserSettings> {

}
