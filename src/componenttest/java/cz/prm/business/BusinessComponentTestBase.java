package cz.prm.business;

import static cz.prm.utils.ContactComponentTestUtils.about;
import static cz.prm.utils.ContactComponentTestUtils.contact;
import static cz.prm.utils.NoteComponentTestUtils.note;
import static cz.prm.utils.ReferralComponentTestUtils.reminder;
import static cz.prm.utils.SecurityContextComponentTestUtils.clearContext;
import static cz.prm.utils.SecurityContextComponentTestUtils.ensureUserContext;
import static cz.prm.utils.TaskComponentTestUtils.task;
import static java.util.stream.Collectors.toList;

import cz.prm.ComponentTestBase;
import cz.prm.controllers.dto.contact.ContactDto;
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
    protected ComponentTestReferralRepository reminderRepository;
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
        reminderRepository.deleteAll();
        taskRepository.deleteAll();
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
        var reminder = reminder(contact.getContactId());
        var savedReferral = reminderRepository.save(reminder);
        clearContext();
        return savedReferral;
    }

    protected List<Task> createTasks(ComponentTestUser user) {
        return createTasks(user, 3);
    }

    protected List<Task> createTasks(ComponentTestUser user, int numOfTasks) {
        var contact = createContact(user);
        return IntStream.range(0, numOfTasks).mapToObj((i) -> createTask(user, contact)).collect(toList());
    }

    protected Task createTask(ComponentTestUser user) {
        var contact = createContact(user);
        return createTask(user, contact);
    }

    protected Task createTask(ComponentTestUser user, Contact contact) {
        ensureUserContext(user);
        var task = task(contact.getContactId());
        var savedTask = taskRepository.save(task);
        clearContext();
        return savedTask;
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
        return reminderRepository.getByDescription(dto.getDescription());
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
