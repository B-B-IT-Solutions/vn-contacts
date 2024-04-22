package cz.prm.services.contact;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.security.SecurityContextUtils.getUsername;
import static java.lang.String.format;

import cz.prm.domain.contact.Contact;
import cz.prm.repositories.contact.ContactPredicates;
import cz.prm.repositories.contact.ContactRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

   private ContactRepository repository;
   private ContactPredicates predicates;

   public ContactService(ContactRepository repository, ContactPredicates predicates) {
      this.repository = repository;
      this.predicates = predicates;
   }

   public void createContact(Contact contact) {
      var username = getUsername();
      contact.setOwner(username);
      repository.save(contact);
   }

   public void updateContact(Long contactId, Contact updatedContact) {
      var savedContact = getContactById(contactId);
      updateContact(savedContact, updatedContact);
      repository.save(savedContact);
   }

   public List<Contact> getContacts() {
      var predicate = predicates.contacts();
      return newArrayList(repository.findAll(predicate));
   }

   public Contact getContact(Long contactId) {
      return getContactById(contactId);
   }

   private void updateContact(Contact savedContact, Contact updatedContact) {
      savedContact.setFirstName(updatedContact.getFirstName());
      savedContact.setLastName(updatedContact.getLastName());
      savedContact.setEmail(updatedContact.getEmail());
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
