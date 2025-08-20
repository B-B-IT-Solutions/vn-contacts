package cz.prm.controllers;

import cz.prm.controllers.dto.settings.contact.ContactSettingsDto;
import cz.prm.controllers.dto.settings.note.NoteSettingsDto;
import cz.prm.controllers.mappers.customizations.CustomizationsMapper;
import cz.prm.services.customizations.CustomizationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("settings")
@RestController
public class CustomizationsController {

    private CustomizationsService customizationsService;
    private CustomizationsMapper mapper;

    @Autowired
    public CustomizationsController(CustomizationsService customizationsService, CustomizationsMapper mapper) {
        this.customizationsService = customizationsService;
        this.mapper = mapper;
    }

    @GetMapping("/contact")
    public ContactSettingsDto getContactSettings() {
        var settings = customizationsService.getContactSettings();
        return mapper.toContactSettingsDto(settings);
    }

    @PutMapping("/contact")
    public void updateContactSettings(@RequestBody ContactSettingsDto dto) {
        var settings = mapper.toContactSettings(dto);
        customizationsService.updateContactSettings(settings);
    }

    @GetMapping("/note")
    public NoteSettingsDto getNoteSettings() {
        var settings = customizationsService.getNoteSettings();
        return mapper.toNoteSettingsDto(settings);
    }

    @PutMapping("/note")
    public void updateNoteSettings(@RequestBody NoteSettingsDto dto) {
        var settings = mapper.toNoteSettings(dto);
        customizationsService.updateNoteSettings(settings);
    }
}
