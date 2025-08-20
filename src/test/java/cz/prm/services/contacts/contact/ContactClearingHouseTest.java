package cz.prm.services.contacts.contact;

import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.MockitoUtils.returnParamAnswer;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.assertions.ContactAssertions.assertAbout;
import static cz.prm.utils.assertions.ContactAssertions.assertContact;
import static cz.prm.utils.assertions.ContactAssertions.assertDecoratedContact;
import static cz.prm.utils.data.contacts.ContactUtils.about;
import static cz.prm.utils.data.contacts.ContactUtils.contact;
import static cz.prm.utils.data.contacts.ContactUtils.contacts;
import static cz.prm.utils.data.contacts.ContactUtils.contactsQuery;
import static cz.prm.utils.data.contacts.ContactUtils.decoratedContact;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.prm.services.contacts.contact.data.AboutService;
import cz.prm.services.contacts.contact.data.ContactService;
import cz.prm.services.contacts.note.NoteService;
import cz.prm.services.contacts.referral.ReferralService;
import cz.prm.services.contacts.task.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContactClearingHouseTest {

    @Mock
    private ContactService contactService;
    @Mock
    private AboutService aboutService;
    @Mock
    private NoteService noteService;
    @Mock
    private TaskService taskService;
    @Mock
    private ReferralService referralService;

    private ContactClearingHouse clearingHouse;

    @BeforeEach
    void setUp() {
        clearingHouse = new ContactClearingHouse(contactService, aboutService, noteService, taskService, referralService);
    }

    @Test
    void getDecoratedContact() {
        var contact = contact();
        var about = about();
        var contactId = randomLong();
        when(contactService.getContact(contactId)).thenReturn(contact);
        when(aboutService.getAbout(contactId)).thenReturn(about);

        var response = clearingHouse.getDecoratedContact(contactId);
        verify(contactService).getContact(contactId);
        verify(aboutService).getAbout(contactId);
        assertContact(response.getContact(), contact);
        assertAbout(response.getAbout(), about);
    }

    @Test
    void createDecoratedContact() {
        var dc = decoratedContact();
        var contact = dc.getContact();
        var about = dc.getAbout();
        when(contactService.createContact(contact)).thenAnswer(returnParamAnswer(0));
        when(aboutService.createAbout(contact.getContactId(), about)).thenAnswer(returnParamAnswer(1));

        var response = clearingHouse.createDecoratedContact(dc);
        verify(contactService).createContact(contact);
        verify(aboutService).createAbout(contact.getContactId(), about);
        assertDecoratedContact(dc, response);
    }

    @Test
    void updateDecoratedContact() {
        var dc = decoratedContact();
        var contact = dc.getContact();
        var about = dc.getAbout();
        var contactId = randomLong();
        when(contactService.updateContact(contactId, contact)).thenAnswer(returnParamAnswer(1));
        when(aboutService.updateAbout(contactId, about)).thenAnswer(returnParamAnswer(1));

        var response = clearingHouse.updateDecoratedContact(contactId, dc);
        verify(contactService).updateContact(contactId, contact);
        verify(aboutService).updateAbout(contactId, about);
        assertDecoratedContact(dc, response);
    }

    @Test
    void deleteDecoratedContact() {
        var contact = contact();
        clearingHouse.deleteDecoratedContact(contact.getContactId());
        verify(noteService).deleteByContactId(contact.getContactId());
        verify(taskService).deleteByContactId(contact.getContactId());
        verify(referralService).deleteByContactId(contact.getContactId());
        verify(aboutService).deleteAbout(contact.getContactId());
        verify(contactService).deleteContact(contact.getContactId());
    }

    @Test
    void getContacts() {
        var contacts = contacts();
        var page = page(contacts);
        var query = contactsQuery();
        when(contactService.getContacts(query)).thenReturn(page);
        var result = clearingHouse.getContacts(query);
        assertThat(result).isEqualTo(page);
    }

    @Test
    void getContact() {
        var contact = contact();
        when(contactService.getContact(contact.getContactId())).thenReturn(contact);
        var result = clearingHouse.getContact(contact.getContactId());
        assertContact(result, contact);
    }

    @Test
    void getAbout() {
        var about = about();
        when(aboutService.getAbout(about.getContactId())).thenReturn(about);
        var result = clearingHouse.getAbout(about.getContactId());
        assertAbout(result, about);
    }

    @Test
    void updateAbout() {
        var about = about();
        clearingHouse.updateAbout(about.getContactId(), about);
        verify(aboutService).updateAbout(about.getContactId(), about);
    }
}