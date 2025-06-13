package cz.prm.repositories.referral;

import cz.prm.domain.referral.Referral;
import cz.prm.repositories.customisations.executors.PrmQuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferralRepository extends JpaRepository<Referral, Long>, PrmQuerydslPredicateExecutor<Referral> {

}
