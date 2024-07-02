package cz.prm.services;

import cz.prm.domain.settings.GeneralSettings;
import cz.prm.domain.settings.UserSettings;
import cz.prm.repositories.settings.GeneralSettingsRepository;
import cz.prm.repositories.settings.SettingsPredicates;
import cz.prm.repositories.settings.UserSettingsRepository;
import jakarta.transaction.Transactional;
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

    public GeneralSettings getGeneralSettings() {
        return generalSettingsRepository.getReferenceById(1L);
    }

    public UserSettings getUserSettings() {
        return getOrCreateUserSettings();
    }

    private UserSettings getOrCreateUserSettings() {
        var predicate = predicates.userSettings();
        var optional = userSettingsRepository.findOne(predicate);
        if (optional.isEmpty()) {
            var settings = new UserSettings();
            var savedSettings = userSettingsRepository.saveAndFlush(settings);
            userSettingsRepository.refresh(savedSettings);
            return savedSettings;
        }
        return optional.get();
    }
}
