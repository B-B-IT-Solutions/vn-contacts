package cz.prm.utils.assertions.settings;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.settings.AccountSettingsDto;
import cz.prm.controllers.dto.settings.notifications.NotificationSettingsDto;
import cz.prm.controllers.dto.settings.notifications.dials.ContactNotificationsDto;
import cz.prm.controllers.dto.settings.notifications.dials.ReferralNotificationsDto;
import cz.prm.controllers.dto.settings.notifications.dials.TaskNotificationsDto;
import cz.prm.domain.settings.AccountSettings;
import cz.prm.domain.settings.notifications.NotificationSettings;
import cz.prm.domain.settings.notifications.dials.ContactNotifications;
import cz.prm.domain.settings.notifications.dials.ReferralNotifications;
import cz.prm.domain.settings.notifications.dials.TaskNotifications;

public class SettingsAssertions {

    public static void assertSettings(AccountSettings settings1, AccountSettings settings2) {
        assertThat(settings1.getSettingsId()).isEqualTo(settings2.getSettingsId());
        assertThat(settings1.getAppLanguage()).isEqualTo(settings2.getAppLanguage());
    }

    public static void assertSettings(AccountSettings settings, AccountSettingsDto dto) {
        assertThat(settings.getSettingsId()).isEqualTo(dto.getSettingsId());
        assertThat(settings.getAppLanguage()).isEqualTo(dto.getAppLanguage());
    }

    public static void assertSettings(NotificationSettings settings1, NotificationSettings settings2) {
        assertThat(settings1.getSettingsId()).isEqualTo(settings2.getSettingsId());
        assertThat(settings1.getGlobal()).isEqualTo(settings2.getGlobal());
        assertThat(settings1.getLastEditDate()).isEqualTo(settings2.getLastEditDate());
        assertThat(settings1.getOwner()).isEqualTo(settings2.getOwner());
        assertNotifications(settings1.getContact(), settings2.getContact());
        assertNotifications(settings1.getReferral(), settings2.getReferral());
        assertNotifications(settings1.getTask(), settings2.getTask());
    }

    public static void assertSettings(NotificationSettings settings, NotificationSettingsDto dto) {
        assertThat(settings.getSettingsId()).isEqualTo(dto.getSettingsId());
        assertThat(settings.getGlobal()).isEqualTo(dto.getGlobal());
        assertThat(settings.getLastEditDate()).isEqualTo(dto.getLastEditDate());
        assertNotifications(settings.getContact(), dto.getContact());
        assertNotifications(settings.getReferral(), dto.getReferral());
        assertNotifications(settings.getTask(), dto.getTask());
    }

    public static void assertNotifications(ContactNotifications settings1, ContactNotifications settings2) {
        assertThat(settings1.isStalenessReminder()).isEqualTo(settings2.isStalenessReminder());
    }

    public static void assertNotifications(ContactNotifications settings, ContactNotificationsDto dto) {
        assertThat(settings.isStalenessReminder()).isEqualTo(dto.isStalenessReminder());
    }

    public static void assertNotifications(ReferralNotifications settings1, ReferralNotifications settings2) {
        assertThat(settings1.isFollowupReminder()).isEqualTo(settings2.isFollowupReminder());
        assertThat(settings1.isExpiryReminder()).isEqualTo(settings2.isExpiryReminder());
        assertThat(settings1.isStalenessReminder()).isEqualTo(settings2.isStalenessReminder());
    }

    public static void assertNotifications(ReferralNotifications settings, ReferralNotificationsDto dto) {
        assertThat(settings.isFollowupReminder()).isEqualTo(dto.isFollowupReminder());
        assertThat(settings.isExpiryReminder()).isEqualTo(dto.isExpiryReminder());
        assertThat(settings.isStalenessReminder()).isEqualTo(dto.isStalenessReminder());
    }

    public static void assertNotifications(TaskNotifications settings1, TaskNotifications settings2) {
        assertThat(settings1.isReminders()).isEqualTo(settings2.isReminders());
        assertThat(settings1.isAboutToExpire()).isEqualTo(settings2.isAboutToExpire());
    }

    public static void assertNotifications(TaskNotifications settings, TaskNotificationsDto dto) {
        assertThat(settings.isReminders()).isEqualTo(dto.isReminders());
        assertThat(settings.isAboutToExpire()).isEqualTo(dto.isAboutToExpire());
    }
}
