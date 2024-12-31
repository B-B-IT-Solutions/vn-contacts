package cz.prm.services;

import static cz.prm.domain.common.PageRequests.getPageRequest;
import static java.lang.String.format;

import cz.prm.domain.common.query.Page;
import cz.prm.domain.reminder.Recurrence;
import cz.prm.domain.reminder.Reminder;
import cz.prm.domain.reminder.query.RemindersQuery;
import cz.prm.repositories.reminder.ReminderPredicates;
import cz.prm.repositories.reminder.ReminderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReminderService {

    private ReminderRepository repository;
    private ReminderPredicates predicates;

    public ReminderService(ReminderRepository repository, ReminderPredicates predicates) {
        this.repository = repository;
        this.predicates = predicates;
    }

    public Page<Reminder> getReminders(Long contactId, RemindersQuery query) {
        var pageRequest = getPageRequest(query.getPagination(), query.resolveSort());
        var predicate = predicates.byContactId(contactId);
        var page = repository.findAll(predicate, pageRequest);
        return new Page<>(page);
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

    public void deleteReminder(Long reminderId) {
        var savedReminder = getReminderById(reminderId);
        repository.deleteById(savedReminder.getReminderId());
    }

    public void deleteByContactId(Long contactId) {
        var predicate = predicates.byContactId(contactId);
        var notes = repository.findAll(predicate);
        repository.deleteAll(notes);
    }

    private void updateReminderFields(Reminder savedReminder, Reminder updatedReminder) {
        savedReminder.setTitle(updatedReminder.getTitle());
        savedReminder.setDescription(updatedReminder.getDescription());
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

    private Supplier<EntityNotFoundException> entityNotFoundSupplier(Long userId) {
        return () -> new EntityNotFoundException(format("Reminder for given id=[%s] not found!", userId));
    }
}
