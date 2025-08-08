package cz.prm.business.contacts;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static cz.prm.utils.ComponentTestUtils.arrayIncludesAllFilter;
import static cz.prm.utils.ComponentTestUtils.arrayIncludesFilter;
import static cz.prm.utils.ComponentTestUtils.containsFilter;
import static cz.prm.utils.ComponentTestUtils.containsNotContainsFilter;
import static cz.prm.utils.ComponentTestUtils.notContainsFilter;
import static cz.prm.utils.ComponentTestUtils.uuid;
import static cz.prm.utils.ContactComponentTestUtils.contactDto;
import static cz.prm.utils.ContactComponentTestUtils.contactsQueryDto;
import static cz.prm.utils.assertions.ContractComponentTestAssertions.assertAboutDto;
import static cz.prm.utils.assertions.ContractComponentTestAssertions.assertContact;
import static cz.prm.utils.assertions.ContractComponentTestAssertions.assertContacts;
import static java.time.Instant.now;
import static java.util.Collections.sort;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.contact.ContactDto;
import org.junit.jupiter.api.Test;

public class ContactComponentTest extends ContactComponentTestBase {

    @Test
    void getContactsDataAccess() {
        var queryDto = contactsQueryDto();
        var pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user2GetContacts(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetContacts(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user1Contacts = createContacts(USER_1);
        pageDto = user1GetContacts(queryDto);
        assertContacts(user1Contacts, pageDto);

        pageDto = user2GetContacts(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetContacts(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user2Contacts = createContacts(USER_2);
        pageDto = user2GetContacts(queryDto);
        assertContacts(user2Contacts, pageDto);

        pageDto = user1GetContacts(queryDto);
        assertContacts(user1Contacts, pageDto);

        pageDto = user3GetContacts(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user3Contacts = createContacts(USER_3);
        pageDto = user3GetContacts(queryDto);
        assertContacts(user3Contacts, pageDto);

        pageDto = user1GetContacts(queryDto);
        assertContacts(user1Contacts, pageDto);

        pageDto = user2GetContacts(queryDto);
        assertContacts(user2Contacts, pageDto);
    }

    @Test
    void getContactsPagination() {
        var queryDto = contactsQueryDto();
        var pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getTotalPages()).isZero();
        assertThat(pageDto.getTotalElements()).isZero();
        assertThat(pageDto.getPageSize()).isEqualTo(50);
        assertThat(pageDto.getContent()).isEmpty();

        createContacts(USER_1, 21);
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(1);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(50);
        assertThat(pageDto.getContent()).hasSize(21);

        var pagination = queryDto.getPagination();
        pagination.setPageSize(5);
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(5);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(5);
        assertThat(pageDto.getContent()).hasSize(5);

        pagination.setPageNumber(1);
        pagination.setPageSize(10);
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(3);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(10);
        assertThat(pageDto.getContent()).hasSize(10);

        pagination.setPageNumber(2);
        pagination.setPageSize(10);
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(3);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(10);
        assertThat(pageDto.getContent()).hasSize(1);
    }

    @Test
    void getContactsSorting() {
        var queryDto = contactsQueryDto();
        queryDto.setSort("asc(firstName)");
        var pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        createContacts(USER_1, 21);
        queryDto = contactsQueryDto();
        queryDto.setSort("asc(firstName)");
        pageDto = user1GetContacts(queryDto);
        var actual = pageDto.getContent();
        var expected = newArrayList(actual);
        sort(expected, comparing(ContactDto::getFirstName));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(firstName)");
        pageDto = user1GetContacts(queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(ContactDto::getFirstName).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("asc(lastName)");
        pageDto = user1GetContacts(queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(ContactDto::getLastName));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(lastName)");
        pageDto = user1GetContacts(queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(ContactDto::getLastName).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("asc(dateOfBirth)");
        pageDto = user1GetContacts(queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(ContactDto::getDateOfBirth));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(dateOfBirth)");
        pageDto = user1GetContacts(queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(ContactDto::getDateOfBirth).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);
    }

    @Test
    void getContactsFilters() {
        var queryDto = contactsQueryDto();
        var pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var contactsDto = createContacts(USER_1, 21);
        var contactDto1 = contactsDto.get(0);
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        queryDto = contactsQueryDto();
        var filter = queryDto.getFilter();

        filter.setFirstName(contactDto1.getFirstName());
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setFirstName(containsFilter(contactDto1.getFirstName()));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setFirstName(notContainsFilter(contactDto1.getFirstName()));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).hasSize(20);

        var contactDto2 = contactsDto.get(1);
        filter.setFirstName(containsNotContainsFilter(contactDto1.getFirstName(), contactDto2.getFirstName()));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setFirstName(notContainsFilter("First"));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        queryDto = contactsQueryDto();
        filter = queryDto.getFilter();

        filter.setLastName(contactDto1.getLastName());
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setLastName(containsFilter(contactDto1.getLastName()));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setLastName(notContainsFilter(contactDto1.getLastName()));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).hasSize(20);

        filter.setLastName(containsNotContainsFilter(contactDto1.getLastName(), contactDto2.getLastName()));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setLastName(notContainsFilter("Last"));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        queryDto = contactsQueryDto();
        filter = queryDto.getFilter();

        filter.setStatus(contactDto1.getStatus());
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setStatus(containsFilter(contactDto1.getStatus()));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setStatus(notContainsFilter(contactDto1.getStatus()));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).hasSize(20);

        filter.setStatus(containsNotContainsFilter(contactDto1.getStatus(), contactDto2.getStatus()));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setStatus(notContainsFilter("Status"));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        queryDto = contactsQueryDto();
        filter = queryDto.getFilter();

        filter.setSource(contactDto1.getSource());
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setSource(containsFilter(contactDto1.getSource()));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setSource(notContainsFilter(contactDto1.getSource()));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).hasSize(20);

        filter.setSource(containsNotContainsFilter(contactDto1.getSource(), contactDto2.getSource()));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setSource(notContainsFilter("Source"));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        queryDto = contactsQueryDto();
        filter = queryDto.getFilter();

        filter.setLabels(null);
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setLabels(arrayIncludesFilter(contactDto1.getLabels()));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setLabels(arrayIncludesFilter(newArrayList(uuid())));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setLabels(arrayIncludesAllFilter(contactDto1.getLabels()));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setLabels(arrayIncludesAllFilter(newArrayList(uuid())));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        queryDto = contactsQueryDto();
        filter = queryDto.getFilter();

        filter.setIndustries(null);
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setIndustries(arrayIncludesFilter(contactDto1.getIndustries()));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setIndustries(arrayIncludesFilter(newArrayList(uuid())));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setIndustries(arrayIncludesAllFilter(contactDto1.getIndustries()));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setIndustries(arrayIncludesAllFilter(newArrayList(uuid())));
        pageDto = user1GetContacts(queryDto);
        assertThat(pageDto.getContent()).isEmpty();
    }

    @Test
    void getContact() {
        var contact = createContact(USER_1);
        var contactId = contact.getContactId();

        var contactDto = user1GetContact(contactId);
        assertContact(contact, contactDto);
        user2GetContactExpectNotFound(contactId);
        user3GetContactExpectNotFound(contactId);

        contact = createContact(USER_2);
        contactId = contact.getContactId();
        contactDto = user2GetContact(contactId);
        assertContact(contact, contactDto);
        user1GetContactExpectNotFound(contactId);
        user3GetContactExpectNotFound(contactId);

        contact = createContact(USER_3);
        contactId = contact.getContactId();
        contactDto = user3GetContact(contactId);
        assertContact(contact, contactDto);
        user1GetContactExpectNotFound(contactId);
        user2GetContactExpectNotFound(contactId);
    }

    @Test
    void createContact() {
        var toCreateDto = contactDto();
        user1CreateContact(toCreateDto);
        var contact = getContactFromDb(toCreateDto);
        var contactId = contact.getContactId();

        var createdDto = user1GetContact(contactId);
        assertContact(contact, createdDto);
        user2GetContactExpectNotFound(contactId);
        user3GetContactExpectNotFound(contactId);

        toCreateDto = contactDto();
        user2CreateContact(toCreateDto);
        contact = getContactFromDb(toCreateDto);
        contactId = contact.getContactId();

        createdDto = user2GetContact(contactId);
        assertContact(contact, createdDto);
        user1GetContactExpectNotFound(contactId);
        user3GetContactExpectNotFound(contactId);

        toCreateDto = contactDto();
        user3CreateContact(toCreateDto);
        contact = getContactFromDb(toCreateDto);
        contactId = contact.getContactId();

        createdDto = user3GetContact(contactId);
        assertContact(contact, createdDto);
        user1GetContactExpectNotFound(contactId);
        user2GetContactExpectNotFound(contactId);
    }

    @Test
    void updateContact() {
        var contact = createContact(USER_1);
        var contactId = contact.getContactId();
        var updateDto = user1GetContact(contactId);

        updateDto.setFirstName(uuid());
        updateDto.setLastName(uuid());
        updateDto.setDateOfBirth(now());
        user1UpdateContact(contactId, updateDto);
        contact = getContactFromDb(updateDto);
        assertContact(contact, updateDto);

        user2UpdateContactExpectNotFound(contactId, updateDto);
        user3UpdateContactExpectNotFound(contactId, updateDto);

        contact = createContact(USER_2);
        contactId = contact.getContactId();
        updateDto = user2GetContact(contactId);

        updateDto.setFirstName(uuid());
        updateDto.setLastName(uuid());
        updateDto.setDateOfBirth(now());
        user2UpdateContact(contactId, updateDto);
        contact = getContactFromDb(updateDto);
        assertContact(contact, updateDto);

        user1UpdateContactExpectNotFound(contactId, updateDto);
        user3UpdateContactExpectNotFound(contactId, updateDto);

        contact = createContact(USER_3);
        contactId = contact.getContactId();
        updateDto = user3GetContact(contactId);

        updateDto.setFirstName(uuid());
        updateDto.setLastName(uuid());
        updateDto.setDateOfBirth(now());
        user3UpdateContact(contactId, updateDto);
        contact = getContactFromDb(updateDto);
        assertContact(contact, updateDto);

        user1UpdateContactExpectNotFound(contactId, updateDto);
        user2UpdateContactExpectNotFound(contactId, updateDto);
    }

    @Test
    void deleteContact() {
        var contact = createContact(USER_1);
        var contactId = contact.getContactId();
        var contactDto = user1GetContact(contactId);
        assertContact(contact, contactDto);

        user2DeleteContactExpectNotFound(contactId);
        user3DeleteContactExpectNotFound(contactId);
        user1DeleteContact(contactId);
        user1GetContactExpectNotFound(contactId);

        contact = createContact(USER_2);
        contactId = contact.getContactId();
        contactDto = user2GetContact(contactId);
        assertContact(contact, contactDto);

        user1DeleteContactExpectNotFound(contactId);
        user3DeleteContactExpectNotFound(contactId);
        user2DeleteContact(contactId);
        user2GetContactExpectNotFound(contactId);

        contact = createContact(USER_3);
        contactId = contact.getContactId();
        contactDto = user3GetContact(contactId);
        assertContact(contact, contactDto);

        user1DeleteContactExpectNotFound(contactId);
        user2DeleteContactExpectNotFound(contactId);
        user3DeleteContact(contactId);
        user3GetContactExpectNotFound(contactId);
    }

    @Test
    void getAbout() {
        var contact = createContact(USER_1);
        var contactId = contact.getContactId();

        var about = getAboutFromDb(contact);
        var aboutDto = user1GetAbout(contactId);
        assertAboutDto(about, aboutDto);
        user2GetAboutExpectNotFound(contactId);
        user3GetAboutExpectNotFound(contactId);

        contact = createContact(USER_2);
        contactId = contact.getContactId();

        about = getAboutFromDb(contact);
        aboutDto = user2GetAbout(contactId);
        assertAboutDto(about, aboutDto);
        user1GetAboutExpectNotFound(contactId);
        user3GetAboutExpectNotFound(contactId);

        contact = createContact(USER_3);
        contactId = contact.getContactId();

        about = getAboutFromDb(contact);
        aboutDto = user3GetAbout(contactId);
        assertAboutDto(about, aboutDto);
        user1GetAboutExpectNotFound(contactId);
        user2GetAboutExpectNotFound(contactId);
    }

    @Test
    void updateAbout() {
        var contact = createContact(USER_1);
        var contactId = contact.getContactId();
        var updateDto = user1GetAbout(contactId);

        updateDto.setDescription(uuid());
        user1UpdateAbout(contactId, updateDto);
        var about = getAboutFromDb(contact);
        assertAboutDto(about, updateDto);

        user2UpdateAboutExpectNotFound(contactId, updateDto);
        user3UpdateAboutExpectNotFound(contactId, updateDto);

        contact = createContact(USER_2);
        contactId = contact.getContactId();
        updateDto = user2GetAbout(contactId);

        updateDto.setDescription(uuid());
        user2UpdateAbout(contactId, updateDto);
        about = getAboutFromDb(contact);
        assertAboutDto(about, updateDto);

        user1UpdateAboutExpectNotFound(contactId, updateDto);
        user3UpdateAboutExpectNotFound(contactId, updateDto);

        contact = createContact(USER_3);
        contactId = contact.getContactId();
        updateDto = user3GetAbout(contactId);

        updateDto.setDescription(uuid());
        user3UpdateAbout(contactId, updateDto);
        about = getAboutFromDb(contact);
        assertAboutDto(about, updateDto);

        user1UpdateAboutExpectNotFound(contactId, updateDto);
        user2UpdateAboutExpectNotFound(contactId, updateDto);
    }
}
