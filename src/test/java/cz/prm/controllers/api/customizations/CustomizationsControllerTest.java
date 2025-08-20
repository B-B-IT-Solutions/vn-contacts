package cz.prm.controllers.api.customizations;

import static cz.prm.utils.SettingsUtils.contactSettings;
import static cz.prm.utils.SettingsUtils.contactSettingsDto;
import static cz.prm.utils.SettingsUtils.noteSettings;
import static cz.prm.utils.SettingsUtils.noteSettingsDto;
import static cz.prm.utils.assertions.SettingsAssertions.assertSettings;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.prm.controllers.mapper.customizations.CustomizationsMapper;
import cz.prm.domain.customizations.contact.ContactCustomizations;
import cz.prm.domain.customizations.note.NoteCustomizations;
import cz.prm.services.customizations.CustomizationsService;
import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomizationsControllerTest {

    @Mock
    private CustomizationsService customizationsService;
    @Captor
    private ArgumentCaptor<ContactCustomizations> contactSettingsCapt;
    @Captor
    private ArgumentCaptor<NoteCustomizations> noteSettingsCapt;

    private CustomizationsMapper mapper = MapperUtils.getCustomizationsMapper();
    private CustomizationsController controller;

    @BeforeEach
    void setUp() {
        controller = new CustomizationsController(customizationsService, mapper);
    }

    @Test
    void getContactSettings() {
        var settings = contactSettings();
        when(customizationsService.getContactSettings()).thenReturn(settings);
        var result = controller.getContactSettings();
        assertSettings(settings, result);
    }

    @Test
    void updateContactSettings() {
        var dto = contactSettingsDto();
        controller.updateContactSettings(dto);
        verify(customizationsService).updateContactSettings(contactSettingsCapt.capture());
        var settings = contactSettingsCapt.getValue();
        assertSettings(settings, dto);
    }

    @Test
    void getNoteSettings() {
        var settings = noteSettings();
        when(customizationsService.getNoteSettings()).thenReturn(settings);
        var result = controller.getNoteSettings();
        assertSettings(settings, result);
    }

    @Test
    void updateNoteSettings() {
        var dto = noteSettingsDto();
        controller.updateNoteSettings(dto);
        verify(customizationsService).updateNoteSettings(noteSettingsCapt.capture());
        var settings = noteSettingsCapt.getValue();
        assertSettings(settings, dto);
    }
}