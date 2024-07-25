package cz.prm.repositories.settings;

import cz.prm.domain.settings.ContactSettings;
import cz.prm.repositories.customisations.executors.PrmQuerydslPredicateExecutor;
import cz.prm.repositories.customisations.repositories.RefreshAwareRepository;

public interface ContactSettingsRepository extends RefreshAwareRepository<ContactSettings, Long>, PrmQuerydslPredicateExecutor<ContactSettings> {

}
