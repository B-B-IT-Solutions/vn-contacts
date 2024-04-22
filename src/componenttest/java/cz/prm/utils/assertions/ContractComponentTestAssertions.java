package cz.prm.utils.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.domain.contact.Contact;
import java.util.List;
import java.util.Objects;

public class ContractComponentTestAssertions {

   public static void assertContacts(List<Contact> contacts, List<ContactDto> dtos) {
      assertThat(contacts).isNotEmpty().hasSameSizeAs(dtos);
      contacts.forEach(u1 -> {
         var u2 = dtos.stream().filter(u -> Objects.equals(u1.getContactId(), u.getContactId())).findFirst().get();
         assertContact(u1, u2);
      });
   }

   public static void assertContact(Contact contact, ContactDto contactDto) {
      assertThat(contactDto.getEmail()).isEqualTo(contact.getEmail());
      assertThat(contactDto.getFirstName()).isEqualTo(contact.getFirstName());
      assertThat(contactDto.getLastName()).isEqualTo(contact.getLastName());
   }
}
