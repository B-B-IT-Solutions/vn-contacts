package cz.prm.repositories.contact;

import cz.prm.domain.contact.Contact;
import cz.prm.repositories.customisations.executors.PrmQuerydslPredicateExecutor;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactRepository extends JpaRepository<Contact, Long>, PrmQuerydslPredicateExecutor<Contact> {

    @Query("SELECT c FROM Contact c " +
        "WHERE (SIZE(c.productsAndServices) > 0 AND c.productsAndServices IN :offerings) " +
        "   OR (SIZE(c.skillsAndSpecialties) > 0 AND c.skillsAndSpecialties IN :skills) " +
        "   OR (SIZE(c.industries) > 0 AND c.industries IN :industries) " +
        "   OR (SIZE(c.targetMarkets) > 0 AND c.targetMarkets IN :markets)")
    List<Contact> findMatchingContacts(@Param("offerings") Set<String> offerings,
        @Param("skills") Set<String> skills,
        @Param("industries") Set<String> industries,
        @Param("markets") Set<String> markets);
}
