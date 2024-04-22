package cz.prm.services.contact;

import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.ContactUtils.contacts;
import static cz.prm.utils.TestUtils.uuid;
import static cz.prm.utils.assertions.ContactAssertions.assertContact;
import static cz.prm.utils.assertions.ContactAssertions.assertContacts;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.querydsl.core.BooleanBuilder;
import cz.prm.repositories.contact.ContactPredicates;
import cz.prm.repositories.contact.ContactRepository;
import cz.prm.security.SecurityContextUtils;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
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
   void createContact() {
      try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
         var contact = contact();
         contact.setOwner(null);
         var username = uuid();
         context.when(() -> SecurityContextUtils.getUsername()).thenReturn(username);
         assertThat(contact.getOwner()).isNull();

         contactService.createContact(contact);
         verify(repository).save(contact);
         assertThat(contact.getOwner()).isEqualTo(username);
      }
   }

   @Test
   void getContacts() {
      var users = contacts();
      var predicate = new BooleanBuilder();
      when(predicates.contacts()).thenReturn(predicate);
      when(repository.findAll(predicate)).thenReturn(users);
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