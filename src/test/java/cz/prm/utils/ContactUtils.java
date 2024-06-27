package cz.prm.utils;

import static cz.prm.utils.CommonUtils.pagination;
import static cz.prm.utils.CommonUtils.paginationDto;
import static cz.prm.utils.CommonUtils.user;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.TestUtils.uuid;
import static java.time.Instant.now;
import static org.assertj.core.util.Lists.newArrayList;

import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.contact.query.ContactsFilterDto;
import cz.prm.controllers.dto.contact.query.ContactsQueryDto;
import cz.prm.domain.contact.Contact;
import cz.prm.domain.contact.query.ContactsFilter;
import cz.prm.domain.contact.query.ContactsQuery;
import java.util.List;

public class ContactUtils {

    public static List<Contact> contacts() {
        return newArrayList(contact(), contact(), contact());
    }

    public static Contact contact() {
        var contact = new Contact();
        contact.setContactId(randomLong());
        contact.setFirstName(uuid());
        contact.setMiddleName(uuid());
        contact.setLastName(uuid());
        contact.setNickName(uuid());
        contact.setEmail(uuid());
        contact.setProfessions(uuid());
        contact.setIndustries(uuid());
        contact.setOwner(user());
        contact.setLastEditDate(now());
        contact.setCreationDate(now());
        return contact;
    }

    public static ContactDto contactDto() {
        var contact = new ContactDto();
        contact.setContactId(randomLong());
        contact.setFirstName(uuid());
        contact.setMiddleName(uuid());
        contact.setLastName(uuid());
        contact.setNickName(uuid());
        contact.setEmail(uuid());
        contact.setProfessions(uuid());
        contact.setIndustries(uuid());
        contact.setLastEditDate(now());
        contact.setCreationDate(now());
        return contact;
    }

    public static ContactsQuery contactsQuery() {
        var query = new ContactsQuery();
        query.setPagination(pagination());
        query.setFilter(contactsFilter());
        query.setSort(uuid());
        return query;
    }

    public static ContactsQueryDto contactsQueryDto() {
        var query = new ContactsQueryDto();
        query.setPagination(paginationDto());
        query.setFilter(contactsFilterDto());
        query.setSort(uuid());
        return query;
    }

    public static ContactsFilter contactsFilter() {
        var filter = new ContactsFilter();
        filter.setFirstName(uuid());
        filter.setLastName(uuid());
        filter.setEmail(uuid());
        return filter;
    }

    public static ContactsFilterDto contactsFilterDto() {
        var filter = new ContactsFilterDto();
        filter.setFirstName(uuid());
        filter.setLastName(uuid());
        filter.setEmail(uuid());
        return filter;
    }
}
