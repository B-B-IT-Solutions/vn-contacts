package cz.prm.custom;

import cz.prm.repositories.customizations.ContactSettingsRepository;
import org.springframework.context.annotation.Primary;

@Primary
public interface ComponentTestContactSettingsRepository extends ContactSettingsRepository {

}
