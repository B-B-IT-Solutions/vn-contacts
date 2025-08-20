package cz.prm.repositories.settings;

import cz.prm.domain.settings.contact.ContactSettings;
import cz.prm.repositories.extensions.executors.PrmQuerydslPredicateExecutor;
import cz.prm.repositories.extensions.repositories.RefreshAwareRepository;

public interface ContactSettingsRepository extends RefreshAwareRepository<ContactSettings, Long>, PrmQuerydslPredicateExecutor<ContactSettings> {

}
