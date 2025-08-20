package cz.prm.repositories.reminder;

import cz.prm.domain.contacts.reminder.Reminder;
import cz.prm.repositories.customisations.executors.PrmQuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderRepository extends JpaRepository<Reminder, Long>, PrmQuerydslPredicateExecutor<Reminder> {

}
