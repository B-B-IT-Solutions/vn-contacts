package cz.prm.repositories.referral;

import static cz.prm.domain.contacts.referral.querydsl.QReferral.referral;
import static cz.prm.repositories.common.query.PredicateCriteriaUtils.applyCriteria;
import static cz.prm.security.SecurityContextUtils.getUser;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import cz.prm.domain.contacts.referral.query.ReferralsFilter;
import org.springframework.stereotype.Component;

@Component
public class ReferralPredicates {

    public Predicate contactReferrals(Long contactId, ReferralsFilter filter) {
        var predicate = new BooleanBuilder();
        predicate.and(referrals(filter));
        return predicate.and(referral.contactId.eq(contactId));
    }

    public Predicate referrals(ReferralsFilter filter) {
        var predicate = dataAccessPredicate();
        return predicate.and(filterPredicates(filter));
    }

    public Predicate byReferralId(Long referralId) {
        var predicate = dataAccessPredicate();
        return predicate.and(referral.referralId.eq(referralId));
    }

    private BooleanExpression dataAccessPredicate() {
        var user = getUser();
        return referral.owner.username.eq(user.getUsername());
    }

    private BooleanBuilder filterPredicates(ReferralsFilter filter) {
        var predicate = new BooleanBuilder();
        if (filter.isGlobalFilter()) {
            predicate.or(referral.name.containsIgnoreCase(filter.getGlobalFilter()));
            predicate.or(referral.note.containsIgnoreCase(filter.getGlobalFilter()));
        }
        if (filter.isName()) {
            applyCriteria(predicate, referral.name, filter.getName());
        }
        return predicate;
    }
}