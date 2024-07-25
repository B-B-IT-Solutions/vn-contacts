package cz.prm.business;

import static cz.prm.utils.ContactComponentTestUtils.contact;
import static cz.prm.utils.NoteComponentTestUtils.note;
import static cz.prm.utils.SecurityContextComponentTestUtils.clearContext;
import static cz.prm.utils.SecurityContextComponentTestUtils.ensureUserContext;
import static java.util.stream.Collectors.toList;

import cz.prm.ComponentTestBase;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.note.NoteDto;
import cz.prm.controllers.dto.settings.AccountSettingsDto;
import cz.prm.controllers.dto.settings.ContactSettingsDto;
import cz.prm.custom.ComponentTestAccountSettingsRepository;
import cz.prm.custom.ComponentTestContactRepository;
import cz.prm.custom.ComponentTestContactSettingsRepository;
import cz.prm.custom.ComponentTestNoteRepository;
import cz.prm.domain.contact.Contact;
import cz.prm.domain.note.Note;
import cz.prm.domain.settings.AccountSettings;
import cz.prm.domain.settings.ContactSettings;
import cz.prm.utils.ComponentTestUser;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public class BusinessComponentTestBase extends ComponentTestBase {

    @Autowired
    protected ComponentTestContactRepository contactRepository;
    @Autowired
    protected ComponentTestNoteRepository noteRepository;
    @Autowired
    protected ComponentTestAccountSettingsRepository generalSettingsRepository;
    @Autowired
    protected ComponentTestContactSettingsRepository userSettingsRepository;

    @BeforeEach
    void setUp() {
        noteRepository.deleteAll();
        contactRepository.deleteAll();
        userSettingsRepository.deleteAll();
    }

    protected List<Contact> createContacts(ComponentTestUser user) {
        return createContacts(user, 3);
    }

    protected List<Contact> createContacts(ComponentTestUser user, int numOfContacts) {
        return IntStream.range(0, numOfContacts).mapToObj((i) -> createContact(user)).collect(toList());
    }

    protected Contact createContact(ComponentTestUser user) {
        ensureUserContext(user);
        var contact = contact();
        var savedContact = contactRepository.save(contact);
        clearContext();
        return savedContact;
    }

    protected List<Note> createNotes(ComponentTestUser user) {
        return createNotes(user, 3);
    }

    protected List<Note> createNotes(ComponentTestUser user, int numOfNotes) {
        var contact = createContact(user);
        return IntStream.range(0, numOfNotes).mapToObj((i) -> createNote(user, contact)).collect(toList());
    }

    protected Note createNote(ComponentTestUser user) {
        var contact = createContact(user);
        return createNote(user, contact);
    }

    protected Note createNote(ComponentTestUser user, Contact contact) {
        ensureUserContext(user);
        var note = note(contact.getContactId());
        var savedNote = noteRepository.save(note);
        clearContext();
        return savedNote;
    }

    protected Contact getContactFromDb(ContactDto dto) {
        return contactRepository.getByLastName(dto.getLastName());
    }

    protected Note getNoteFromDb(NoteDto dto) {
        return noteRepository.getByText(dto.getText());
    }

    protected AccountSettings getGeneralSettingsFromDb(AccountSettingsDto dto) {
        return generalSettingsRepository.getReferenceById(dto.getSettingsId());
    }

    protected ContactSettings getUserSettingsFromDb(ContactSettingsDto dto) {
        return userSettingsRepository.getReferenceById(dto.getSettingsId());
    }
}
