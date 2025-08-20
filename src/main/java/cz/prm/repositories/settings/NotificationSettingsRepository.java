package cz.prm.repositories.settings;

import cz.prm.domain.settings.notifications.NotificationSettings;
import cz.prm.repositories.extensions.executors.PrmQuerydslPredicateExecutor;
import cz.prm.repositories.extensions.repositories.RefreshAwareRepository;

public interface NotificationSettingsRepository extends RefreshAwareRepository<NotificationSettings, Long>,
    PrmQuerydslPredicateExecutor<NotificationSettings> {

}