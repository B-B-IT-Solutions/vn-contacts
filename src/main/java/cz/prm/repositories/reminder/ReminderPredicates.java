package cz.prm.repositories.reminder;

import static cz.prm.domain.reminder.querydsl.QReminder.reminder;

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