package cz.prm.services;

import cz.prm.domain.settings.Settings;
import cz.prm.repositories.settings.SettingsPredicates;
import cz.prm.repositories.settings.SettingsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SettingsService {

    private SettingsRepository repository;
    private SettingsPredicates predicates;

    public SettingsService(SettingsRepository repository, SettingsPredicates predicates) {
        this.repository = repository;
        this.predicates = predicates;
    }

    public Settings getSettings() {
        return getOrCreateSettings();
    }

    private Settings getOrCreateSettings() {
        var predicate = predicates.settings();
        var optional = repository.findOne(predicate);
        if (optional.isEmpty()) {
            var settings = new Settings();
            return repository.saveAndFlush(settings);
        }
        return optional.get();
    }
}
