package cz.prm.services.customizations;

import static cz.prm.domain.customizations.contact.InitContactCustomizations.INITIAL_INDUSTRIES;
import static cz.prm.domain.customizations.contact.InitContactCustomizations.INITIAL_PRODUCTS;
import static cz.prm.domain.customizations.contact.InitContactCustomizations.INITIAL_SKILLS;
import static cz.prm.domain.customizations.contact.InitContactCustomizations.INITIAL_TARGET_MARKETS;

import cz.prm.domain.customizations.contact.ContactCustomizations;
import cz.prm.domain.customizations.note.NoteCustomizations;
import cz.prm.repositories.customizations.ContactCustomizationsRepository;
import cz.prm.repositories.customizations.CustomizationsPredicates;
import cz.prm.repositories.customizations.NoteCustomizaitonsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomizationsService {

    private ContactCustomizationsRepository contactCustomizationsRepository;
    private NoteCustomizaitonsRepository noteCustomizaitonsRepository;
    private CustomizationsPredicates predicates;

    public CustomizationsService(ContactCustomizationsRepository contactCustomizationsRepository,
        NoteCustomizaitonsRepository noteCustomizaitonsRepository, CustomizationsPredicates predicates) {
        this.contactCustomizationsRepository = contactCustomizationsRepository;
        this.noteCustomizaitonsRepository = noteCustomizaitonsRepository;
        this.predicates = predicates;
    }

    public ContactCustomizations getContactCustomizations() {
        return getOrCreateContactSettings();
    }

    public NoteCustomizations getNoteCustomizations() {
        return getOrCreateNoteSettings();
    }

    public void updateContactCustomizations(ContactCustomizations updatedSettings) {
        var settings = getOrCreateContactSettings();
        updateContactSettingFields(settings, updatedSettings);
        contactCustomizationsRepository.save(settings);
    }

    public void updateNoteCustomizations(NoteCustomizations updatedSettings) {
        var settings = getOrCreateNoteSettings();
        updateNoteSettingFields(settings, updatedSettings);
        noteCustomizaitonsRepository.save(settings);
    }

    private ContactCustomizations getOrCreateContactSettings() {
        var predicate = predicates.contactCustomizations();
        var optional = contactCustomizationsRepository.findOne(predicate);
        if (optional.isEmpty()) {
            var settings = new ContactCustomizations();
            settings.setIndustries(INITIAL_INDUSTRIES);
            settings.setSkills(INITIAL_SKILLS);
            settings.setProducts(INITIAL_PRODUCTS);
            settings.setTargetMarkets(INITIAL_TARGET_MARKETS);
            var savedSettings = contactCustomizationsRepository.saveAndFlush(settings);
            contactCustomizationsRepository.refresh(savedSettings);
            return savedSettings;
        }
        return optional.get();
    }

    private NoteCustomizations getOrCreateNoteSettings() {
        var predicate = predicates.noteCustomizations();
        var optional = noteCustomizaitonsRepository.findOne(predicate);
        if (optional.isEmpty()) {
            var settings = new NoteCustomizations();
            var savedSettings = noteCustomizaitonsRepository.saveAndFlush(settings);
            noteCustomizaitonsRepository.refresh(savedSettings);
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


