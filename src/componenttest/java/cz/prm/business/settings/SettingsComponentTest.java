package cz.prm.business.settings;

import static cz.prm.utils.SettingsComponentTestUtils.categoriesDto;
import static cz.prm.utils.SettingsComponentTestUtils.industriesDto;
import static cz.prm.utils.SettingsComponentTestUtils.labelsDto;
import static cz.prm.utils.SettingsComponentTestUtils.skillsDto;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.settings.AccountSettingsDto;
import cz.prm.controllers.dto.settings.contact.ContactSettingsDto;
import cz.prm.controllers.dto.settings.note.NoteSettingsDto;
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
    void getNoteSettings() {
        var dto1 = user1GetNoteSettings();
        var dto2 = user1GetNoteSettings();
        assertThat(dto1).isEqualTo(dto2);
        assertSettings(dto2);

        dto1 = user2GetNoteSettings();
        dto2 = user2GetNoteSettings();
        assertThat(dto1).isEqualTo(dto2);
        assertSettings(dto2);

        dto1 = user3GetNoteSettings();
        dto2 = user3GetNoteSettings();
        assertThat(dto1).isEqualTo(dto2);
        assertSettings(dto2);
    }

    @Test
    void updateContactSettings() {
        var dto1 = user1GetContactSettings();
        dto1.setLabels(labelsDto());
        dto1.setIndustries(industriesDto());
        dto1.setSkills(skillsDto());
        user1UpdateContactSettings(dto1);
        var dto2 = user1GetContactSettings();
        assertSettings(dto1, dto2);

        dto1 = user2GetContactSettings();
        dto1.setLabels(labelsDto());
        dto1.setIndustries(industriesDto());
        dto1.setSkills(skillsDto());
        user2UpdateContactSettings(dto1);
        dto2 = user2GetContactSettings();
        assertSettings(dto1, dto2);

        dto1 = user3GetContactSettings();
        dto1.setLabels(labelsDto());
        dto1.setIndustries(industriesDto());
        dto1.setSkills(skillsDto());
        user3UpdateContactSettings(dto1);
        dto2 = user3GetContactSettings();
        assertSettings(dto1, dto2);
    }

    @Test
    void updateNoteSettings() {
        var dto1 = user1GetNoteSettings();
        dto1.setCategories(categoriesDto());
        user1UpdateNoteSettings(dto1);
        var dto2 = user1GetNoteSettings();
        assertSettings(dto1, dto2);

        dto1 = user2GetNoteSettings();
        dto1.setCategories(categoriesDto());
        user2UpdateNoteSettings(dto1);
        dto2 = user2GetNoteSettings();
        assertSettings(dto1, dto2);

        dto1 = user3GetNoteSettings();
        dto1.setCategories(categoriesDto());
        user3UpdateNoteSettings(dto1);
        dto2 = user3GetNoteSettings();
        assertSettings(dto1, dto2);
    }

    private void assertSettings(AccountSettingsDto dto) {
        var settings = getGeneralSettingsFromDb(dto);
        SettingsComponentTestAssertions.assertSettings(settings, dto);
    }

    private void assertSettings(ContactSettingsDto dto) {
        var settings = getContactSettingsFromDb(dto);
        SettingsComponentTestAssertions.assertSettings(settings, dto);
    }

    private void assertSettings(ContactSettingsDto dto1, ContactSettingsDto dto2) {
        SettingsComponentTestAssertions.assertSettings(dto1, dto2);
    }

    private void assertSettings(NoteSettingsDto dto) {
        var settings = getNoteSettingsFromDb(dto);
        SettingsComponentTestAssertions.assertSettings(settings, dto);
    }

    private void assertSettings(NoteSettingsDto dto1, NoteSettingsDto dto2) {
        SettingsComponentTestAssertions.assertSettings(dto1, dto2);
    }
}
