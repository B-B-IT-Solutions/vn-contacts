package cz.prm.business.settings;

import static cz.prm.domain.settings.notifications.dials.GlobalNotifications.DISABLED;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.settings.AccountSettingsDto;
import cz.prm.controllers.dto.settings.notifications.NotificationSettingsDto;
import cz.prm.utils.assertions.SettingsComponentTestAssertions;
import org.junit.jupiter.api.Test;

public class SettingsComponentTest extends SettingsComponentTestBase {

    @Test
    void getAccountSettings() {
        var dto = user1GetAccountSettings();
        assertSettings(dto);

        dto = user2GetAccountSettings();
        assertSettings(dto);

        dto = user3GetAccountSettings();
        assertSettings(dto);
    }

    @Test
    void getNotificationSettings() {
        var dto1 = user1GetNotificationSettings();
        var dto2 = user1GetNotificationSettings();
        assertThat(dto1).isEqualTo(dto2);
        assertSettings(dto2);

        dto1 = user2GetNotificationSettings();
        dto2 = user2GetNotificationSettings();
        assertThat(dto1).isEqualTo(dto2);
        assertSettings(dto2);

        dto1 = user3GetNotificationSettings();
        dto2 = user3GetNotificationSettings();
        assertThat(dto1).isEqualTo(dto2);
        assertSettings(dto2);
    }

    @Test
    void updateNotificationSettings() {
        var dto1 = user1GetNotificationSettings();
        dto1.setGlobal(DISABLED);
        user1UpdateNotificationSettings(dto1);
        var dto2 = user1GetNotificationSettings();
        assertSettings(dto1, dto2);

        dto1 = user2GetNotificationSettings();
        dto1.setGlobal(DISABLED);
        user2UpdateNotificationSettings(dto1);
        dto2 = user2GetNotificationSettings();
        assertSettings(dto1, dto2);

        dto1 = user3GetNotificationSettings();
        dto1.setGlobal(DISABLED);
        user3UpdateNotificationSettings(dto1);
        dto2 = user3GetNotificationSettings();
        assertSettings(dto1, dto2);
    }

    private void assertSettings(AccountSettingsDto dto) {
        var settings = getAccountSettingsFromDb(dto);
        SettingsComponentTestAssertions.assertSettings(settings, dto);
    }

    private void assertSettings(NotificationSettingsDto dto) {
        var settings = getNotificationSettingsFromDb(dto);
        SettingsComponentTestAssertions.assertSettings(settings, dto);
    }

    private void assertSettings(NotificationSettingsDto dto1, NotificationSettingsDto dto2) {
        SettingsComponentTestAssertions.assertSettings(dto1, dto2);
    }
}
