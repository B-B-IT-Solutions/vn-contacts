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

    public Page<Referral> getReferrals(Long contactId, ReferralsQuery query) {
        var pageRequest = getPageRequest(query.getPagination(), query.resolveSort());
        var predicate = predicates.byContactId(contactId);
        var page = repository.findAll(predicate, pageRequest);
        return new Page<>(page);
    }

    public Referral getReferral(Long reminderId) {
        return getReferralById(reminderId);
    }

    public void createReferral(Referral referral) {
        repository.save(referral);
    }

    public void updateReferral(Long reminderId, Referral updatedReferral) {
        var savedReferral = getReferralById(reminderId);
        updateReferralFields(savedReferral, updatedReferral);
        repository.save(savedReferral);
    }

    public void deleteReferral(Long reminderId) {
        var savedReferral = getReferralById(reminderId);
        repository.deleteById(savedReferral.getReferralId());
    }

    public void deleteByContactId(Long contactId) {
        var predicate = predicates.byContactId(contactId);
        var notes = repository.findAll(predicate);
        repository.deleteAll(notes);
    }

    private void updateReferralFields(Referral savedReferral, Referral updatedReferral) {
        savedReferral.setTitle(updatedReferral.getTitle());
        savedReferral.setDescription(updatedReferral.getDescription());
        savedReferral.setRecurrence(updatedReferral.getRecurrence());
        updateRecurrenceFields(savedReferral.getRecurrence(), updatedReferral.getRecurrence());
    }

    private void updateRecurrenceFields(Recurrence savedRecurrence, Recurrence updatedRecurrence) {
        savedRecurrence.setValue(updatedRecurrence.getValue());
        savedRecurrence.resetParsedRule();
    }

    private Referral getReferralById(Long reminderId) {
        var predicate = predicates.byReferralId(reminderId);
        var optional = repository.findOne(predicate);
        return optional.orElseThrow(entityNotFoundSupplier(reminderId));
    }

    private Supplier<EntityNotFoundException> entityNotFoundSupplier(Long userId) {
        return () -> new EntityNotFoundException(format("Referral for given id=[%s] not found!", userId));
    }
}
