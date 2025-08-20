package cz.prm.repositories.customizations;

import cz.prm.domain.customizations.contact.ContactCustomizations;
import cz.prm.repositories.extensions.executors.PrmQuerydslPredicateExecutor;
import cz.prm.repositories.extensions.repositories.RefreshAwareRepository;

public interface ContactCustomizationsRepository extends RefreshAwareRepository<ContactCustomizations, Long>,
    PrmQuerydslPredicateExecutor<ContactCustomizations> {

}
