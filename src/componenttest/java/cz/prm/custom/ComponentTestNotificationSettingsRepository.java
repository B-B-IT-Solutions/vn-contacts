package cz.prm.custom;

import cz.prm.repositories.settings.NotificationSettingsRepository;
import org.springframework.context.annotation.Primary;

@Primary
public interface ComponentTestNotificationSettingsRepository extends NotificationSettingsRepository {

}
