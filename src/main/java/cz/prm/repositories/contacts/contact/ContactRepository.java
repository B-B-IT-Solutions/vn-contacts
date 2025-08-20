package cz.prm.repositories.contacts.contact;

import cz.prm.domain.contacts.contact.Contact;
import cz.prm.repositories.extensions.executors.PrmQuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long>, PrmQuerydslPredicateExecutor<Contact> {

}
