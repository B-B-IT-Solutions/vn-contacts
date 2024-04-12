package cz.prm.repositories.user;

import cz.prm.domain.contact.Contact;
import cz.prm.repositories.customisations.executors.PrmQuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long>, PrmQuerydslPredicateExecutor<Contact> {

}
