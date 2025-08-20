package cz.prm.extensions.settings;

import cz.prm.repositories.settings.AccountSettingsRepository;
import org.springframework.context.annotation.Primary;

@Primary
public interface ComponentTestAccountSettingsRepository extends AccountSettingsRepository {

}
