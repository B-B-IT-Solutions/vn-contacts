package cz.prm.custom;

import cz.prm.domain.reminder.Reminder;
import cz.prm.repositories.reminder.ReminderRepository;
import org.springframework.context.annotation.Primary;

@Primary
public interface ComponentTestReminderRepository extends ReminderRepository {

    Reminder getByDescription(String description);
}
