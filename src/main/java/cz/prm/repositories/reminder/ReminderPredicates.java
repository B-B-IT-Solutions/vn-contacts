package cz.prm.repositories.reminder;

import static cz.prm.domain.referral.querydsl.QReferral.referral;
import static cz.prm.security.SecurityContextUtils.getUser;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Component;

@Component
public class ReminderPredicates {

    public Predicate reminders() {
        var predicate = dataAccessPredicate();
        return predicate;
    }

    public Predicate byReminderId(Long reminderId) {
        var predicate = dataAccessPredicate();
        return predicate.and(referral.referralId.eq(reminderId));
    }

    private BooleanExpression dataAccessPredicate() {
        var user = getUser();
        return referral.owner.username.eq(user.getUsername());
    }
}