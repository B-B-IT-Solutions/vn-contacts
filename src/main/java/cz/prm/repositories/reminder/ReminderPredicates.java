package cz.prm.repositories.reminder;

import static cz.prm.domain.reminder.querydsl.QReminder.reminder;
import static cz.prm.security.SecurityContextUtils.getUser;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Component;

@Component
public class ReminderPredicates {

    public Predicate reminders() {
        return dataAccessPredicate();
    }

    public Predicate byReminderId(Long reminderId) {
        var predicate = dataAccessPredicate();
        return predicate.and(reminder.reminderId.eq(reminderId));
    }

    public Predicate byContactId(Long contactId) {
        var predicate = dataAccessPredicate();
        return predicate.and(reminder.contactId.eq(contactId));
    }

    private BooleanExpression dataAccessPredicate() {
        var user = getUser();
        return reminder.owner.username.eq(user.getUsername());
    }
}