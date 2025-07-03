package cz.prm.services.contact;

import cz.prm.domain.common.query.Page;
import cz.prm.domain.contact.About;
import cz.prm.domain.contact.Contact;
import cz.prm.domain.contact.query.ContactsQuery;
import cz.prm.services.TaskService;
import cz.prm.services.contact.data.AboutService;
import cz.prm.services.contact.data.ContactService;
import cz.prm.services.note.NoteService;
import cz.prm.services.referral.ReferralService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ContactClearingHouse {

    private ContactService contactService;

    private AboutService aboutService;
    private NoteService noteService;
    private TaskService taskService;
    private ReferralService referralService;

    @Autowired
    public ContactClearingHouse(ContactService contactService, AboutService aboutService, NoteService noteService, TaskService taskService,
        ReferralService referralService) {
        this.contactService = contactService;
        this.aboutService = aboutService;
        this.noteService = noteService;
        this.taskService = taskService;
        this.referralService = referralService;
    }

    public Page<Contact> getContacts(ContactsQuery query) {
        return contactService.getContacts(query);
    }

    public Contact getContact(Long contactId) {
        return contactService.getContact(contactId);
    }

    public void createContact(Contact contact) {
        var savedContact = contactService.createContact(contact);
        aboutService.createAbout(savedContact.getContactId());
    }

    public void updateContact(Long contactId, Contact updatedContact) {
        contactService.updateContact(contactId, updatedContact);
    }

    public void deleteContact(Long contactId) {
        noteService.deleteByContactId(contactId);
        taskService.deleteByContactId(contactId);
        referralService.deleteByContactId(contactId);
        aboutService.deleteAbout(contactId);
        contactService.deleteContact(contactId);
    }

    public About getAbout(Long contactId) {
        return aboutService.getAbout(contactId);
    }

    public void updateAbout(Long contactId, About updatedAbout) {
        aboutService.updateAbout(contactId, updatedAbout);
    }
}
