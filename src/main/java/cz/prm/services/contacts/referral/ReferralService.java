package cz.prm.services.contacts.referral;

import static cz.prm.domain.common.PageRequests.getPageRequest;
import static java.lang.String.format;

import cz.prm.domain.common.query.Page;
import cz.prm.domain.contacts.referral.Referral;
import cz.prm.domain.contacts.referral.query.ReferralsFilter;
import cz.prm.domain.contacts.referral.query.ReferralsQuery;
import cz.prm.repositories.contacts.referral.ReferralPredicates;
import cz.prm.repositories.contacts.referral.ReferralRepository;
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

    public Page<Referral> getReferrals(ReferralsQuery query) {
        var pageRequest = getPageRequest(query.getPagination(), query.resolveSort());
        var predicate = predicates.referrals(query.getFilter());
        var page = repository.findAll(predicate, pageRequest);
        return new Page<>(page);
    }

    public Page<Referral> getContactReferrals(Long contactId, ReferralsQuery query) {
        var pageRequest = getPageRequest(query.getPagination(), query.resolveSort());
        var predicate = predicates.contactReferrals(contactId, query.getFilter());
        var page = repository.findAll(predicate, pageRequest);
        return new Page<>(page);
    }

    public Referral getReferral(Long referralId) {
        return getReferralById(referralId);
    }

    public void createReferral(Referral referral) {
        repository.save(referral);
    }

    public void updateReferral(Long referralId, Referral updatedReferral) {
        var savedReferral = getReferralById(referralId);
        updateReferralFields(savedReferral, updatedReferral);
        repository.save(savedReferral);
    }

    public void deleteReferral(Long referralId) {
        var savedReferral = getReferralById(referralId);
        repository.deleteById(savedReferral.getReferralId());
    }

    public void deleteByContactId(Long contactId) {
        var predicate = predicates.contactReferrals(contactId, new ReferralsFilter());
        var notes = repository.findAll(predicate);
        repository.deleteAll(notes);
    }

    private void updateReferralFields(Referral savedReferral, Referral updatedReferral) {
        savedReferral.setName(updatedReferral.getName());
        savedReferral.setNote(updatedReferral.getNote());
        savedReferral.setSource(updatedReferral.getSource());
        savedReferral.setStatus(updatedReferral.getStatus());
        savedReferral.setPriority(updatedReferral.getPriority());
        savedReferral.setClosedDate(updatedReferral.getClosedDate());
        savedReferral.setStartDate(updatedReferral.getStartDate());
        savedReferral.setExpiredDate(updatedReferral.getExpiredDate());
    }

    private Referral getReferralById(Long referralId) {
        var predicate = predicates.byReferralId(referralId);
        var optional = repository.findOne(predicate);
        return optional.orElseThrow(entityNotFoundSupplier(referralId));
    }

    private Supplier<EntityNotFoundException> entityNotFoundSupplier(Long userId) {
        return () -> new EntityNotFoundException(format("Referral for given id=[%s] not found!", userId));
    }
}
