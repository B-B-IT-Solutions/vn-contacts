package cz.prm.repositories.contacts.referral;

import cz.prm.domain.contacts.referral.Referral;
import cz.prm.repositories.extensions.executors.PrmQuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferralRepository extends JpaRepository<Referral, Long>, PrmQuerydslPredicateExecutor<Referral> {

}
