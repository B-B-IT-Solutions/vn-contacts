package cz.prm.services.customizations;

import static cz.prm.domain.customizations.contact.InitContactCustomizations.INITIAL_INDUSTRIES;
import static cz.prm.domain.customizations.contact.InitContactCustomizations.INITIAL_PRODUCTS;
import static cz.prm.domain.customizations.contact.InitContactCustomizations.INITIAL_SKILLS;
import static cz.prm.domain.customizations.contact.InitContactCustomizations.INITIAL_TARGET_MARKETS;

import cz.prm.domain.customizations.contact.ContactCustomizations;
import cz.prm.domain.customizations.note.NoteCustomizations;
import cz.prm.repositories.customizations.ContactSettingsRepository;
import cz.prm.repositories.customizations.CustomizationsPredicates;
import cz.prm.repositories.customizations.NoteSettingsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomizationsService {

    private ContactSettingsRepository contactSettingsRepository;
    private NoteSettingsRepository noteSettingsRepository;
    private CustomizationsPredicates predicates;

    public CustomizationsService(ContactSettingsRepository contactSettingsRepository, NoteSettingsRepository noteSettingsRepository,
        CustomizationsPredicates predicates) {
        this.contactSettingsRepository = contactSettingsRepository;
        this.noteSettingsRepository = noteSettingsRepository;
        this.predicates = predicates;
    }

    public ContactCustomizations getContactSettings() {
        return getOrCreateContactSettings();
    }

    public NoteCustomizations getNoteSettings() {
        return getOrCreateNoteSettings();
    }

    public void updateContactSettings(ContactCustomizations updatedSettings) {
        var settings = getOrCreateContactSettings();
        updateContactSettingFields(settings, updatedSettings);
        contactSettingsRepository.save(settings);
    }

    public void updateNoteSettings(NoteCustomizations updatedSettings) {
        var settings = getOrCreateNoteSettings();
        updateNoteSettingFields(settings, updatedSettings);
        noteSettingsRepository.save(settings);
    }

    private ContactCustomizations getOrCreateContactSettings() {
        var predicate = predicates.contactSettings();
        var optional = contactSettingsRepository.findOne(predicate);
        if (optional.isEmpty()) {
            var settings = new ContactCustomizations();
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

    private NoteCustomizations getOrCreateNoteSettings() {
        var predicate = predicates.noteSettings();
        var optional = noteSettingsRepository.findOne(predicate);
        if (optional.isEmpty()) {
            var settings = new NoteCustomizations();
            var savedSettings = noteSettingsRepository.saveAndFlush(settings);
            noteSettingsRepository.refresh(savedSettings);
            return savedSettings;
        }
        return optional.get();
    }

    private void updateContactSettingFields(ContactCustomizations settings, ContactCustomizations updatedSettings) {
        settings.setLabels(updatedSettings.getLabels());
        settings.setIndustries(updatedSettings.getIndustries());
        settings.setSkills(updatedSettings.getSkills());
        settings.setProducts(updatedSettings.getProducts());
        settings.setTargetMarkets(updatedSettings.getTargetMarkets());
    }

    private void updateNoteSettingFields(NoteCustomizations settings, NoteCustomizations updatedSettings) {
        settings.setCategories(updatedSettings.getCategories());
    }
}


