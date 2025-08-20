package cz.prm.repositories.contacts.reminder;

import cz.prm.domain.contacts.reminder.Reminder;
import cz.prm.repositories.extensions.executors.PrmQuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderRepository extends JpaRepository<Reminder, Long>, PrmQuerydslPredicateExecutor<Reminder> {

}
