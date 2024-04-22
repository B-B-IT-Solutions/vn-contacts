package cz.prm.services.contact;

import static com.google.common.collect.Lists.newArrayList;
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
      repository.save(contact);
   }

   public List<Contact> getContacts() {
      var predicate = predicates.contacts();
      return newArrayList(repository.findAll(predicate));
   }

   public Contact getContact(Long contactId) {
      var predicate = predicates.byContactId(contactId);
      var optional = repository.findOne(predicate);
      return optional.orElseThrow(entityNotFoundSupplier(contactId));
   }

   private Supplier<EntityNotFoundException> entityNotFoundSupplier(Long userId) {
      return () -> new EntityNotFoundException(format("Contact for given id=[%s] not found!", userId));
   }

}
