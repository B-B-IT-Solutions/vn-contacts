package cz.prm.services;

import cz.prm.domain.settings.AccountSettings;
import cz.prm.domain.settings.contact.ContactSettings;
import cz.prm.repositories.settings.AccountSettingsRepository;
import cz.prm.repositories.settings.ContactSettingsRepository;
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
    private SettingsPredicates predicates;

    public SettingsService(AccountSettingsRepository accountSettingsRepository, ContactSettingsRepository contactSettingsRepository,
        SettingsPredicates predicates) {
        this.accountSettingsRepository = accountSettingsRepository;
        this.contactSettingsRepository = contactSettingsRepository;
        this.predicates = predicates;
    }

    public AccountSettings getAccountSettings() {
        var predicate = predicates.accountSettings();
        var optional = accountSettingsRepository.findOne(predicate);
        return optional.orElseThrow(generalSettingsNotFoundSupplier());
    }

    public ContactSettings getContactSettings() {
        return getOrCreateContactSettings();
    }

    public void updateContactSettings(ContactSettings updatedSettings) {
        var settings = getOrCreateContactSettings();
        updateUserSettingFields(settings, updatedSettings);
        contactSettingsRepository.save(settings);
    }

    private ContactSettings getOrCreateContactSettings() {
        var predicate = predicates.userSettings();
        var optional = contactSettingsRepository.findOne(predicate);
        if (optional.isEmpty()) {
            var settings = new ContactSettings();
            var savedSettings = contactSettingsRepository.saveAndFlush(settings);
            contactSettingsRepository.refresh(savedSettings);
            return savedSettings;
        }
        return optional.get();
    }

    private void updateUserSettingFields(ContactSettings settings, ContactSettings updatedSettings) {
        settings.setLabels(updatedSettings.getLabels());
        settings.setIndustries(updatedSettings.getIndustries());
    }

    private Supplier<EntityNotFoundException> generalSettingsNotFoundSupplier() {
        return () -> new EntityNotFoundException("GeneralSettings not found!");
    }
}
