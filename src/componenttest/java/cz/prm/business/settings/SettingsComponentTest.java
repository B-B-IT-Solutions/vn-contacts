package cz.prm.business.settings;

import static cz.prm.utils.SettingsComponentTestUtils.industriesDto;
import static cz.prm.utils.SettingsComponentTestUtils.labelsDto;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.settings.AccountSettingsDto;
import cz.prm.controllers.dto.settings.ContactSettingsDto;
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
    void getContactSettings() {
        var dto1 = user1GetContactSettings();
        var dto2 = user1GetContactSettings();
        assertThat(dto1).isEqualTo(dto2);
        assertSettings(dto2);

        dto1 = user2GetContactSettings();
        dto2 = user2GetContactSettings();
        assertThat(dto1).isEqualTo(dto2);
        assertSettings(dto2);

        dto1 = user3GetContactSettings();
        dto2 = user3GetContactSettings();
        assertThat(dto1).isEqualTo(dto2);
        assertSettings(dto2);
    }

    @Test
    void updateContactSettings() {
        var dto1 = user1GetContactSettings();
        dto1.setLabels(labelsDto());
        dto1.setIndustries(industriesDto());
        user1UpdateContactSettings(dto1);
        var dto2 = user1GetContactSettings();
        assertSettings(dto1, dto2);

        dto1 = user2GetContactSettings();
        dto1.setLabels(labelsDto());
        dto1.setIndustries(industriesDto());
        user2UpdateContactSettings(dto1);
        dto2 = user2GetContactSettings();
        assertSettings(dto1, dto2);

        dto1 = user3GetContactSettings();
        dto1.setLabels(labelsDto());
        dto1.setIndustries(industriesDto());
        user3UpdateContactSettings(dto1);
        dto2 = user3GetContactSettings();
        assertSettings(dto1, dto2);
    }

    private void assertSettings(AccountSettingsDto dto) {
        var settings = getGeneralSettingsFromDb(dto);
        SettingsComponentTestAssertions.assertSettings(settings, dto);
    }

    private void assertSettings(ContactSettingsDto dto) {
        var settings = getUserSettingsFromDb(dto);
        SettingsComponentTestAssertions.assertSettings(settings, dto);
    }

    private void assertSettings(ContactSettingsDto dto1, ContactSettingsDto dto2) {
        SettingsComponentTestAssertions.assertSettings(dto1, dto2);
    }
}
