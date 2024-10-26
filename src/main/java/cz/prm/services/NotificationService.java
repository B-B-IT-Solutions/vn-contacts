package cz.prm.services;

import cz.prm.domain.reminder.query.RemindersQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
public class NotificationService {

    private ReminderService reminderService;

    @Autowired
    public NotificationService(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    public void getNotifications(Long contactId) {
        var reminders = reminderService.getReminders(contactId, new RemindersQuery());

//        reminders.getContent().stream().filter(r -> r.hasRecurrenceRule()).forEach(r -> {
//            var rule = r.getRecurrenceRule();
//            new OfRule(rule);
//        });
    }
}
