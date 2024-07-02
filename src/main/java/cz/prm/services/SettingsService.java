package cz.prm.services;

import cz.prm.domain.settings.UserSettings;
import cz.prm.repositories.settings.SettingsPredicates;
import cz.prm.repositories.settings.UserSettingsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SettingsService {

    private UserSettingsRepository repository;
    private SettingsPredicates predicates;

    public SettingsService(UserSettingsRepository repository, SettingsPredicates predicates) {
        this.repository = repository;
        this.predicates = predicates;
    }

    public UserSettings getSettings() {
        return getOrCreateSettings();
    }

    private UserSettings getOrCreateSettings() {
        var predicate = predicates.settings();
        var optional = repository.findOne(predicate);
        if (optional.isEmpty()) {
            var settings = new UserSettings();
            var savedSettings = repository.saveAndFlush(settings);
            repository.refresh(savedSettings);
            return savedSettings;
        }
        return optional.get();
    }
}
