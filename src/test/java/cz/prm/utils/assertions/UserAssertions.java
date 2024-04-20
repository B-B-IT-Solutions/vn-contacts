package cz.prm.utils.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.domain.contact.Contact;
import java.util.List;
import java.util.Objects;

public class UserAssertions {

   public static void assertContacts(List<Contact> contacts1, List<Contact> contacts2) {
      assertThat(contacts1).isNotEmpty().hasSameSizeAs(contacts2);
      contacts1.forEach(c1 -> {
         var c2 = contacts2.stream().filter(u -> Objects.equals(c1.getContactId(), u.getContactId())).findFirst().get();
         assertContact(c1, c2);
      });
   }

   public static void assertContactsDto(List<Contact> contacts, List<ContactDto> dtos) {
      assertThat(contacts).isNotEmpty().hasSameSizeAs(dtos);
      contacts.forEach(u1 -> {
         var u2 = dtos.stream().filter(u -> Objects.equals(u1.getContactId(), u.getUserId())).findFirst().get();
         assertContact(u1, u2);
      });
   }

   public static void assertContact(Contact contact1, Contact contact2) {
      assertThat(contact1.getContactId()).isEqualTo(contact2.getContactId());
      assertThat(contact1.getFirstName()).isEqualTo(contact2.getFirstName());
      assertThat(contact1.getLastName()).isEqualTo(contact2.getLastName());
   }

   public static void assertContact(Contact contact, ContactDto dto) {
      assertThat(contact.getContactId()).isEqualTo(dto.getUserId());
      assertThat(contact.getFirstName()).isEqualTo(dto.getFirstName());
      assertThat(contact.getLastName()).isEqualTo(dto.getLastName());
      assertThat(contact.getEmail()).isEqualTo(dto.getEmail());
   }

}
