package cz.prm.business.contacts;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static cz.prm.utils.ContactComponentTestUtils.contact;
import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import cz.prm.ComponentTestBase;
import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.contact.query.ContactsFilterDto;
import cz.prm.controllers.dto.contact.query.ContactsQueryDto;
import cz.prm.custom.ComponentTestContactRepository;
import cz.prm.domain.contact.Contact;
import cz.prm.services.contact.ContactService;
import cz.prm.utils.ComponentTestUser;
import io.restassured.common.mapper.TypeRef;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public class ContactComponentTestBase extends ComponentTestBase {

   protected static String CONTACTS_URL = "contacts";
   protected static String CONTACT_URL = CONTACTS_URL + "/%s";

   @Autowired
   protected ComponentTestContactRepository contactRepository;
   @Autowired
   protected ContactService contactService;

   @BeforeEach
   void setUp() {
      contactRepository.deleteAll();
   }

   protected void user1CreateContact(ContactDto dto) {
      createContact(dto, USER_1);
   }

   protected void user2CreateContact(ContactDto dto) {
      createContact(dto, USER_2);
   }

   protected void user3CreateContact(ContactDto dto) {
      createContact(dto, USER_3);
   }

   protected void user1UpdateContact(Long contactId, ContactDto dto) {
      updateContact(contactId, dto, USER_1);
   }

   protected void user2UpdateContact(Long contactId, ContactDto dto) {
      updateContact(contactId, dto, USER_2);
   }

   protected void user3UpdateContact(Long contactId, ContactDto dto) {
      updateContact(contactId, dto, USER_3);
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

   protected void createContact(ContactDto dto, ComponentTestUser user) {
      post(CONTACTS_URL, user, dto);
   }

   protected void updateContact(Long contactId, ContactDto dto, ComponentTestUser user) {
      var url = format(CONTACT_URL, contactId);
      put(url, user, dto);
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

   protected void user1UpdateContactExpectNotFound(Long contactId, ContactDto dto) {
      updateContactExpectNotFound(contactId, dto, USER_1);
   }

   protected void user2UpdateContactExpectNotFound(Long contactId, ContactDto dto) {
      updateContactExpectNotFound(contactId, dto, USER_2);
   }

   protected void user3UpdateContactExpectNotFound(Long contactId, ContactDto dto) {
      updateContactExpectNotFound(contactId, dto, USER_3);
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

   protected void updateContactExpectNotFound(Long contactId, ContactDto dto, ComponentTestUser user) {
      var url = format(CONTACT_URL, contactId);
      putExpectNotFound(url, user, dto);
   }

   protected void getContactExpectNotFound(Long contactId, ComponentTestUser user) {
      var url = format(CONTACT_URL, contactId);
      getExpectNotFound(url, user);
   }

   protected String appendQueryToUrl(String url, ContactsQueryDto queryDto) {
      var sb = new StringBuilder(url);
      var filters = toUrlFilterParams(queryDto.getFilter());
      var pagination = toUrlPaginationParams(queryDto.getPagination());

      if (isNotBlank(filters) || isNotBlank(pagination)) {
         sb.append("?");
         sb.append(filters);
         sb.append(pagination);
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
         if (isNotEmpty(filterDto.getEmail())) {
            sb.append("filter.email=");
            sb.append(filterDto.getLastName());
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

   protected Contact createContact(ComponentTestUser user) {
      var contact = contact(user);
      return contactRepository.save(contact);
   }

   protected Contact getContactFromDb(ContactDto dto) {
      return contactRepository.getByEmail(dto.getEmail());
   }
}
