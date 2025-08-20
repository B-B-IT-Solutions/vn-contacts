package cz.prm.repositories.customizations;

import cz.prm.domain.customizations.contact.ContactCustomizations;
import cz.prm.repositories.extensions.executors.PrmQuerydslPredicateExecutor;
import cz.prm.repositories.extensions.repositories.RefreshAwareRepository;

public interface ContactSettingsRepository extends RefreshAwareRepository<ContactCustomizations, Long>, PrmQuerydslPredicateExecutor<ContactCustomizations> {

}
