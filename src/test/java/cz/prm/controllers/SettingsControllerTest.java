package cz.prm.controllers;

import static cz.prm.utils.SettingsUtils.generalSettings;
import static cz.prm.utils.SettingsUtils.userSettings;
import static cz.prm.utils.SettingsUtils.userSettingsDto;
import static cz.prm.utils.assertions.SettingsAssertions.assertSettings;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.prm.controllers.mappers.SettingsMapper;
import cz.prm.domain.settings.UserSettings;
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
    private ArgumentCaptor<UserSettings> userSettingsCapt;

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
    void getUserSettings() {
        var settings = userSettings();
        when(settingsService.getUserSettings()).thenReturn(settings);
        var result = controller.getUserSettings();
        assertSettings(settings, result);
    }

    @Test
    void updateUserSettings() {
        var dto = userSettingsDto();
        controller.updateUserSettings(dto);
        verify(settingsService).updateUserSettings(userSettingsCapt.capture());
        var settings = userSettingsCapt.getValue();
        assertSettings(settings, dto);
    }
}