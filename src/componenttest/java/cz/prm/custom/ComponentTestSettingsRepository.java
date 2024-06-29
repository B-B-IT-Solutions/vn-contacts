package cz.prm.custom;

import cz.prm.domain.settings.Settings;
import cz.prm.repositories.settings.SettingsRepository;
import org.springframework.context.annotation.Primary;

@Primary
public interface ComponentTestSettingsRepository extends SettingsRepository {

    Settings getById(Long settingsId);
}
