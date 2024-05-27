package cz.prm.utils.assertions;

import static cz.prm.utils.assertions.CommonAssertions.assertQuery;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.contact.query.ContactsFilterDto;
import cz.prm.controllers.dto.contact.query.ContactsQueryDto;
import cz.prm.domain.common.query.Page;
import cz.prm.domain.contact.Contact;
import cz.prm.domain.contact.query.ContactsFilter;
import cz.prm.domain.contact.query.ContactsQuery;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.PageImpl;

public class ContactAssertions {

    public static void assertPage(Page<Contact> page, PageDto<ContactDto> pageDto) {
        assertThat(page.getTotalPages()).isEqualTo(pageDto.getTotalPages());
        assertThat(page.getNumberOfElements()).isEqualTo(pageDto.getNumberOfElements());
        assertThat(page.getTotalElements()).isEqualTo(pageDto.getTotalElements());
        assertThat(page.getPageSize()).isEqualTo(pageDto.getPageSize());
        assertThat(page.getPageNumber()).isEqualTo(pageDto.getPageNumber());
        assertThat(page.getContent()).isNotEmpty().hasSameSizeAs(pageDto.getContent());
        page.getContent().forEach(contact -> {
            var dto = pageDto.getContent().stream().filter(c -> Objects.equals(contact.getContactId(), c.getContactId())).findFirst().get();
            assertContact(contact, dto);
        });
    }

    public static void assertPage(Page<Contact> page1, PageImpl<Contact> page2) {
        assertThat(page1.getTotalPages()).isEqualTo(page2.getTotalPages());
        assertThat(page1.getNumberOfElements()).isEqualTo(page2.getNumberOfElements());
        assertThat(page1.getTotalElements()).isEqualTo(page2.getTotalElements());
        assertThat(page1.getPageSize()).isEqualTo(page2.getSize());
        assertThat(page1.getPageNumber()).isEqualTo(page2.getNumber());
        assertThat(page1.getContent()).isNotEmpty().hasSameSizeAs(page2.getContent());
        page1.getContent().forEach(contact1 -> {
            var contact2 = page2.getContent().stream().filter(c -> Objects.equals(contact1.getContactId(), c.getContactId())).findFirst().get();
            assertContact(contact1, contact2);
        });
    }

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
            var u2 = dtos.stream().filter(u -> Objects.equals(u1.getContactId(), u.getContactId())).findFirst().get();
            assertContact(u1, u2);
        });
    }

    public static void assertContact(Contact contact1, Contact contact2) {
        assertThat(contact1.getContactId()).isEqualTo(contact2.getContactId());
        assertThat(contact1.getFirstName()).isEqualTo(contact2.getFirstName());
        assertThat(contact1.getMiddleName()).isEqualTo(contact2.getMiddleName());
        assertThat(contact1.getLastName()).isEqualTo(contact2.getLastName());
        assertThat(contact1.getNickName()).isEqualTo(contact2.getNickName());
        assertThat(contact1.getEmail()).isEqualTo(contact2.getEmail());
        assertThat(contact1.getOwner()).isEqualTo(contact2.getOwner());
    }

    public static void assertContact(Contact contact, ContactDto dto) {
        assertThat(contact.getContactId()).isEqualTo(dto.getContactId());
        assertThat(contact.getFirstName()).isEqualTo(dto.getFirstName());
        assertThat(contact.getMiddleName()).isEqualTo(dto.getMiddleName());
        assertThat(contact.getLastName()).isEqualTo(dto.getLastName());
        assertThat(contact.getNickName()).isEqualTo(dto.getNickName());
        assertThat(contact.getEmail()).isEqualTo(dto.getEmail());
    }

    public static void assertContactQuery(ContactsQuery query, ContactsQueryDto dto) {
        assertQuery(query, dto);
        assertContactFilter(query.getFilter(), dto.getFilter());
    }

    public static void assertContactFilter(ContactsFilter filter, ContactsFilterDto dto) {
        assertThat(filter.getFirstName()).isEqualTo(dto.getFirstName());
        assertThat(filter.getLastName()).isEqualTo(dto.getLastName());
        assertThat(filter.getEmail()).isEqualTo(dto.getEmail());
    }
}
