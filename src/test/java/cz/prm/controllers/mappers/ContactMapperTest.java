package cz.prm.controllers.mappers;

import static cz.prm.utils.CommonUtils.DEFAULT_PAGE_SIZE;
import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.ContactUtils.about;
import static cz.prm.utils.ContactUtils.aboutDto;
import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.ContactUtils.contactDto;
import static cz.prm.utils.ContactUtils.contactEdit;
import static cz.prm.utils.ContactUtils.contactEditDto;
import static cz.prm.utils.ContactUtils.contacts;
import static cz.prm.utils.ContactUtils.contactsFilterDto;
import static cz.prm.utils.ContactUtils.contactsQueryDto;
import static cz.prm.utils.assertions.ContactAssertions.assertAbout;
import static cz.prm.utils.assertions.ContactAssertions.assertContact;
import static cz.prm.utils.assertions.ContactAssertions.assertDecoratedContact;
import static cz.prm.utils.assertions.ContactAssertions.assertContactFilter;
import static cz.prm.utils.assertions.ContactAssertions.assertContactQuery;
import static cz.prm.utils.assertions.ContactAssertions.assertPage;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.contact.AboutDto;
import cz.prm.controllers.dto.contact.query.ContactsQueryDto;
import cz.prm.domain.contact.DecoratedContact;
import cz.prm.domain.contact.query.ContactsQuery;
import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.Test;

class ContactMapperTest {

    public static final String DEFAULT_CONTACTS_SORT = "asc(lastName)";

    private ContactMapper mapper = MapperUtils.getContactMapper();

    @Test
    void toPageDto() {
        var page = page(contacts());
        var dtos = mapper.toPageDto(page);
        assertPage(page, dtos);
    }

    @Test
    void toContactEdit() {
        var dto = contactEditDto();
        var ce = mapper.toContactEdit(dto);
        assertDecoratedContact(ce, dto);
    }

    @Test
    void toContactEditDto() {
        var ce = contactEdit();
        var dto = mapper.toContactEditDto(ce);
        assertDecoratedContact(ce, dto);
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
    void toAboutDto() {
        var about = about();
        var dto = mapper.toAboutDto(about);
        assertAbout(about, dto);
    }

    @Test
    void toAbout() {
        var dto = aboutDto();
        var about = mapper.toAbout(dto);
        assertAbout(about, dto);
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
    void afterDecoratedContactDto() {
        var target = new DecoratedContact();
        target.setContact(null);
        target.setAbout(null);
        assertThat(target.getContact()).isNull();
        assertThat(target.getAbout()).isNull();
        mapper.afterDecoratedContactDto(null, target);
        assertThat(target.getContact()).isNotNull();
        assertThat(target.getAbout()).isNotNull();
    }

    @Test
    void afterAboutDto() {
        var target = new AboutDto();
        target.setIdealClients(null);
        target.setFirstInteraction(null);
        assertThat(target.getFirstInteraction()).isNull();
        mapper.afterAboutDto(null, target);
        assertThat(target.getIdealClients()).isNotNull().isEmpty();
        assertThat(target.getFirstInteraction()).isNotNull();
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