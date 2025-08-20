package cz.prm.custom;

import cz.prm.domain.contacts.referral.Referral;
import cz.prm.repositories.contacts.referral.ReferralRepository;
import org.springframework.context.annotation.Primary;

@Primary
public interface ComponentTestReferralRepository extends ReferralRepository {

    Referral getByNote(String note);
}
