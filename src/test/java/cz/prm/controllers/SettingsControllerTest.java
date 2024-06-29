package cz.prm.controllers;

import static cz.prm.utils.SettingsUtils.settings;
import static cz.prm.utils.assertions.SettingsAssertions.assertSettings;
import static org.mockito.Mockito.when;

import cz.prm.controllers.mappers.SettingsMapper;
import cz.prm.services.SettingsService;
import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SettingsControllerTest {

    @Mock
    private SettingsService settingsService;

    private SettingsMapper mapper = MapperUtils.getSettingsMapper();
    private SettingsController controller;

    @BeforeEach
    void setUp() {
        controller = new SettingsController(settingsService, mapper);
    }

    @Test
    void getSettings() {
        var settings = settings();
        when(settingsService.getSettings()).thenReturn(settings);
        var result = controller.getSettings();
        assertSettings(settings, result);
    }
}