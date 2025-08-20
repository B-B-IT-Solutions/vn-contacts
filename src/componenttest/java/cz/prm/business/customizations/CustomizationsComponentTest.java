package cz.prm.business.customizations;

import static cz.prm.utils.data.customizations.CustomizationsComponentTestUtils.categoriesDto;
import static cz.prm.utils.data.customizations.CustomizationsComponentTestUtils.industriesDto;
import static cz.prm.utils.data.customizations.CustomizationsComponentTestUtils.labelsDto;
import static cz.prm.utils.data.customizations.CustomizationsComponentTestUtils.productsDto;
import static cz.prm.utils.data.customizations.CustomizationsComponentTestUtils.skillsDto;
import static cz.prm.utils.data.customizations.CustomizationsComponentTestUtils.targetMarketsDto;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.customizations.contact.ContactCustomizationsDto;
import cz.prm.controllers.dto.customizations.note.NoteCustomizationsDto;
import cz.prm.utils.assertions.customizations.CustomizationsComponentTestAssertions;
import org.junit.jupiter.api.Test;

public class CustomizationsComponentTest extends CustomizationsComponentTestBase {

    @Test
    void getContactCustomizations() {
        var dto1 = user1GetContactCustomizations();
        var dto2 = user1GetContactCustomizations();
        assertThat(dto1).isEqualTo(dto2);
        assertSettings(dto2);

        dto1 = user2GetContactCustomizations();
        dto2 = user2GetContactCustomizations();
        assertThat(dto1).isEqualTo(dto2);
        assertSettings(dto2);

        dto1 = user3GetContactCustomizations();
        dto2 = user3GetContactCustomizations();
        assertThat(dto1).isEqualTo(dto2);
        assertSettings(dto2);
    }

    @Test
    void getNoteCustomizations() {
        var dto1 = user1GetNoteCustomizations();
        var dto2 = user1GetNoteCustomizations();
        assertThat(dto1).isEqualTo(dto2);
        assertSettings(dto2);

        dto1 = user2GetNoteCustomizations();
        dto2 = user2GetNoteCustomizations();
        assertThat(dto1).isEqualTo(dto2);
        assertSettings(dto2);

        dto1 = user3GetNoteCustomizations();
        dto2 = user3GetNoteCustomizations();
        assertThat(dto1).isEqualTo(dto2);
        assertSettings(dto2);
    }

    @Test
    void updateContactCustomizations() {
        var dto1 = user1GetContactCustomizations();
        dto1.setLabels(labelsDto());
        dto1.setIndustries(industriesDto());
        dto1.setSkills(skillsDto());
        dto1.setProducts(productsDto());
        dto1.setTargetMarkets(targetMarketsDto());
        user1UpdateContactCustomizations(dto1);
        var dto2 = user1GetContactCustomizations();
        assertSettings(dto1, dto2);

        dto1 = user2GetContactCustomizations();
        dto1.setLabels(labelsDto());
        dto1.setIndustries(industriesDto());
        dto1.setSkills(skillsDto());
        dto1.setProducts(productsDto());
        dto1.setTargetMarkets(targetMarketsDto());
        user2UpdateContactCustomizations(dto1);
        dto2 = user2GetContactCustomizations();
        assertSettings(dto1, dto2);

        dto1 = user3GetContactCustomizations();
        dto1.setLabels(labelsDto());
        dto1.setIndustries(industriesDto());
        dto1.setSkills(skillsDto());
        dto1.setProducts(productsDto());
        dto1.setTargetMarkets(targetMarketsDto());
        user3UpdateContactCustomizations(dto1);
        dto2 = user3GetContactCustomizations();
        assertSettings(dto1, dto2);
    }

    @Test
    void updateNoteCustomizations() {
        var dto1 = user1GetNoteCustomizations();
        dto1.setCategories(categoriesDto());
        user1UpdateNoteCustomizations(dto1);
        var dto2 = user1GetNoteCustomizations();
        assertSettings(dto1, dto2);

        dto1 = user2GetNoteCustomizations();
        dto1.setCategories(categoriesDto());
        user2UpdateNoteCustomizations(dto1);
        dto2 = user2GetNoteCustomizations();
        assertSettings(dto1, dto2);

        dto1 = user3GetNoteCustomizations();
        dto1.setCategories(categoriesDto());
        user3UpdateNoteCustomizations(dto1);
        dto2 = user3GetNoteCustomizations();
        assertSettings(dto1, dto2);
    }

    private void assertSettings(ContactCustomizationsDto dto) {
        var settings = getContactSettingsFromDb(dto);
        CustomizationsComponentTestAssertions.assertSettings(settings, dto);
    }

    private void assertSettings(ContactCustomizationsDto dto1, ContactCustomizationsDto dto2) {
        CustomizationsComponentTestAssertions.assertSettings(dto1, dto2);
    }

    private void assertSettings(NoteCustomizationsDto dto) {
        var settings = getNoteSettingsFromDb(dto);
        CustomizationsComponentTestAssertions.assertSettings(settings, dto);
    }

    private void assertSettings(NoteCustomizationsDto dto1, NoteCustomizationsDto dto2) {
        CustomizationsComponentTestAssertions.assertSettings(dto1, dto2);
    }
}
