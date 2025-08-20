package cz.prm.services;

import static cz.prm.domain.settings.notifications.dials.GlobalNotifications.ALL;

import cz.prm.domain.settings.AccountSettings;
import cz.prm.domain.settings.notifications.NotificationSettings;
import cz.prm.domain.settings.notifications.dials.ContactNotifications;
import cz.prm.domain.settings.notifications.dials.ReferralNotifications;
import cz.prm.domain.settings.notifications.dials.TaskNotifications;
import cz.prm.repositories.settings.AccountSettingsRepository;
import cz.prm.repositories.settings.NotificationSettingsRepository;
import cz.prm.repositories.settings.SettingsPredicates;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SettingsService {

    private AccountSettingsRepository accountSettingsRepository;
    private NotificationSettingsRepository notificationSettingsRepository;
    private SettingsPredicates predicates;

    public SettingsService(AccountSettingsRepository accountSettingsRepository, NotificationSettingsRepository notificationSettingsRepository,
        SettingsPredicates predicates) {
        this.accountSettingsRepository = accountSettingsRepository;
        this.notificationSettingsRepository = notificationSettingsRepository;
        this.predicates = predicates;
    }

    public AccountSettings getAccountSettings() {
        var predicate = predicates.accountSettings();
        var optional = accountSettingsRepository.findOne(predicate);
        return optional.orElseThrow(accountSettingsNotFoundSupplier());
    }

    public NotificationSettings getNotificationSettings() {
        return getOrCreateNotificationSettings();
    }

    public NotificationSettings updateNotificationSettings(NotificationSettings updatedSettings) {
        var settings = getOrCreateNotificationSettings();
        updateNotificationSettingFields(settings, updatedSettings);
        return notificationSettingsRepository.save(settings);
    }

    private NotificationSettings getOrCreateNotificationSettings() {
        var predicate = predicates.notificationSettings();
        var optional = notificationSettingsRepository.findOne(predicate);
        if (optional.isEmpty()) {
            var settings = new NotificationSettings();
            settings.setGlobal(ALL);
            settings.setContact(new ContactNotifications());
            settings.setReferral(new ReferralNotifications());
            settings.setTask(new TaskNotifications());
            var savedSettings = notificationSettingsRepository.saveAndFlush(settings);
            notificationSettingsRepository.refresh(savedSettings);
            return savedSettings;
        }
        return optional.get();
    }

    private void updateNotificationSettingFields(NotificationSettings settings, NotificationSettings updatedSettings) {
        settings.setGlobal(updatedSettings.getGlobal());
        settings.setContact(updatedSettings.getContact());
        settings.setReferral(updatedSettings.getReferral());
        settings.setTask(updatedSettings.getTask());
    }

    private Supplier<EntityNotFoundException> accountSettingsNotFoundSupplier() {
        return () -> new EntityNotFoundException("GeneralSettings not found!");
    }
}
