package cz.prm.services;

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

    public void getNotifications() {
        var reminders = reminderService.getReminders();
        reminders.stream().filter(r -> r.hasActiveRecurrence()).forEach(r -> {
            var rule = r.getRecurrence();
        });
    }
}
