package cz.prm.custom;

import cz.prm.repositories.settings.UserSettingsRepository;
import org.springframework.context.annotation.Primary;

@Primary
public interface ComponentTestUserSettingsRepository extends UserSettingsRepository {

}
