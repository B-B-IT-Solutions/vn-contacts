package cz.prm.controllers;

import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.ContactUtils.about;
import static cz.prm.utils.ContactUtils.aboutDto;
import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.ContactUtils.contacts;
import static cz.prm.utils.ContactUtils.contactsQueryDto;
import static cz.prm.utils.ContactUtils.decoratedContact;
import static cz.prm.utils.ContactUtils.decoratedContactDto;
import static cz.prm.utils.MockitoUtils.returnParamAnswer;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.assertions.ContactAssertions.assertAbout;
import static cz.prm.utils.assertions.ContactAssertions.assertContact;
import static cz.prm.utils.assertions.ContactAssertions.assertContactQuery;
import static cz.prm.utils.assertions.ContactAssertions.assertDecoratedContact;
import static cz.prm.utils.assertions.ContactAssertions.assertPage;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.prm.controllers.mappers.ContactMapper;
import cz.prm.domain.contact.About;
import cz.prm.domain.contact.DecoratedContact;
import cz.prm.domain.contact.query.ContactsQuery;
import cz.prm.services.contact.ContactClearingHouse;
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
    private ContactClearingHouse clearingHouse;
    @Captor
    private ArgumentCaptor<DecoratedContact> decoratedContactCapt;
    @Captor
    private ArgumentCaptor<About> aboutCapt;
    @Captor
    private ArgumentCaptor<ContactsQuery> cQueryCapt;

    private ContactMapper mapper = MapperUtils.getContactMapper();
    private ContactController controller;

    @BeforeEach
    void setUp() {
        controller = new ContactController(clearingHouse, mapper);
    }

    @Test
    void getDecoratedContact() {
        var dc = decoratedContact();
        var contactId = randomLong();
        when(clearingHouse.getDecoratedContact(contactId)).thenReturn(dc);

        var responseDto = controller.getDecoratedContact(contactId);
        verify(clearingHouse).getDecoratedContact(contactId);
        assertDecoratedContact(dc, responseDto);
    }

    @Test
    void createDecoratedContact() {
        var dto = decoratedContactDto();
        when(clearingHouse.createDecoratedContact(any(DecoratedContact.class))).thenAnswer(returnParamAnswer(0));

        var responseDto = controller.createDecoratedContact(dto);
        verify(clearingHouse).createDecoratedContact(decoratedContactCapt.capture());
        var dc = decoratedContactCapt.getValue();
        assertDecoratedContact(dc, dto);
        assertDecoratedContact(dc, responseDto);
    }

    @Test
    void updateDecoratedContact() {
        var dto = decoratedContactDto();
        var contactId = randomLong();
        when(clearingHouse.updateDecoratedContact(eq(contactId), any(DecoratedContact.class))).thenAnswer(returnParamAnswer(1));

        var responseDto = controller.updateDecoratedContact(contactId, dto);
        verify(clearingHouse).updateDecoratedContact(eq(contactId), decoratedContactCapt.capture());
        var dc = decoratedContactCapt.getValue();
        assertDecoratedContact(dc, dto);
        assertDecoratedContact(dc, responseDto);
    }

    @Test
    void deleteDecoratedContact() {
        var contactId = randomLong();
        controller.deleteDecoratedContact(contactId);
        verify(clearingHouse).deleteDecoratedContact(contactId);
    }

    @Test
    void getContacts() {
        var page = page(contacts());
        var queryDto = contactsQueryDto();
        when(clearingHouse.getContacts(any(ContactsQuery.class))).thenReturn(page);

        var result = controller.getContacts(queryDto);
        assertPage(page, result);
        verify(clearingHouse).getContacts(cQueryCapt.capture());
        var query = cQueryCapt.getValue();
        assertContactQuery(query, queryDto);
    }

    @Test
    void getContact() {
        var contact = contact();
        var contactId = contact.getContactId();
        when(clearingHouse.getContact(contactId)).thenReturn(contact);
        var result = controller.getContact(contactId);
        assertContact(contact, result);
    }

    @Test
    void getAbout() {
        var about = about();
        var contactId = about.getContactId();
        when(clearingHouse.getAbout(contactId)).thenReturn(about);
        var result = controller.getAbout(contactId);
        assertAbout(about, result);
    }

    @Test
    void updateAbout() {
        var dto = aboutDto();
        controller.updateAbout(dto.getContactId(), dto);
        verify(clearingHouse).updateAbout(eq(dto.getContactId()), aboutCapt.capture());
        var about = aboutCapt.getValue();
        assertAbout(about, dto);
    }
}