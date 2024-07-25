package cz.prm.custom;

import cz.prm.repositories.settings.ContactSettingsRepository;
import org.springframework.context.annotation.Primary;

@Primary
public interface ComponentTestContactSettingsRepository extends ContactSettingsRepository {

}
