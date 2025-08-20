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
    void getContactCustomizations() {
        var settings = contactSettings();
        when(customizationsService.getContactCustomizations()).thenReturn(settings);
        var result = controller.getContactCustomizations();
        assertSettings(settings, result);
    }

    @Test
    void updateContactCustomizations() {
        var dto = contactSettingsDto();
        controller.updateContactCustomizations(dto);
        verify(customizationsService).updateContactCustomizations(contactSettingsCapt.capture());
        var settings = contactSettingsCapt.getValue();
        assertSettings(settings, dto);
    }

    @Test
    void getNoteCustomizations() {
        var settings = noteSettings();
        when(customizationsService.getNoteCustomizations()).thenReturn(settings);
        var result = controller.getNoteCustomizations();
        assertSettings(settings, result);
    }

    @Test
    void updateNoteCustomizations() {
        var dto = noteSettingsDto();
        controller.updateNoteCustomizations(dto);
        verify(customizationsService).updateNoteCustomizations(noteSettingsCapt.capture());
        var settings = noteSettingsCapt.getValue();
        assertSettings(settings, dto);
    }
}