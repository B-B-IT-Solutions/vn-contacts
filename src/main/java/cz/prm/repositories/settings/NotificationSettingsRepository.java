package cz.prm.repositories.settings;

import cz.prm.domain.settings.notifications.NotificationSettings;
import cz.prm.repositories.customisations.executors.PrmQuerydslPredicateExecutor;
import cz.prm.repositories.customisations.repositories.RefreshAwareRepository;

public interface NotificationSettingsRepository extends RefreshAwareRepository<NotificationSettings, Long>,
    PrmQuerydslPredicateExecutor<NotificationSettings> {

}