package cz.prm.controllers;

import cz.prm.controllers.dto.settings.SettingsDto;
import cz.prm.controllers.mappers.SettingsMapper;
import cz.prm.services.SettingsService;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/user")
    public SettingsDto getUserSettings() {
        var settings = settingsService.getUserSettings();
        return mapper.toSettingsDto(settings);
    }
}
