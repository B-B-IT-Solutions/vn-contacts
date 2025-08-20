package cz.prm.services;

import static cz.prm.domain.settings.notifications.dials.GlobalNotifications.ALL;
import static cz.prm.utils.MockitoUtils.returnParamAnswer;
import static cz.prm.utils.SettingsUtils.accountSettings;
import static cz.prm.utils.SettingsUtils.notificationSettings;
import static cz.prm.utils.assertions.SettingsAssertions.assertNotifications;
import static cz.prm.utils.assertions.SettingsAssertions.assertSettings;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.querydsl.core.BooleanBuilder;
import cz.prm.domain.settings.notifications.NotificationSettings;
import cz.prm.repositories.settings.AccountSettingsRepository;
import cz.prm.repositories.settings.NotificationSettingsRepository;
import cz.prm.repositories.settings.SettingsPredicates;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SettingsServiceTest {

    @Mock
    private AccountSettingsRepository accountSettingsRepository;
    @Mock
    private NotificationSettingsRepository notificationSettingsRepository;
    @Mock
    private SettingsPredicates predicates;
    @Captor
    private ArgumentCaptor<NotificationSettings> notificationSettingsCapt;

    private SettingsService settingsService;

    @BeforeEach
    void setUp() {
        settingsService = new SettingsService(accountSettingsRepository, notificationSettingsRepository, predicates);
    }

    @Test
    void getAccountSettings() {
        var predicate = new BooleanBuilder();
        when(predicates.accountSettings()).thenReturn(predicate);
        when(accountSettingsRepository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> settingsService.getAccountSettings());
    }

    @Test
    void getAccountSettings_SettingsNotFound() {
        var settings = accountSettings();
        var predicate = new BooleanBuilder();
        when(predicates.accountSettings()).thenReturn(predicate);
        when(accountSettingsRepository.findOne(predicate)).thenReturn(of(settings));

        var result = settingsService.getAccountSettings();
        assertSettings(result, settings);
    }

    @Test
    void getNotificationSettings() {
        var settings = notificationSettings();
        var predicate = new BooleanBuilder();
        when(predicates.notificationSettings()).thenReturn(predicate);
        when(notificationSettingsRepository.findOne(predicate)).thenReturn(of(settings));

        var result = settingsService.getNotificationSettings();
        assertSettings(result, settings);
    }

    @Test
    void getNotificationSettings_SettingsNotFound() {
        var predicate = new BooleanBuilder();
        when(predicates.notificationSettings()).thenReturn(predicate);
        when(notificationSettingsRepository.findOne(predicate)).thenReturn(empty());
        when(notificationSettingsRepository.saveAndFlush(any(NotificationSettings.class))).thenAnswer((invocation -> invocation.getArgument(0)));

        var result = settingsService.getNotificationSettings();
        assertInitialNotificationSetting(result);
        verify(notificationSettingsRepository).refresh(result);
    }

    @Test
    void updateNotificationSettings() {
        var settingsInDb = notificationSettings();
        var updatedSettings = notificationSettings();
        var predicate = new BooleanBuilder();
        when(predicates.notificationSettings()).thenReturn(predicate);
        when(notificationSettingsRepository.findOne(predicate)).thenReturn(of(settingsInDb));
        when(notificationSettingsRepository.save(any(NotificationSettings.class))).thenAnswer(returnParamAnswer(0));

        var response = settingsService.updateNotificationSettings(updatedSettings);
        verify(notificationSettingsRepository).save(notificationSettingsCapt.capture());
        var savedSettings = notificationSettingsCapt.getValue();
        assertNotificationSettingFieldsUpdated(settingsInDb, updatedSettings, savedSettings);
        assertNotificationSettingFieldsUpdated(settingsInDb, updatedSettings, response);
    }

    private static void assertInitialNotificationSetting(NotificationSettings settings) {
        assertThat(settings).isNotNull();
        assertThat(settings.getGlobal()).isEqualTo(ALL);

        var contactNotifications = settings.getContact();
        assertThat(contactNotifications.isStalenessReminder()).isFalse();

        var referralNotifications = settings.getReferral();
        assertThat(referralNotifications.isFollowupReminder()).isFalse();
        assertThat(referralNotifications.isExpiryReminder()).isFalse();
        assertThat(referralNotifications.isStalenessReminder()).isFalse();

        var taskNotifications = settings.getTask();
        assertThat(taskNotifications.isReminders()).isFalse();
        assertThat(taskNotifications.isAboutToExpire()).isFalse();
    }

    private static void assertNotificationSettingFieldsUpdated(NotificationSettings settingsInDb, NotificationSettings updatedSettings,
        NotificationSettings savedSettings) {
        assertThat(settingsInDb.getSettingsId()).isEqualTo(savedSettings.getSettingsId());
        assertThat(settingsInDb.getOwner()).isEqualTo(savedSettings.getOwner());
        assertThat(savedSettings.getGlobal()).isEqualTo(updatedSettings.getGlobal());
        assertNotifications(savedSettings.getContact(), updatedSettings.getContact());
        assertNotifications(savedSettings.getReferral(), updatedSettings.getReferral());
        assertNotifications(savedSettings.getTask(), updatedSettings.getTask());
    }
}