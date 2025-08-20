package cz.prm.controllers.api.customizations;

import cz.prm.controllers.dto.customizations.contact.ContactCustomizationsDto;
import cz.prm.controllers.dto.customizations.note.NoteCustomizationsDto;
import cz.prm.controllers.mapper.customizations.CustomizationsMapper;
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
    public ContactCustomizationsDto getContactSettings() {
        var settings = customizationsService.getContactSettings();
        return mapper.toContactSettingsDto(settings);
    }

    @PutMapping("/contact")
    public void updateContactSettings(@RequestBody ContactCustomizationsDto dto) {
        var settings = mapper.toContactSettings(dto);
        customizationsService.updateContactSettings(settings);
    }

    @GetMapping("/note")
    public NoteCustomizationsDto getNoteSettings() {
        var settings = customizationsService.getNoteSettings();
        return mapper.toNoteSettingsDto(settings);
    }

    @PutMapping("/note")
    public void updateNoteSettings(@RequestBody NoteCustomizationsDto dto) {
        var settings = mapper.toNoteSettings(dto);
        customizationsService.updateNoteSettings(settings);
    }
}
