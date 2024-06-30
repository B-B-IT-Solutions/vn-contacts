package cz.prm.controllers.mappers;

import static cz.prm.utils.CommonUtils.DEFAULT_PAGE_SIZE;
import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.ContactUtils.contactDto;
import static cz.prm.utils.ContactUtils.contacts;
import static cz.prm.utils.ContactUtils.contactsFilterDto;
import static cz.prm.utils.ContactUtils.contactsQueryDto;
import static cz.prm.utils.assertions.ContactAssertions.assertContact;
import static cz.prm.utils.assertions.ContactAssertions.assertContactFilter;
import static cz.prm.utils.assertions.ContactAssertions.assertContactQuery;
import static cz.prm.utils.assertions.ContactAssertions.assertPage;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.contact.query.ContactsQueryDto;
import cz.prm.domain.contact.query.ContactsQuery;
import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.Test;

class ContactMapperTest {

    public static final String DEFAULT_CONTACTS_SORT = "asc(firstName)";

    private ContactMapper mapper = MapperUtils.getContactMapper();

    @Test
    void toPageDto() {
        var page = page(contacts());
        var dtos = mapper.toPageDto(page);
        assertPage(page, dtos);
    }

    @Test
    void toContactDto() {
        var contact = contact();
        var dto = mapper.toContactDto(contact);
        assertContact(contact, dto);
    }

    @Test
    void toContact() {
        var dto = contactDto();
        var contact = mapper.toContact(dto);
        assertContact(contact, dto);
    }

    @Test
    void toContactsQuery() {
        var dto = contactsQueryDto();
        var query = mapper.toContactsQuery(dto);
        assertContactQuery(query, dto);
    }

    @Test
    void toContactsFilter() {
        var dto = contactsFilterDto();
        var filter = mapper.toContactsFilter(dto);
        assertContactFilter(filter, dto);
    }

    @Test
    void toNullSafeContactsQueryNullQuery() {
        var query = mapper.toNullSafeContactsQuery(null);
        assertNullSafeContactQuery(query);
    }

    @Test
    void toNullSafeContactsQueryNotNullQuery() {
        var dto = contactsQueryDto();
        var query = mapper.toNullSafeContactsQuery(dto);
        assertContactQuery(query, dto);
    }

    @Test
    void toNullSafeContactsQueryNullFiltersPagination() {
        var dto = new ContactsQueryDto();
        dto.setPagination(null);
        dto.setFilter(null);
        var query = mapper.toNullSafeContactsQuery(dto);
        assertNullSafeContactQuery(query);
    }

    @Test
    void afterContactsQuery() {
        var target = new ContactsQuery();
        target.setPagination(null);
        target.setFilter(null);
        mapper.afterContactsQuery(null, target);
        assertNullSafeContactQuery(target);
    }

    private void assertNullSafeContactQuery(ContactsQuery query) {
        assertThat(query.getPagination()).isNotNull();
        assertThat(query.getFilter()).isNotNull();
        assertThat(query.getSort()).isEqualTo(DEFAULT_CONTACTS_SORT);
        var pagination = query.getPagination();
        assertThat(pagination.getPageNumber()).isZero();
        assertThat(pagination.getPageSize()).isEqualTo(DEFAULT_PAGE_SIZE);
    }
}