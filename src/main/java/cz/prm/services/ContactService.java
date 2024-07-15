package cz.prm.services;

import static cz.prm.domain.common.PageRequests.getPageRequest;
import static java.lang.String.format;

import cz.prm.domain.common.query.Page;
import cz.prm.domain.contact.Contact;
import cz.prm.domain.contact.query.ContactsQuery;
import cz.prm.repositories.contact.ContactPredicates;
import cz.prm.repositories.contact.ContactRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ContactService {

    private ContactRepository repository;
    private ContactPredicates predicates;

    public ContactService(ContactRepository repository, ContactPredicates predicates) {
        this.repository = repository;
        this.predicates = predicates;
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

    public void createContact(Contact contact) {
        repository.save(contact);
    }

    public void updateContact(Long contactId, Contact updatedContact) {
        var savedContact = getContactById(contactId);
        updateContactFields(savedContact, updatedContact);
        repository.save(savedContact);
    }

    public void deleteContact(Long contactId) {
        repository.deleteById(contactId);
    }

    private void updateContactFields(Contact savedContact, Contact updatedContact) {
        savedContact.setFirstName(updatedContact.getFirstName());
        savedContact.setLastName(updatedContact.getLastName());
        savedContact.setMiddleName(updatedContact.getMiddleName());
        savedContact.setNickName(updatedContact.getNickName());
        savedContact.setTelephones(updatedContact.getTelephones());
        savedContact.setEmails(updatedContact.getEmails());
        savedContact.setUrls(updatedContact.getUrls());
        savedContact.setDateOfBirth(updatedContact.getDateOfBirth());
        savedContact.setOccupation(updatedContact.getOccupation());
        savedContact.setLabels(updatedContact.getLabels());
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
