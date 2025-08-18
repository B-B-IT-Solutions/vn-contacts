package cz.prm.controllers;

import cz.prm.controllers.dto.settings.AccountSettingsDto;
import cz.prm.controllers.dto.settings.contact.ContactSettingsDto;
import cz.prm.controllers.dto.settings.note.NoteSettingsDto;
import cz.prm.controllers.dto.settings.notifications.NotificationSettingsDto;
import cz.prm.controllers.mappers.SettingsMapper;
import cz.prm.services.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public SettingsController(SettingsService settingsService, SettingsMapper mapper) {
        this.settingsService = settingsService;
        this.mapper = mapper;
    }

    @GetMapping("/account")
    public AccountSettingsDto getAccountSettings() {
        var settings = settingsService.getAccountSettings();
        return mapper.toAccountSettingsDto(settings);
    }

    @GetMapping("/contact")
    public ContactSettingsDto getContactSettings() {
        var settings = settingsService.getContactSettings();
        return mapper.toContactSettingsDto(settings);
    }

    @PutMapping("/contact")
    public void updateContactSettings(@RequestBody ContactSettingsDto dto) {
        var settings = mapper.toContactSettings(dto);
        settingsService.updateContactSettings(settings);
    }

    @GetMapping("/note")
    public NoteSettingsDto getNoteSettings() {
        var settings = settingsService.getNoteSettings();
        return mapper.toNoteSettingsDto(settings);
    }

    @PutMapping("/note")
    public void updateNoteSettings(@RequestBody NoteSettingsDto dto) {
        var settings = mapper.toNoteSettings(dto);
        settingsService.updateNoteSettings(settings);
    }

    @GetMapping("/notifications")
    public NotificationSettingsDto getNotificationSettings() {
        var settings = settingsService.getNotificationSettings();
        return mapper.toNotificationSettingsDto(settings);
    }

    @PutMapping("/notifications")
    public NotificationSettingsDto updateNotificationSettings(@RequestBody NotificationSettingsDto dto) {
        var settings = mapper.toNotificationSettings(dto);
        var response = settingsService.updateNotificationSettings(settings);
        return mapper.toNotificationSettingsDto(response);
    }
}
