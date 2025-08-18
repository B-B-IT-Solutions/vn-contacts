package cz.prm.services;

import static cz.prm.domain.settings.contact.InitContactSettings.INITIAL_INDUSTRIES;
import static cz.prm.domain.settings.contact.InitContactSettings.INITIAL_PRODUCTS;
import static cz.prm.domain.settings.contact.InitContactSettings.INITIAL_SKILLS;
import static cz.prm.domain.settings.contact.InitContactSettings.INITIAL_TARGET_MARKETS;

import cz.prm.domain.settings.AccountSettings;
import cz.prm.domain.settings.contact.ContactSettings;
import cz.prm.domain.settings.note.NoteSettings;
import cz.prm.domain.settings.notifications.NotificationSettings;
import cz.prm.repositories.settings.AccountSettingsRepository;
import cz.prm.repositories.settings.ContactSettingsRepository;
import cz.prm.repositories.settings.NoteSettingsRepository;
import cz.prm.repositories.settings.NotificationSettingsRepository;
import cz.prm.repositories.settings.SettingsPredicates;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SettingsService {

    private AccountSettingsRepository accountSettingsRepository;
    private ContactSettingsRepository contactSettingsRepository;
    private NoteSettingsRepository noteSettingsRepository;
    private NotificationSettingsRepository notificationSettingsRepository;
    private SettingsPredicates predicates;

    public SettingsService(AccountSettingsRepository accountSettingsRepository, ContactSettingsRepository contactSettingsRepository,
        NoteSettingsRepository noteSettingsRepository, NotificationSettingsRepository notificationSettingsRepository, SettingsPredicates predicates) {
        this.accountSettingsRepository = accountSettingsRepository;
        this.contactSettingsRepository = contactSettingsRepository;
        this.noteSettingsRepository = noteSettingsRepository;
        this.notificationSettingsRepository = notificationSettingsRepository;
        this.predicates = predicates;
    }

    public AccountSettings getAccountSettings() {
        var predicate = predicates.accountSettings();
        var optional = accountSettingsRepository.findOne(predicate);
        return optional.orElseThrow(accountSettingsNotFoundSupplier());
    }

    public ContactSettings getContactSettings() {
        return getOrCreateContactSettings();
    }

    public NoteSettings getNoteSettings() {
        return getOrCreateNoteSettings();
    }

    public NotificationSettings getNotificationSettings() {
        return getOrCreateNotificationSettings();
    }

    public void updateContactSettings(ContactSettings updatedSettings) {
        var settings = getOrCreateContactSettings();
        updateContactSettingFields(settings, updatedSettings);
        contactSettingsRepository.save(settings);
    }

    public void updateNoteSettings(NoteSettings updatedSettings) {
        var settings = getOrCreateNoteSettings();
        updateNoteSettingFields(settings, updatedSettings);
        noteSettingsRepository.save(settings);
    }

    public NotificationSettings updateNotificationSettings(NotificationSettings updatedSettings) {
        var settings = getOrCreateNotificationSettings();
        updateNotificationSettingFields(settings, updatedSettings);
        return notificationSettingsRepository.save(settings);
    }

    private ContactSettings getOrCreateContactSettings() {
        var predicate = predicates.contactSettings();
        var optional = contactSettingsRepository.findOne(predicate);
        if (optional.isEmpty()) {
            var settings = new ContactSettings();
            settings.setIndustries(INITIAL_INDUSTRIES);
            settings.setSkills(INITIAL_SKILLS);
            settings.setProducts(INITIAL_PRODUCTS);
            settings.setTargetMarkets(INITIAL_TARGET_MARKETS);
            var savedSettings = contactSettingsRepository.saveAndFlush(settings);
            contactSettingsRepository.refresh(savedSettings);
            return savedSettings;
        }
        return optional.get();
    }

    private NoteSettings getOrCreateNoteSettings() {
        var predicate = predicates.noteSettings();
        var optional = noteSettingsRepository.findOne(predicate);
        if (optional.isEmpty()) {
            var settings = new NoteSettings();
            var savedSettings = noteSettingsRepository.saveAndFlush(settings);
            noteSettingsRepository.refresh(savedSettings);
            return savedSettings;
        }
        return optional.get();
    }

    private NotificationSettings getOrCreateNotificationSettings() {
        var predicate = predicates.notificationSettings();
        var optional = notificationSettingsRepository.findOne(predicate);
        if (optional.isEmpty()) {
            var settings = new NotificationSettings();
            var savedSettings = notificationSettingsRepository.saveAndFlush(settings);
            notificationSettingsRepository.refresh(savedSettings);
            return savedSettings;
        }
        return optional.get();
    }

    private void updateContactSettingFields(ContactSettings settings, ContactSettings updatedSettings) {
        settings.setLabels(updatedSettings.getLabels());
        settings.setIndustries(updatedSettings.getIndustries());
        settings.setSkills(updatedSettings.getSkills());
        settings.setProducts(updatedSettings.getProducts());
        settings.setTargetMarkets(updatedSettings.getTargetMarkets());
    }

    private void updateNoteSettingFields(NoteSettings settings, NoteSettings updatedSettings) {
        settings.setCategories(updatedSettings.getCategories());
    }

    private void updateNotificationSettingFields(NotificationSettings settings, NotificationSettings updatedSettings) {
        settings.setGlobal(updatedSettings.getGlobal());
        settings.setContact(updatedSettings.getContact());
        settings.setReferral(updatedSettings.getReferral());
        settings.setTask(updatedSettings.getTask());
    }

    private Supplier<EntityNotFoundException> accountSettingsNotFoundSupplier() {
        return () -> new EntityNotFoundException("GeneralSettings not found!");
    }
}
