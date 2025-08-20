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
    void toContactCustomizationsDto() {
        var settings = contactSettings();
        var dto = mapper.toContactCustomizationsDto(settings);
        assertSettings(settings, dto);
    }

    @Test
    void toContactCustomizations() {
        var dto = contactSettingsDto();
        var settings = mapper.toContactCustomizations(dto);
        assertSettings(settings, dto);
    }

    @Test
    void toNoteCustomizationsDto() {
        var settings = noteSettings();
        var dto = mapper.toNoteCustomizationsDto(settings);
        assertSettings(settings, dto);
    }

    @Test
    void toNoteCustomizations() {
        var dto = noteSettingsDto();
        var settings = mapper.toNoteCustomizations(dto);
        assertSettings(settings, dto);
    }
}