package cz.prm.controllers;

import static cz.prm.utils.MockitoUtils.returnParamAnswer;
import static cz.prm.utils.SettingsUtils.accountSettings;
import static cz.prm.utils.SettingsUtils.notificationSettings;
import static cz.prm.utils.SettingsUtils.notificationSettingsDto;
import static cz.prm.utils.assertions.SettingsAssertions.assertSettings;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.prm.controllers.mappers.SettingsMapper;
import cz.prm.domain.settings.notifications.NotificationSettings;
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
    private ArgumentCaptor<NotificationSettings> notificationSettingsCapt;

    private SettingsMapper mapper = MapperUtils.getSettingsMapper();
    private SettingsController controller;

    @BeforeEach
    void setUp() {
        controller = new SettingsController(settingsService, mapper);
    }

    @Test
    void getAccountSettings() {
        var settings = accountSettings();
        when(settingsService.getAccountSettings()).thenReturn(settings);
        var result = controller.getAccountSettings();
        assertSettings(settings, result);
    }

    @Test
    void getNotificationSettings() {
        var settings = notificationSettings();
        when(settingsService.getNotificationSettings()).thenReturn(settings);
        var result = controller.getNotificationSettings();
        assertSettings(settings, result);
    }

    @Test
    void updateNotificationSettings() {
        var dto = notificationSettingsDto();
        when(settingsService.updateNotificationSettings(any(NotificationSettings.class))).thenAnswer(returnParamAnswer(0));

        var responseDto = controller.updateNotificationSettings(dto);
        verify(settingsService).updateNotificationSettings(notificationSettingsCapt.capture());
        var settings = notificationSettingsCapt.getValue();
        assertSettings(settings, dto);
        assertSettings(settings, responseDto);
    }
}