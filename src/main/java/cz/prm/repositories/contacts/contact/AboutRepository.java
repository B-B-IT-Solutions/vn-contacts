package cz.prm.repositories.contacts.contact;

import cz.prm.domain.contacts.contact.About;
import cz.prm.repositories.extensions.executors.PrmQuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AboutRepository extends JpaRepository<About, Long>, PrmQuerydslPredicateExecutor<About> {

}
