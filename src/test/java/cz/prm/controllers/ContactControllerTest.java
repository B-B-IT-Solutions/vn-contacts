package cz.prm.controllers;

import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.ContactUtils.about;
import static cz.prm.utils.ContactUtils.aboutDto;
import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.ContactUtils.contactDto;
import static cz.prm.utils.ContactUtils.contacts;
import static cz.prm.utils.ContactUtils.contactsQueryDto;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.assertions.ContactAssertions.assertAboutDto;
import static cz.prm.utils.assertions.ContactAssertions.assertContact;
import static cz.prm.utils.assertions.ContactAssertions.assertContactQuery;
import static cz.prm.utils.assertions.ContactAssertions.assertPage;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.prm.controllers.mappers.ContactMapper;
import cz.prm.domain.contact.About;
import cz.prm.domain.contact.Contact;
import cz.prm.domain.contact.query.ContactsQuery;
import cz.prm.services.AboutService;
import cz.prm.services.ContactService;
import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContactControllerTest {

    @Mock
    private ContactService contactService;
    @Mock
    private AboutService aboutService;
    @Captor
    private ArgumentCaptor<Contact> contactCapt;
    @Captor
    private ArgumentCaptor<About> aboutCapt;
    @Captor
    private ArgumentCaptor<ContactsQuery> cQueryCapt;

    private ContactMapper mapper = MapperUtils.getContactMapper();
    private ContactController controller;

    @BeforeEach
    void setUp() {
        controller = new ContactController(contactService, aboutService, mapper);
    }

    @Test
    void getContacts() {
        var page = page(contacts());
        var queryDto = contactsQueryDto();
        when(contactService.getContacts(any(ContactsQuery.class))).thenReturn(page);

        var result = controller.getContacts(queryDto);
        assertPage(page, result);
        verify(contactService).getContacts(cQueryCapt.capture());
        var query = cQueryCapt.getValue();
        assertContactQuery(query, queryDto);
    }

    @Test
    void getContact() {
        var contact = contact();
        var contactId = contact.getContactId();
        when(contactService.getContact(contactId)).thenReturn(contact);
        var result = controller.getContact(contactId);
        assertContact(contact, result);
    }

    @Test
    void createContact() {
        var dto = contactDto();
        controller.createContact(dto);
        verify(contactService).createContact(contactCapt.capture());
        var contact = contactCapt.getValue();
        assertContact(contact, dto);
    }

    @Test
    void updateContact() {
        var dto = contactDto();
        controller.updateContact(dto.getContactId(), dto);
        verify(contactService).updateContact(eq(dto.getContactId()), contactCapt.capture());
        var contact = contactCapt.getValue();
        assertContact(contact, dto);
    }

    @Test
    void deleteContact() {
        var contactId = randomLong();
        controller.deleteContact(contactId);
        verify(contactService).deleteContact(contactId);
    }

    @Test
    void getAbout() {
        var about = about();
        var contactId = about.getContactId();
        when(aboutService.getAbout(contactId)).thenReturn(about);
        var result = controller.getAbout(contactId);
        assertAboutDto(about, result);
    }

    @Test
    void updateAbout() {
        var dto = aboutDto();
        controller.updateAbout(dto.getContactId(), dto);
        verify(aboutService).updateAbout(eq(dto.getContactId()), aboutCapt.capture());
        var about = aboutCapt.getValue();
        assertAboutDto(about, dto);
    }
}