package cz.prm.controllers.api.customizations;

import static cz.prm.utils.assertions.customizations.CustomizationsAssertions.assertSettings;
import static cz.prm.utils.data.customizations.CustomizationsUtils.contactCustomizations;
import static cz.prm.utils.data.customizations.CustomizationsUtils.contactCustomizationsDto;
import static cz.prm.utils.data.customizations.CustomizationsUtils.noteCustomizations;
import static cz.prm.utils.data.customizations.CustomizationsUtils.noteCustomizationsDto;
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
    private ArgumentCaptor<ContactCustomizations> contactCustomizationsCapt;
    @Captor
    private ArgumentCaptor<NoteCustomizations> noteCustomizationsCapt;

    private CustomizationsMapper mapper = MapperUtils.getCustomizationsMapper();
    private CustomizationsController controller;

    @BeforeEach
    void setUp() {
        controller = new CustomizationsController(customizationsService, mapper);
    }

    @Test
    void getContactCustomizations() {
        var customizations = contactCustomizations();
        when(customizationsService.getContactCustomizations()).thenReturn(customizations);
        var result = controller.getContactCustomizations();
        assertSettings(customizations, result);
    }

    @Test
    void updateContactCustomizations() {
        var dto = contactCustomizationsDto();
        controller.updateContactCustomizations(dto);
        verify(customizationsService).updateContactCustomizations(contactCustomizationsCapt.capture());
        var settings = contactCustomizationsCapt.getValue();
        assertSettings(settings, dto);
    }

    @Test
    void getNoteCustomizations() {
        var customizations = noteCustomizations();
        when(customizationsService.getNoteCustomizations()).thenReturn(customizations);
        var result = controller.getNoteCustomizations();
        assertSettings(customizations, result);
    }

    @Test
    void updateNoteCustomizations() {
        var dto = noteCustomizationsDto();
        controller.updateNoteCustomizations(dto);
        verify(customizationsService).updateNoteCustomizations(noteCustomizationsCapt.capture());
        var settings = noteCustomizationsCapt.getValue();
        assertSettings(settings, dto);
    }
}