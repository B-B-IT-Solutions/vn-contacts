package cz.prm.services;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;

import cz.prm.domain.recurrence.Recurrence;
import cz.prm.domain.reminder.Reminder;
import cz.prm.repositories.reminder.ReminderPredicates;
import cz.prm.repositories.reminder.ReminderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReminderService {

    private ReminderRepository repository;
    private ReminderPredicates predicates;

    @Autowired
    public ReminderService(ReminderRepository repository, ReminderPredicates predicates) {
        this.repository = repository;
        this.predicates = predicates;
    }

    public List<Reminder> getReminders() {
        var predicate = predicates.reminders();
        var page = repository.findAll(predicate);
        return newArrayList(page);
    }

    public Reminder getReminder(Long reminderId) {
        return getReminderById(reminderId);
    }

    public void createReminder(Reminder reminder) {
        repository.save(reminder);
    }

    public void updateReminder(Long reminderId, Reminder updatedReminder) {
        var savedReminder = getReminderById(reminderId);
        updateReminderFields(savedReminder, updatedReminder);
        repository.save(savedReminder);
    }

    public void deleteReminder(Long referralId) {
        var savedReminder = getReminderById(referralId);
        repository.deleteById(savedReminder.getReminderId());
    }

    private void updateReminderFields(Reminder savedReminder, Reminder updatedReminder) {
        savedReminder.setRecurrence(updatedReminder.getRecurrence());
        updateRecurrenceFields(savedReminder.getRecurrence(), updatedReminder.getRecurrence());
    }

    private void updateRecurrenceFields(Recurrence savedRecurrence, Recurrence updatedRecurrence) {
        savedRecurrence.setValue(updatedRecurrence.getValue());
        savedRecurrence.resetParsedRule();
    }

    private Reminder getReminderById(Long reminderId) {
        var predicate = predicates.byReminderId(reminderId);
        var optional = repository.findOne(predicate);
        return optional.orElseThrow(entityNotFoundSupplier(reminderId));
    }

    private Supplier<EntityNotFoundException> entityNotFoundSupplier(Long reminderId) {
        return () -> new EntityNotFoundException(format("Reminder for given id=[%s] not found!", reminderId));
    }
}
