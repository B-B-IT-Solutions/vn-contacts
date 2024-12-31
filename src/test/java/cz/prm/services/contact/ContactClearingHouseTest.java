package cz.prm.services.contact;

import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.ContactUtils.about;
import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.ContactUtils.contacts;
import static cz.prm.utils.ContactUtils.contactsQuery;
import static cz.prm.utils.assertions.ContactAssertions.assertAbout;
import static cz.prm.utils.assertions.ContactAssertions.assertContact;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.prm.services.NoteService;
import cz.prm.services.ReminderService;
import cz.prm.services.TaskService;
import cz.prm.services.contact.data.AboutService;
import cz.prm.services.contact.data.ContactService;
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
    private ReminderService reminderService;

    private ContactClearingHouse clearingHouse;

    @BeforeEach
    void setUp() {
        clearingHouse = new ContactClearingHouse(contactService, aboutService, noteService, taskService, reminderService);
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
    void createContact() {
        var contact = contact();
        when(contactService.createContact(contact)).thenAnswer((invocation -> invocation.getArgument(0)));
        clearingHouse.createContact(contact);
        verify(contactService).createContact(contact);
        verify(aboutService).createAbout(contact.getContactId());
    }

    @Test
    void updateContact() {
        var contact = contact();
        clearingHouse.updateContact(contact.getContactId(), contact);
        verify(contactService).updateContact(contact.getContactId(), contact);
    }

    @Test
    void deleteContact() {
        var contact = contact();
        clearingHouse.deleteContact(contact.getContactId());
        verify(aboutService).deleteAbout(contact.getContactId());
        verify(noteService).deleteByContactId(contact.getContactId());
        verify(taskService).deleteByContactId(contact.getContactId());
        verify(reminderService).deleteByContactId(contact.getContactId());
        verify(contactService).deleteContact(contact.getContactId());
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