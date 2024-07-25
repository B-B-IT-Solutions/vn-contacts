package cz.prm.controllers;

import static cz.prm.utils.SettingsUtils.contactSettings;
import static cz.prm.utils.SettingsUtils.contactSettingsDto;
import static cz.prm.utils.SettingsUtils.generalSettings;
import static cz.prm.utils.assertions.SettingsAssertions.assertSettings;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.prm.controllers.mappers.SettingsMapper;
import cz.prm.domain.settings.ContactSettings;
import cz.prm.services.SettingsService;
import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SettingsControllerTest {

    @Mock
    private SettingsService settingsService;
    @Captor
    private ArgumentCaptor<ContactSettings> userSettingsCapt;

    private SettingsMapper mapper = MapperUtils.getSettingsMapper();
    private SettingsController controller;

    @BeforeEach
    void setUp() {
        controller = new SettingsController(settingsService, mapper);
    }

    @Test
    void getGeneralSettings() {
        var settings = generalSettings();
        when(settingsService.getGeneralSettings()).thenReturn(settings);
        var result = controller.getGeneralSettings();
        assertSettings(settings, result);
    }

    @Test
    void getContactSettings() {
        var settings = contactSettings();
        when(settingsService.getContactSettings()).thenReturn(settings);
        var result = controller.getContactSettings();
        assertSettings(settings, result);
    }

    @Test
    void updateContactSettings() {
        var dto = contactSettingsDto();
        controller.updateContactSettings(dto);
        verify(settingsService).updateContactSettings(userSettingsCapt.capture());
        var settings = userSettingsCapt.getValue();
        assertSettings(settings, dto);
    }
}