package cz.prm.business.contacts;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import cz.prm.business.BusinessComponentTestBase;
import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.contact.AboutDto;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.contact.DecoratedContactDto;
import cz.prm.controllers.dto.contact.query.ContactsFilterDto;
import cz.prm.controllers.dto.contact.query.ContactsQueryDto;
import cz.prm.domain.contact.Contact;
import cz.prm.utils.ComponentTestUser;
import io.restassured.common.mapper.TypeRef;
import java.util.List;
import java.util.stream.IntStream;

public class ContactComponentTestBase extends BusinessComponentTestBase {

    protected static String CONTACTS_BASE_URL = "contacts";
    protected static String DECORATED_CONTACTS_URL = CONTACTS_BASE_URL;
    protected static String DECORATED_CONTACT_URL = CONTACTS_BASE_URL + "/%s";
    protected static String CONTACTS_URL = CONTACTS_BASE_URL + "/contact";
    protected static String CONTACT_URL = CONTACTS_BASE_URL + "/%s/contact";
    protected static String ABOUT_URL = CONTACTS_BASE_URL + "/%s/about";

    protected DecoratedContactDto user1CreateDecoratedContact(DecoratedContactDto dto) {
        return createDecoratedContact(dto, USER_1);
    }

    protected DecoratedContactDto user2CreateDecoratedContact(DecoratedContactDto dto) {
        return createDecoratedContact(dto, USER_2);
    }

    protected DecoratedContactDto user3CreateDecoratedContact(DecoratedContactDto dto) {
        return createDecoratedContact(dto, USER_3);
    }

    protected DecoratedContactDto user1UpdateDecoratedContact(Long contactId, DecoratedContactDto dto) {
        return updateDecoratedContact(contactId, dto, USER_1);
    }

    protected DecoratedContactDto user2UpdateDecoratedContact(Long contactId, DecoratedContactDto dto) {
        return updateDecoratedContact(contactId, dto, USER_2);
    }

    protected DecoratedContactDto user3UpdateDecoratedContact(Long contactId, DecoratedContactDto dto) {
        return updateDecoratedContact(contactId, dto, USER_3);
    }

    protected void user1DeleteDecoratedContact(Long contactId) {
        deleteDecoratedContact(contactId, USER_1);
    }

    protected void user2DeleteDecoratedContact(Long contactId) {
        deleteDecoratedContact(contactId, USER_2);
    }

    protected void user3DeleteDecoratedContact(Long contactId) {
        deleteDecoratedContact(contactId, USER_3);
    }

    protected PageDto<ContactDto> user1GetContacts(ContactsQueryDto queryDto) {
        return getContactsPage(queryDto, USER_1);
    }

    protected PageDto<ContactDto> user2GetContacts(ContactsQueryDto queryDto) {
        return getContactsPage(queryDto, USER_2);
    }

    protected PageDto<ContactDto> user3GetContacts(ContactsQueryDto queryDto) {
        return getContactsPage(queryDto, USER_3);
    }

    protected ContactDto user1GetContact(Long contactId) {
        return getContact(contactId, USER_1);
    }

    protected ContactDto user2GetContact(Long contactId) {
        return getContact(contactId, USER_2);
    }

    protected ContactDto user3GetContact(Long contactId) {
        return getContact(contactId, USER_3);
    }

    protected void user1UpdateAbout(Long contactId, AboutDto dto) {
        updateAbout(contactId, dto, USER_1);
    }

    protected void user2UpdateAbout(Long contactId, AboutDto dto) {
        updateAbout(contactId, dto, USER_2);
    }

    protected void user3UpdateAbout(Long contactId, AboutDto dto) {
        updateAbout(contactId, dto, USER_3);
    }

    protected AboutDto user1GetAbout(Long contactId) {
        return getAbout(contactId, USER_1);
    }

    protected AboutDto user2GetAbout(Long contactId) {
        return getAbout(contactId, USER_2);
    }

    protected AboutDto user3GetAbout(Long contactId) {
        return getAbout(contactId, USER_3);
    }

    protected DecoratedContactDto createDecoratedContact(DecoratedContactDto dto, ComponentTestUser user) {
        var returnType = new TypeRef<DecoratedContactDto>() {
        };
        return postWithResponse(DECORATED_CONTACTS_URL, user, dto, returnType);
    }

    protected DecoratedContactDto updateDecoratedContact(Long contactId, DecoratedContactDto dto, ComponentTestUser user) {
        var returnType = new TypeRef<DecoratedContactDto>() {
        };
        var url = format(DECORATED_CONTACT_URL, contactId);
        return putWithResponse(url, user, dto, returnType);
    }

    protected void deleteDecoratedContact(Long contactId, ComponentTestUser user) {
        var url = format(DECORATED_CONTACT_URL, contactId);
        delete(url, user);
    }

    protected PageDto<ContactDto> getContactsPage(ContactsQueryDto queryDto, ComponentTestUser user) {
        var url = appendQueryToUrl(CONTACTS_URL, queryDto);
        var typeRef = new TypeRef<PageDto<ContactDto>>() {
        };
        return getPage(url, user, typeRef);
    }

    protected ContactDto getContact(Long contactId, ComponentTestUser user) {
        var url = format(CONTACT_URL, contactId);
        var typeRef = new TypeRef<ContactDto>() {
        };
        return getOne(url, user, typeRef);
    }

    protected void updateAbout(Long contactId, AboutDto dto, ComponentTestUser user) {
        var url = format(ABOUT_URL, contactId);
        put(url, user, dto);
    }

    protected AboutDto getAbout(Long contactId, ComponentTestUser user) {
        var url = format(ABOUT_URL, contactId);
        var typeRef = new TypeRef<AboutDto>() {
        };
        return getOne(url, user, typeRef);
    }

    protected void user1UpdateDecoratedContactExpectNotFound(Long contactId, DecoratedContactDto dto) {
        updateDecoratedContactExpectNotFound(contactId, dto, USER_1);
    }

    protected void user2UpdateDecoratedContactExpectNotFound(Long contactId, DecoratedContactDto dto) {
        updateDecoratedContactExpectNotFound(contactId, dto, USER_2);
    }

    protected void user3UpdateDecoratedContactExpectNotFound(Long contactId, DecoratedContactDto dto) {
        updateDecoratedContactExpectNotFound(contactId, dto, USER_3);
    }

    protected void user1DeleteDecoratedContactExpectNotFound(Long contactId) {
        deleteDecoratedContactExpectNotFound(contactId, USER_1);
    }

    protected void user2DeleteDecoratedContactExpectNotFound(Long contactId) {
        deleteDecoratedContactExpectNotFound(contactId, USER_2);
    }

    protected void user3DeleteDecoratedContactExpectNotFound(Long contactId) {
        deleteDecoratedContactExpectNotFound(contactId, USER_3);
    }

    protected void user1GetContactExpectNotFound(Long contactId) {
        getContactExpectNotFound(contactId, USER_1);
    }

    protected void user2GetContactExpectNotFound(Long contactId) {
        getContactExpectNotFound(contactId, USER_2);
    }

    protected void user3GetContactExpectNotFound(Long contactId) {
        getContactExpectNotFound(contactId, USER_3);
    }

    protected void user1UpdateAboutExpectNotFound(Long contactId, AboutDto dto) {
        updateAboutExpectNotFound(contactId, dto, USER_1);
    }

    protected void user2UpdateAboutExpectNotFound(Long contactId, AboutDto dto) {
        updateAboutExpectNotFound(contactId, dto, USER_2);
    }

    protected void user3UpdateAboutExpectNotFound(Long contactId, AboutDto dto) {
        updateAboutExpectNotFound(contactId, dto, USER_3);
    }

    protected void user1GetAboutExpectNotFound(Long contactId) {
        getAboutExpectNotFound(contactId, USER_1);
    }

    protected void user2GetAboutExpectNotFound(Long contactId) {
        getAboutExpectNotFound(contactId, USER_2);
    }

    protected void user3GetAboutExpectNotFound(Long contactId) {
        getAboutExpectNotFound(contactId, USER_3);
    }

    protected void updateDecoratedContactExpectNotFound(Long contactId, DecoratedContactDto dto, ComponentTestUser user) {
        var url = format(DECORATED_CONTACT_URL, contactId);
        putExpectNotFound(url, user, dto);
    }

    protected void deleteDecoratedContactExpectNotFound(Long contactId, ComponentTestUser user) {
        var url = format(DECORATED_CONTACT_URL, contactId);
        deleteExpectNotFound(url, user);
    }

    protected void getContactExpectNotFound(Long contactId, ComponentTestUser user) {
        var url = format(CONTACT_URL, contactId);
        getExpectNotFound(url, user);
    }

    protected void updateAboutExpectNotFound(Long contactId, AboutDto dto, ComponentTestUser user) {
        var url = format(ABOUT_URL, contactId);
        putExpectNotFound(url, user, dto);
    }

    protected void getAboutExpectNotFound(Long contactId, ComponentTestUser user) {
        var url = format(ABOUT_URL, contactId);
        getExpectNotFound(url, user);
    }

    protected String appendQueryToUrl(String url, ContactsQueryDto queryDto) {
        var sb = new StringBuilder(url);
        var filters = toUrlFilterParams(queryDto.getFilter());
        var pagination = toUrlPaginationParams(queryDto.getPagination());
        var sort = toUrlSortParams(queryDto.getSort());

        if (isNotBlank(filters) || isNotBlank(pagination) || isNotBlank(sort)) {
            sb.append("?");
            sb.append(filters);
            sb.append(pagination);
            sb.append(sort);
        }
        return sb.toString();
    }

    protected String toUrlFilterParams(ContactsFilterDto filterDto) {
        var sb = new StringBuilder();
        if (nonNull(filterDto)) {
            if (isNotEmpty(filterDto.getFirstName())) {
                sb.append("filter.firstName=");
                sb.append(filterDto.getFirstName());
                sb.append("&");
            }
            if (isNotEmpty(filterDto.getLastName())) {
                sb.append("filter.lastName=");
                sb.append(filterDto.getLastName());
                sb.append("&");
            }
            if (isNotEmpty(filterDto.getCountry())) {
                sb.append("filter.country=");
                sb.append(filterDto.getCountry());
                sb.append("&");
            }
            if (isNotEmpty(filterDto.getCity())) {
                sb.append("filter.city=");
                sb.append(filterDto.getCity());
                sb.append("&");
            }
            if (isNotEmpty(filterDto.getStatus())) {
                sb.append("filter.status=");
                sb.append(filterDto.getStatus());
                sb.append("&");
            }
            if (isNotEmpty(filterDto.getSource())) {
                sb.append("filter.source=");
                sb.append(filterDto.getSource());
                sb.append("&");
            }
            if (isNotEmpty(filterDto.getLabels())) {
                sb.append("filter.labels=");
                sb.append(filterDto.getLabels());
                sb.append("&");
            }
            if (isNotEmpty(filterDto.getIndustries())) {
                sb.append("filter.industries=");
                sb.append(filterDto.getIndustries());
                sb.append("&");
            }
        }
        return sb.toString();
    }

    protected List<Contact> createContacts(ComponentTestUser user) {
        return createContacts(user, 3);
    }

    protected List<Contact> createContacts(ComponentTestUser user, int numOfContacts) {
        return IntStream.range(0, numOfContacts).mapToObj((i) -> createContact(user)).collect(toList());
    }
}
