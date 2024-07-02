package cz.prm.custom;

import cz.prm.repositories.settings.GeneralSettingsRepository;
import org.springframework.context.annotation.Primary;

@Primary
public interface ComponentTestGeneralSettingsRepository extends GeneralSettingsRepository {

}
