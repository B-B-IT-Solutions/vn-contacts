package cz.prm.services.contact;

import static java.lang.String.format;

import cz.prm.domain.contact.Contact;
import cz.prm.repositories.contact.ContactPredicates;
import cz.prm.repositories.contact.ContactRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;

@Service
public class UserService {

   private ContactRepository repository;
   private ContactPredicates predicates;

   public UserService(ContactRepository repository, ContactPredicates predicates) {
      this.repository = repository;
      this.predicates = predicates;
   }

   public List<Contact> getContacts() {
      return repository.findAll();
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
