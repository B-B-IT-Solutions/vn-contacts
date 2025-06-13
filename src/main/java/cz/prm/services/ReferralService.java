package cz.prm.services;

import static cz.prm.domain.common.PageRequests.getPageRequest;
import static java.lang.String.format;

import cz.prm.domain.common.query.Page;
import cz.prm.domain.referral.Recurrence;
import cz.prm.domain.referral.Referral;
import cz.prm.domain.referral.query.ReferralsQuery;
import cz.prm.repositories.referral.ReferralPredicates;
import cz.prm.repositories.referral.ReferralRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReferralService {

    private ReferralRepository repository;
    private ReferralPredicates predicates;

    public ReferralService(ReferralRepository repository, ReferralPredicates predicates) {
        this.repository = repository;
        this.predicates = predicates;
    }

    public Page<Referral> getReminders(Long contactId, ReferralsQuery query) {
        var pageRequest = getPageRequest(query.getPagination(), query.resolveSort());
        var predicate = predicates.byContactId(contactId);
        var page = repository.findAll(predicate, pageRequest);
        return new Page<>(page);
    }

    public Referral getReminder(Long reminderId) {
        return getReminderById(reminderId);
    }

    public void createReminder(Referral referral) {
        repository.save(referral);
    }

    public void updateReminder(Long reminderId, Referral updatedReferral) {
        var savedReminder = getReminderById(reminderId);
        updateReminderFields(savedReminder, updatedReferral);
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

    private void updateReminderFields(Referral savedReferral, Referral updatedReferral) {
        savedReferral.setTitle(updatedReferral.getTitle());
        savedReferral.setDescription(updatedReferral.getDescription());
        savedReferral.setRecurrence(updatedReferral.getRecurrence());
        updateRecurrenceFields(savedReferral.getRecurrence(), updatedReferral.getRecurrence());
    }

    private void updateRecurrenceFields(Recurrence savedRecurrence, Recurrence updatedRecurrence) {
        savedRecurrence.setValue(updatedRecurrence.getValue());
        savedRecurrence.resetParsedRule();
    }

    private Referral getReminderById(Long reminderId) {
        var predicate = predicates.byReminderId(reminderId);
        var optional = repository.findOne(predicate);
        return optional.orElseThrow(entityNotFoundSupplier(reminderId));
    }

    private Supplier<EntityNotFoundException> entityNotFoundSupplier(Long userId) {
        return () -> new EntityNotFoundException(format("Reminder for given id=[%s] not found!", userId));
    }
}
