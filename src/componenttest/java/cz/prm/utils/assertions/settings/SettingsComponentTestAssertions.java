package cz.prm.utils.assertions.settings;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.settings.AccountSettingsDto;
import cz.prm.controllers.dto.settings.notifications.NotificationSettingsDto;
import cz.prm.controllers.dto.settings.notifications.dials.ContactNotificationsDto;
import cz.prm.controllers.dto.settings.notifications.dials.ReferralNotificationsDto;
import cz.prm.controllers.dto.settings.notifications.dials.TaskNotificationsDto;
import cz.prm.domain.settings.AccountSettings;
import cz.prm.domain.settings.notifications.NotificationSettings;

public class SettingsComponentTestAssertions {

    public static void assertSettings(AccountSettings settings, AccountSettingsDto dto) {
        assertThat(dto.getSettingsId()).isEqualTo(settings.getSettingsId());
        assertThat(dto.getAppLanguage()).isEqualTo(dto.getAppLanguage());
    }

    public static void assertSettings(NotificationSettings settings, NotificationSettingsDto dto) {
        assertThat(dto.getSettingsId()).isEqualTo(settings.getSettingsId());
        assertThat(dto.getLastEditDate()).isNotNull();
        assertThat(dto.getGlobal()).isNotNull();
        assertThat(dto.getContact()).isNotNull();
        assertThat(dto.getReferral()).isNotNull();
        assertThat(dto.getTask()).isNotNull();
    }

    public static void assertSettings(NotificationSettingsDto settings1, NotificationSettingsDto settings2) {
        assertThat(settings1.getSettingsId()).isEqualTo(settings2.getSettingsId());
        assertThat(settings1.getGlobal()).isEqualTo(settings2.getGlobal());
        assertNotificationsDto(settings1.getContact(), settings2.getContact());
        assertNotificationsDto(settings1.getReferral(), settings2.getReferral());
        assertNotificationsDto(settings1.getTask(), settings2.getTask());
    }

    public static void assertNotificationsDto(ContactNotificationsDto dto1, ContactNotificationsDto dto2) {
        assertThat(dto1.isStalenessReminder()).isEqualTo(dto2.isStalenessReminder());
    }

    public static void assertNotificationsDto(ReferralNotificationsDto dto1, ReferralNotificationsDto dto2) {
        assertThat(dto1.isFollowupReminder()).isEqualTo(dto2.isFollowupReminder());
        assertThat(dto1.isExpiryReminder()).isEqualTo(dto2.isExpiryReminder());
        assertThat(dto1.isStalenessReminder()).isEqualTo(dto2.isStalenessReminder());
    }

    public static void assertNotificationsDto(TaskNotificationsDto dto1, TaskNotificationsDto dto2) {
        assertThat(dto1.isReminders()).isEqualTo(dto2.isReminders());
        assertThat(dto1.isAboutToExpire()).isEqualTo(dto2.isAboutToExpire());
    }
}
