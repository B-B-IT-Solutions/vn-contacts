package cz.prm.controllers;

import cz.prm.controllers.dto.settings.GeneralSettingsDto;
import cz.prm.controllers.dto.settings.UserSettingsDto;
import cz.prm.controllers.mappers.SettingsMapper;
import cz.prm.services.SettingsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("settings")
@RestController
public class SettingsController {

    private SettingsService settingsService;
    private SettingsMapper mapper;

    public SettingsController(SettingsService settingsService, SettingsMapper mapper) {
        this.settingsService = settingsService;
        this.mapper = mapper;
    }

    @GetMapping("/general")
    public GeneralSettingsDto getGeneralSettings() {
        var settings = settingsService.getGeneralSettings();
        return mapper.toGeneralSettingsDto(settings);
    }

    @GetMapping("/user")
    public UserSettingsDto getUserSettings() {
        var settings = settingsService.getUserSettings();
        return mapper.toUserSettingsDto(settings);
    }

    @PutMapping("/user")
    public void updateUserSettings(@RequestBody UserSettingsDto dto) {
        var settings = mapper.toUserSettings(dto);
        settingsService.updateUserSettings(settings);
    }
}
