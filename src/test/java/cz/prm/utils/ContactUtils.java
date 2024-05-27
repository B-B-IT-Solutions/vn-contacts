package cz.prm.utils;

import static cz.prm.utils.CommonUtils.pagination;
import static cz.prm.utils.CommonUtils.paginationDto;
import static cz.prm.utils.CommonUtils.user;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.TestUtils.uuid;

import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.contact.query.ContactsFilterDto;
import cz.prm.controllers.dto.contact.query.ContactsQueryDto;
import cz.prm.domain.contact.Contact;
import cz.prm.domain.contact.query.ContactsFilter;
import cz.prm.domain.contact.query.ContactsQuery;
import java.util.List;
import org.assertj.core.util.Lists;

public class ContactUtils {

    public static List<Contact> contacts() {
        return Lists.newArrayList(contact(), contact(), contact());
    }

    public static Contact contact() {
        var user = new Contact();
        user.setContactId(randomLong());
        user.setFirstName(uuid());
        user.setMiddleName(uuid());
        user.setLastName(uuid());
        user.setNickName(uuid());
        user.setEmail(uuid());
        user.setOwner(user());
        return user;
    }

    public static ContactDto contactDto() {
        var user = new ContactDto();
        user.setContactId(randomLong());
        user.setFirstName(uuid());
        user.setMiddleName(uuid());
        user.setLastName(uuid());
        user.setNickName(uuid());
        user.setEmail(uuid());
        return user;
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
