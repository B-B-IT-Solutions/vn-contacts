package cz.prm.business;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.ContactComponentTestUtils.about;
import static cz.prm.utils.ContactComponentTestUtils.contact;
import static cz.prm.utils.NoteComponentTestUtils.note;
import static cz.prm.utils.ReferralComponentTestUtils.referral;
import static cz.prm.utils.SecurityContextComponentTestUtils.clearContext;
import static cz.prm.utils.SecurityContextComponentTestUtils.ensureUserContext;
import static cz.prm.utils.TaskComponentTestUtils.task;
import static java.util.stream.Collectors.toList;

import cz.prm.ComponentTestBase;
import cz.prm.controllers.dto.contacts.contact.ContactDto;
import cz.prm.controllers.dto.contacts.contact.DecoratedContactDto;
import cz.prm.controllers.dto.contacts.note.NoteDto;
import cz.prm.controllers.dto.contacts.referral.ReferralDto;
import cz.prm.controllers.dto.contacts.task.TaskDto;
import cz.prm.controllers.dto.settings.AccountSettingsDto;
import cz.prm.controllers.dto.settings.contact.ContactSettingsDto;
import cz.prm.controllers.dto.settings.note.NoteSettingsDto;
import cz.prm.controllers.dto.settings.notifications.NotificationSettingsDto;
import cz.prm.custom.ComponentTestAboutRepository;
import cz.prm.custom.ComponentTestAccountSettingsRepository;
import cz.prm.custom.ComponentTestContactRepository;
import cz.prm.custom.ComponentTestContactSettingsRepository;
import cz.prm.custom.ComponentTestNoteRepository;
import cz.prm.custom.ComponentTestNoteSettingsRepository;
import cz.prm.custom.ComponentTestNotificationSettingsRepository;
import cz.prm.custom.ComponentTestReferralRepository;
import cz.prm.custom.ComponentTestTaskRepository;
import cz.prm.domain.contacts.contact.About;
import cz.prm.domain.contacts.contact.Contact;
import cz.prm.domain.contacts.note.Note;
import cz.prm.domain.contacts.referral.Referral;
import cz.prm.domain.contacts.task.Task;
import cz.prm.domain.customizations.contact.ContactSettings;
import cz.prm.domain.customizations.note.NoteSettings;
import cz.prm.domain.settings.AccountSettings;
import cz.prm.domain.settings.notifications.NotificationSettings;
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
    protected ComponentTestAccountSettingsRepository accountSettingsRepository;
    @Autowired
    protected ComponentTestContactSettingsRepository contactSettingsRepository;
    @Autowired
    protected ComponentTestNoteSettingsRepository noteSettingsRepository;
    @Autowired
    protected ComponentTestNotificationSettingsRepository notificationSettingsRepository;

    @BeforeEach
    void setUp() {
        noteRepository.deleteAll();
        taskRepository.deleteAll();
        referralRepository.deleteAll();
        aboutRepository.deleteAll();
        contactRepository.deleteAll();
        contactSettingsRepository.deleteAll();
        noteSettingsRepository.deleteAll();
        notificationSettingsRepository.deleteAll();
    }

    protected List<Contact> createReferralSuggestions(Contact contact, ComponentTestUser user) {
        return createReferralSuggestions(contact, user, 10);
    }

    protected List<Contact> createReferralSuggestions(Contact contact, ComponentTestUser user, int numOfContacts) {
        return IntStream.range(0, numOfContacts).mapToObj((i) -> createReferralSuggestion(contact, user)).collect(toList());
    }

    protected Contact createReferralSuggestion(Contact contact, ComponentTestUser user) {
        ensureUserContext(user);
        var industries = contact.getIndustries();
        var industry1 = industries.get(0);
        var industry2 = industries.get(1);
        var potentialReferral = contact();
        potentialReferral.getIndustries().addAll(newArrayList(industry1, industry2));
        var savedPotentialReferral = contactRepository.save(potentialReferral);
        var about = about(savedPotentialReferral);
        aboutRepository.save(about);
        clearContext();
        return savedPotentialReferral;
    }

    protected List<Contact> createDecoratedContacts(ComponentTestUser user) {
        return createDecoratedContacts(user, 3);
    }

    protected List<Contact> createDecoratedContacts(ComponentTestUser user, int numOfContacts) {
        return IntStream.range(0, numOfContacts).mapToObj((i) -> createDecoratedContact(user)).collect(toList());
    }

    protected Contact createDecoratedContact(ComponentTestUser user) {
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
        var contact = createDecoratedContact(user);
        return IntStream.range(0, numOfNotes).mapToObj((i) -> createNote(user, contact)).collect(toList());
    }

    protected List<Note> createReferralNotes(ComponentTestUser user, int numOfNotes) {
        var referral = createReferral(user);
        return IntStream.range(0, numOfNotes).mapToObj((i) -> createNote(user, referral)).collect(toList());
    }

    protected Note createContactNote(ComponentTestUser user) {
        var contact = createDecoratedContact(user);
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
        var contact = createDecoratedContact(user);
        return IntStream.range(0, numOfTasks).mapToObj((i) -> createReferral(user, contact)).collect(toList());
    }

    protected Referral createReferral(ComponentTestUser user) {
        var contact = createDecoratedContact(user);
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
        var contact = createDecoratedContact(user);
        var referral = createReferral(user);
        return IntStream.range(0, numOfTasks).mapToObj((i) -> createTask(user, contact, referral)).collect(toList());
    }

    protected Task createTask(ComponentTestUser user) {
        var contact = createDecoratedContact(user);
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

    protected AccountSettings getAccountSettingsFromDb(AccountSettingsDto dto) {
        return accountSettingsRepository.getReferenceById(dto.getSettingsId());
    }

    protected ContactSettings getContactSettingsFromDb(ContactSettingsDto dto) {
        return contactSettingsRepository.getReferenceById(dto.getSettingsId());
    }

    protected NoteSettings getNoteSettingsFromDb(NoteSettingsDto dto) {
        return noteSettingsRepository.getReferenceById(dto.getSettingsId());
    }

    protected NotificationSettings getNotificationSettingsFromDb(NotificationSettingsDto dto) {
        return notificationSettingsRepository.getReferenceById(dto.getSettingsId());
    }
}
