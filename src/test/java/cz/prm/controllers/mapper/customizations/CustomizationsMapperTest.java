package cz.prm.controllers.mapper.customizations;

import static cz.prm.utils.assertions.customizations.CustomizationsAssertions.assertSettings;
import static cz.prm.utils.data.customizations.CustomizationsUtils.contactCustomizations;
import static cz.prm.utils.data.customizations.CustomizationsUtils.contactCustomizationsDto;
import static cz.prm.utils.data.customizations.CustomizationsUtils.noteCustomizations;
import static cz.prm.utils.data.customizations.CustomizationsUtils.noteCustomizationsDto;

import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.Test;

class CustomizationsMapperTest {

    private CustomizationsMapper mapper = MapperUtils.getCustomizationsMapper();

    @Test
    void toContactCustomizationsDto() {
        var customizations = contactCustomizations();
        var dto = mapper.toContactCustomizationsDto(customizations);
        assertSettings(customizations, dto);
    }

    @Test
    void toContactCustomizations() {
        var dto = contactCustomizationsDto();
        var settings = mapper.toContactCustomizations(dto);
        assertSettings(settings, dto);
    }

    @Test
    void toNoteCustomizationsDto() {
        var customizations = noteCustomizations();
        var dto = mapper.toNoteCustomizationsDto(customizations);
        assertSettings(customizations, dto);
    }

    @Test
    void toNoteCustomizations() {
        var dto = noteCustomizationsDto();
        var settings = mapper.toNoteCustomizations(dto);
        assertSettings(settings, dto);
    }
}