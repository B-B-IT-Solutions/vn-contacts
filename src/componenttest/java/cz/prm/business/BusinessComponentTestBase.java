package cz.prm.business;

import static cz.prm.utils.ContactComponentTestUtils.about;
import static cz.prm.utils.ContactComponentTestUtils.contact;
import static cz.prm.utils.NoteComponentTestUtils.note;
import static cz.prm.utils.ReferralComponentTestUtils.referral;
import static cz.prm.utils.SecurityContextComponentTestUtils.clearContext;
import static cz.prm.utils.SecurityContextComponentTestUtils.ensureUserContext;
import static cz.prm.utils.TaskComponentTestUtils.task;
import static java.util.stream.Collectors.toList;

import cz.prm.ComponentTestBase;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.contact.DecoratedContactDto;
import cz.prm.controllers.dto.note.NoteDto;
import cz.prm.controllers.dto.referral.ReferralDto;
import cz.prm.controllers.dto.settings.AccountSettingsDto;
import cz.prm.controllers.dto.settings.contact.ContactSettingsDto;
import cz.prm.controllers.dto.settings.note.NoteSettingsDto;
import cz.prm.controllers.dto.task.TaskDto;
import cz.prm.custom.ComponentTestAboutRepository;
import cz.prm.custom.ComponentTestAccountSettingsRepository;
import cz.prm.custom.ComponentTestContactRepository;
import cz.prm.custom.ComponentTestContactSettingsRepository;
import cz.prm.custom.ComponentTestNoteRepository;
import cz.prm.custom.ComponentTestNoteSettingsRepository;
import cz.prm.custom.ComponentTestReferralRepository;
import cz.prm.custom.ComponentTestTaskRepository;
import cz.prm.domain.contact.About;
import cz.prm.domain.contact.Contact;
import cz.prm.domain.note.Note;
import cz.prm.domain.referral.Referral;
import cz.prm.domain.settings.AccountSettings;
import cz.prm.domain.settings.contact.ContactSettings;
import cz.prm.domain.settings.note.NoteSettings;
import cz.prm.domain.task.Task;
import cz.prm.utils.ComponentTestUser;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public class BusinessComponentTestBase extends ComponentTestBase {

    @Autowired
    protected ComponentTestContactRepository contactRepository;
    @Autowired
    protected ComponentTestAboutRepository aboutRepository;
    @Autowired
    protected ComponentTestNoteRepository noteRepository;
    @Autowired
    protected ComponentTestReferralRepository referralRepository;
    @Autowired
    protected ComponentTestTaskRepository taskRepository;
    @Autowired
    protected ComponentTestAccountSettingsRepository generalSettingsRepository;
    @Autowired
    protected ComponentTestContactSettingsRepository contactSettingsRepository;
    @Autowired
    protected ComponentTestNoteSettingsRepository noteSettingsRepository;

    @BeforeEach
    void setUp() {
        noteRepository.deleteAll();
        taskRepository.deleteAll();
        referralRepository.deleteAll();
        aboutRepository.deleteAll();
        contactRepository.deleteAll();
        contactSettingsRepository.deleteAll();
        noteSettingsRepository.deleteAll();
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
        var about = about(savedContact);
        aboutRepository.save(about);
        clearContext();
        return savedContact;
    }

    protected List<Note> createContactNotes(ComponentTestUser user) {
        return createContactNotes(user, 3);
    }

    protected List<Note> createReferralNotes(ComponentTestUser user) {
        return createReferralNotes(user, 3);
    }

    protected List<Note> createContactNotes(ComponentTestUser user, int numOfNotes) {
        var contact = createContact(user);
        return IntStream.range(0, numOfNotes).mapToObj((i) -> createNote(user, contact)).collect(toList());
    }

    protected List<Note> createReferralNotes(ComponentTestUser user, int numOfNotes) {
        var referral = createReferral(user);
        return IntStream.range(0, numOfNotes).mapToObj((i) -> createNote(user, referral)).collect(toList());
    }

    protected Note createContactNote(ComponentTestUser user) {
        var contact = createContact(user);
        return createNote(user, contact);
    }

    protected Note createReferralNote(ComponentTestUser user) {
        var referral = createReferral(user);
        return createNote(user, referral);
    }

    protected Note createNote(ComponentTestUser user, Contact contact) {
        ensureUserContext(user);
        var note = note(contact.getContactId(), null);
        var savedNote = noteRepository.save(note);
        clearContext();
        return savedNote;
    }

    protected Note createNote(ComponentTestUser user, Referral referral) {
        ensureUserContext(user);
        var note = note(null, referral.getReferralId());
        var savedNote = noteRepository.save(note);
        clearContext();
        return savedNote;
    }

    protected List<Referral> createReferrals(ComponentTestUser user) {
        return createReferrals(user, 3);
    }

    protected List<Referral> createReferrals(ComponentTestUser user, int numOfTasks) {
        var contact = createContact(user);
        return IntStream.range(0, numOfTasks).mapToObj((i) -> createReferral(user, contact)).collect(toList());
    }

    protected Referral createReferral(ComponentTestUser user) {
        var contact = createContact(user);
        return createReferral(user, contact);
    }

    protected Referral createReferral(ComponentTestUser user, Contact contact) {
        ensureUserContext(user);
        var referral = referral(contact.getContactId());
        var savedReferral = referralRepository.save(referral);
        clearContext();
        return savedReferral;
    }

    protected List<Task> createTasks(ComponentTestUser user) {
        return createTasks(user, 3);
    }

    protected List<Task> createTasks(ComponentTestUser user, int numOfTasks) {
        var contact = createContact(user);
        var referral = createReferral(user);
        return IntStream.range(0, numOfTasks).mapToObj((i) -> createTask(user, contact, referral)).collect(toList());
    }

    protected Task createTask(ComponentTestUser user) {
        var contact = createContact(user);
        var referral = createReferral(user);
        return createTask(user, contact, referral);
    }

    protected Task createTask(ComponentTestUser user, Contact contact, Referral referral) {
        ensureUserContext(user);
        var task = task(contact.getContactId(), referral.getReferralId());
        var savedTask = taskRepository.save(task);
        clearContext();
        return savedTask;
    }

    protected Contact getContactFromDb(DecoratedContactDto dto) {
        return getContactFromDb(dto.getContact());
    }

    protected Contact getContactFromDb(ContactDto dto) {
        return contactRepository.getByFirstName(dto.getFirstName());
    }

    protected About getAboutFromDb(Contact contact) {
        return aboutRepository.getByContactId(contact.getContactId());
    }

    protected Note getNoteFromDb(NoteDto dto) {
        return noteRepository.getByText(dto.getText());
    }

    protected Referral getReferralFromDb(ReferralDto dto) {
        return referralRepository.getByNote(dto.getNote());
    }

    protected Task getTaskFromDb(TaskDto dto) {
        return taskRepository.getByDescription(dto.getDescription());
    }

    protected AccountSettings getGeneralSettingsFromDb(AccountSettingsDto dto) {
        return generalSettingsRepository.getReferenceById(dto.getSettingsId());
    }

    protected ContactSettings getContactSettingsFromDb(ContactSettingsDto dto) {
        return contactSettingsRepository.getReferenceById(dto.getSettingsId());
    }

    protected NoteSettings getNoteSettingsFromDb(NoteSettingsDto dto) {
        return noteSettingsRepository.getReferenceById(dto.getSettingsId());
    }
}
