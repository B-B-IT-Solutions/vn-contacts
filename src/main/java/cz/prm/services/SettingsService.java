package cz.prm.services;

import cz.prm.domain.settings.AccountSettings;
import cz.prm.domain.settings.ContactSettings;
import cz.prm.repositories.settings.GeneralSettingsRepository;
import cz.prm.repositories.settings.SettingsPredicates;
import cz.prm.repositories.settings.UserSettingsRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SettingsService {

    private GeneralSettingsRepository generalSettingsRepository;
    private UserSettingsRepository userSettingsRepository;
    private SettingsPredicates predicates;

    public SettingsService(GeneralSettingsRepository generalSettingsRepository, UserSettingsRepository userSettingsRepository,
        SettingsPredicates predicates) {
        this.generalSettingsRepository = generalSettingsRepository;
        this.userSettingsRepository = userSettingsRepository;
        this.predicates = predicates;
    }

    public AccountSettings getAccountSettings() {
        var predicate = predicates.generalSettings();
        var optional = generalSettingsRepository.findOne(predicate);
        return optional.orElseThrow(generalSettingsNotFoundSupplier());
    }

    public ContactSettings getContactSettings() {
        return getOrCreateContactSettings();
    }

    public void updateContactSettings(ContactSettings updatedSettings) {
        var settings = getOrCreateContactSettings();
        updateUserSettingFields(settings, updatedSettings);
        userSettingsRepository.save(settings);
    }

    private ContactSettings getOrCreateContactSettings() {
        var predicate = predicates.userSettings();
        var optional = userSettingsRepository.findOne(predicate);
        if (optional.isEmpty()) {
            var settings = new ContactSettings();
            var savedSettings = userSettingsRepository.saveAndFlush(settings);
            userSettingsRepository.refresh(savedSettings);
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
