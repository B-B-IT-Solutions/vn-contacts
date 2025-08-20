package cz.prm.services.contacts.contact.data;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.domain.common.PageRequests.getPageRequest;
import static java.lang.String.format;

import cz.prm.domain.common.query.Page;
import cz.prm.domain.contacts.contact.Contact;
import cz.prm.domain.contacts.contact.query.ContactsQuery;
import cz.prm.domain.contacts.networking.ReferralRequirement;
import cz.prm.repositories.contacts.contact.ContactPredicates;
import cz.prm.repositories.contacts.contact.ContactRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ContactService {

    private ContactRepository repository;
    private ContactPredicates predicates;

    @Autowired
    public ContactService(ContactRepository repository, ContactPredicates predicates) {
        this.repository = repository;
        this.predicates = predicates;
    }

    public List<Contact> getPotentialReferrals(ReferralRequirement rr) {
        var predicate = predicates.potentialReferrals(rr);
        return newArrayList(repository.findAll(predicate));
    }

    public Page<Contact> getContacts(ContactsQuery query) {
        var pageRequest = getPageRequest(query.getPagination(), query.resolveSort());
        var predicate = predicates.contacts(query.getFilter());
        var page = repository.findAll(predicate, pageRequest);
        return new Page<>(page);
    }

    public Contact getContact(Long contactId) {
        return getContactById(contactId);
    }

    public Contact createContact(Contact contact) {
        return repository.save(contact);
    }

    public Contact updateContact(Long contactId, Contact updatedContact) {
        var savedContact = getContactById(contactId);
        updateContactFields(savedContact, updatedContact);
        return repository.save(savedContact);
    }

    public void deleteContact(Long contactId) {
        var contact = getContactById(contactId);
        repository.deleteById(contact.getContactId());
    }

    private void updateContactFields(Contact savedContact, Contact updatedContact) {
        savedContact.setFirstName(updatedContact.getFirstName());
        savedContact.setLastName(updatedContact.getLastName());
        savedContact.setEmail(updatedContact.getEmail());
        savedContact.setPhoneNumber(updatedContact.getPhoneNumber());
        savedContact.setLinkedInUrl(updatedContact.getLinkedInUrl());
        savedContact.setDateOfBirth(updatedContact.getDateOfBirth());
        savedContact.setStatus(updatedContact.getStatus());
        savedContact.setSource(updatedContact.getSource());
        savedContact.setCountry(updatedContact.getCountry());
        savedContact.setCity(updatedContact.getCity());
        savedContact.setTrustScore(updatedContact.getTrustScore());
        savedContact.setOccupation(updatedContact.getOccupation());
        savedContact.setLabels(updatedContact.getLabels());
        savedContact.setIndustries(updatedContact.getIndustries());
        savedContact.setSkills(updatedContact.getSkills());
        savedContact.setProducts(updatedContact.getProducts());
        savedContact.setTargetMarkets(updatedContact.getTargetMarkets());
    }

    private Contact getContactById(Long contactId) {
        var predicate = predicates.byContactId(contactId);
        var optional = repository.findOne(predicate);
        return optional.orElseThrow(entityNotFoundSupplier(contactId));
    }

    private Supplier<EntityNotFoundException> entityNotFoundSupplier(Long userId) {
        return () -> new EntityNotFoundException(format("Contact for given id=[%s] not found!", userId));
    }
}
