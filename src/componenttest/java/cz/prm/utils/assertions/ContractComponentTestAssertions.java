package cz.prm.utils.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.domain.contact.Contact;
import java.util.List;
import java.util.Objects;

public class ContractComponentTestAssertions {

    public static void assertContacts(List<Contact> contacts, PageDto<ContactDto> pageDto) {
        assertContacts(contacts, pageDto.getContent());
    }

    public static void assertContacts(List<Contact> contacts, List<ContactDto> dtos) {
        assertThat(contacts).isNotEmpty().hasSameSizeAs(dtos);
        contacts.forEach(u1 -> {
            var u2 = dtos.stream().filter(u -> Objects.equals(u1.getContactId(), u.getContactId())).findFirst().get();
            assertContact(u1, u2);
        });
    }

    public static void assertContact(Contact contact, ContactDto contactDto) {
        assertThat(contact.getContactId()).isEqualTo(contactDto.getContactId());
        assertThat(contact.getFirstName()).isEqualTo(contactDto.getFirstName());
        assertThat(contact.getMiddleName()).isEqualTo(contactDto.getMiddleName());
        assertThat(contact.getLastName()).isEqualTo(contactDto.getLastName());
        assertThat(contact.getMiddleName()).isEqualTo(contactDto.getMiddleName());
        assertThat(contact.getEmail()).isEqualTo(contactDto.getEmail());
        assertThat(contact.getProfessions()).isEqualTo(contactDto.getProfessions());
        assertThat(contact.getIndustries()).isEqualTo(contactDto.getIndustries());
        assertThat(contact.getLabels()).isEqualTo(contactDto.getLabels());
        assertThat(contact.getDateOfBirth()).isEqualTo(contactDto.getDateOfBirth());
        assertThat(contact.getLastEditDate()).isNotNull();
        assertThat(contact.getCreationDate()).isNotNull();
    }
}
