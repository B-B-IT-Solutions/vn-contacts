package cz.prm.repositories.referral;

import static cz.prm.domain.referral.querydsl.QReferral.referral;
import static cz.prm.security.SecurityContextUtils.getUser;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Component;

@Component
public class ReferralPredicates {

    public Predicate byContactId(Long contactId) {
        var predicate = dataAccessPredicate();
        return predicate.and(referral.contactId.eq(contactId));
    }

    public Predicate referrals() {
        return dataAccessPredicate();
    }

    public Predicate byReferralId(Long referralId) {
        var predicate = dataAccessPredicate();
        return predicate.and(referral.referralId.eq(referralId));
    }

    private BooleanExpression dataAccessPredicate() {
        var user = getUser();
        return referral.owner.username.eq(user.getUsername());
    }
}