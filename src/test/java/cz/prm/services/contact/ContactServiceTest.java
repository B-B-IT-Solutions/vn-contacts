package cz.prm.services.contact;

import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.ContactUtils.contacts;
import static cz.prm.utils.assertions.ContactAssertions.assertContact;
import static cz.prm.utils.assertions.ContactAssertions.assertContacts;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.querydsl.core.BooleanBuilder;
import cz.prm.repositories.contact.ContactPredicates;
import cz.prm.repositories.contact.ContactRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

   @Mock
   private ContactRepository repository;
   @Mock
   private ContactPredicates predicates;

   private ContactService contactService;

   @BeforeEach
   void setUp() {
      contactService = new ContactService(repository, predicates);
   }

   @Test
   void getContacts() {
      var users = contacts();
      when(repository.findAll()).thenReturn(users);
      var result = contactService.getContacts();
      assertContacts(result, users);
   }

   @Test
   void getContact() {
      var contact = contact();
      var predicate = new BooleanBuilder();
      when(predicates.byContactId(contact.getContactId())).thenReturn(predicate);
      when(repository.findOne(predicate)).thenReturn(of(contact));
      var result = contactService.getContact(contact.getContactId());
      assertContact(result, contact);
   }

   @Test
   void getContact_EntityNotFound() {
      var contact = contact();
      var predicate = new BooleanBuilder();
      when(predicates.byContactId(contact.getContactId())).thenReturn(predicate);
      when(repository.findOne(predicate)).thenReturn(empty());
      assertThrows(EntityNotFoundException.class, () -> contactService.getContact(contact.getContactId()));
   }

}