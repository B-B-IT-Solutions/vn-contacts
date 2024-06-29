package cz.prm.controllers;

import cz.prm.controllers.dto.settings.SettingsDto;
import cz.prm.controllers.mappers.SettingsMapper;
import cz.prm.services.SettingsService;
import org.springframework.web.bind.annotation.GetMapping;

public class SettingsController {

    private SettingsService settingsService;
    private SettingsMapper mapper;

    public SettingsController(SettingsService settingsService, SettingsMapper mapper) {
        this.settingsService = settingsService;
        this.mapper = mapper;
    }

    @GetMapping("/settings")
    public SettingsDto getSettings() {
        var settings = settingsService.getSettings();
        return mapper.toSettingsDto(settings);
    }
}
