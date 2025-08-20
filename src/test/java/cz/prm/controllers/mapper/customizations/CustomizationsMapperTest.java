package cz.prm.controllers.mapper.customizations;

import static cz.prm.utils.SettingsUtils.contactSettings;
import static cz.prm.utils.SettingsUtils.contactSettingsDto;
import static cz.prm.utils.SettingsUtils.noteSettings;
import static cz.prm.utils.SettingsUtils.noteSettingsDto;
import static cz.prm.utils.assertions.SettingsAssertions.assertSettings;

import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.Test;

class CustomizationsMapperTest {

    private CustomizationsMapper mapper = MapperUtils.getCustomizationsMapper();

    @Test
    void toContactSettingsDto() {
        var settings = contactSettings();
        var dto = mapper.toContactSettingsDto(settings);
        assertSettings(settings, dto);
    }

    @Test
    void toContactSettings() {
        var dto = contactSettingsDto();
        var settings = mapper.toContactSettings(dto);
        assertSettings(settings, dto);
    }

    @Test
    void toNoteSettingsDto() {
        var settings = noteSettings();
        var dto = mapper.toNoteSettingsDto(settings);
        assertSettings(settings, dto);
    }

    @Test
    void toNoteSettings() {
        var dto = noteSettingsDto();
        var settings = mapper.toNoteSettings(dto);
        assertSettings(settings, dto);
    }
}