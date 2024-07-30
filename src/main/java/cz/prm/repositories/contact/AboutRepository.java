package cz.prm.repositories.contact;

import cz.prm.domain.contact.About;
import cz.prm.repositories.customisations.executors.PrmQuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AboutRepository extends JpaRepository<About, Long>, PrmQuerydslPredicateExecutor<About> {

    void deleteByContactId(Long contactId);
}
