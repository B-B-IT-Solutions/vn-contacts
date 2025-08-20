package cz.prm.repositories.customizations;

import cz.prm.domain.customizations.contact.ContactSettings;
import cz.prm.repositories.extensions.executors.PrmQuerydslPredicateExecutor;
import cz.prm.repositories.extensions.repositories.RefreshAwareRepository;

public interface ContactSettingsRepository extends RefreshAwareRepository<ContactSettings, Long>, PrmQuerydslPredicateExecutor<ContactSettings> {

}
