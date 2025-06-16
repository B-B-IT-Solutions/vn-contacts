package cz.prm.custom;

import cz.prm.domain.referral.Referral;
import cz.prm.repositories.referral.ReferralRepository;
import org.springframework.context.annotation.Primary;

@Primary
public interface ComponentTestReferralRepository extends ReferralRepository {

    Referral getByNote(String note);
}
