package cz.prm.services.contact;

import cz.prm.domain.common.query.Page;
import cz.prm.domain.contacts.contact.About;
import cz.prm.domain.contacts.contact.Contact;
import cz.prm.domain.contacts.contact.DecoratedContact;
import cz.prm.domain.contacts.contact.query.ContactsQuery;
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

    public DecoratedContact getDecoratedContact(Long contactId) {
        var contact = contactService.getContact(contactId);
        var about = aboutService.getAbout(contactId);
        return new DecoratedContact(contact, about);
    }

    public DecoratedContact createDecoratedContact(DecoratedContact ce) {
        var savedContact = contactService.createContact(ce.getContact());
        var savedAbout = aboutService.createAbout(savedContact.getContactId(), ce.getAbout());
        return new DecoratedContact(savedContact, savedAbout);
    }

    public DecoratedContact updateDecoratedContact(Long contactId, DecoratedContact updatedDecoratedContact) {
        var updtedContact = contactService.updateContact(contactId, updatedDecoratedContact.getContact());
        var updatedAbout = aboutService.updateAbout(contactId, updatedDecoratedContact.getAbout());
        return new DecoratedContact(updtedContact, updatedAbout);
    }

    public void deleteDecoratedContact(Long contactId) {
        noteService.deleteByContactId(contactId);
        taskService.deleteByContactId(contactId);
        referralService.deleteByContactId(contactId);
        aboutService.deleteAbout(contactId);
        contactService.deleteContact(contactId);
    }

    public Page<Contact> getContacts(ContactsQuery query) {
        return contactService.getContacts(query);
    }

    public Contact getContact(Long contactId) {
        return contactService.getContact(contactId);
    }

    public About getAbout(Long contactId) {
        return aboutService.getAbout(contactId);
    }

    public void updateAbout(Long contactId, About updatedAbout) {
        aboutService.updateAbout(contactId, updatedAbout);
    }
}
