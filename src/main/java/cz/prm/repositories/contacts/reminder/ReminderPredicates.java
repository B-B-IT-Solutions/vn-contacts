package cz.prm.repositories.contacts.reminder;

import static cz.prm.domain.contacts.reminder.querydsl.QReminder.reminder;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Component;

@Component
public class ReminderPredicates {

    public Predicate reminders() {
        return new BooleanBuilder();
    }

    public Predicate byReminderId(Long reminderId) {
        var predicate = new BooleanBuilder();
        return predicate.and(reminder.reminderId.eq(reminderId));
    }
}