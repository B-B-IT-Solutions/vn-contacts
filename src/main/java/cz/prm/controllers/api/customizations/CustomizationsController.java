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

@RequestMapping("customizations")
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
    public ContactCustomizationsDto getContactCustomizations() {
        var settings = customizationsService.getContactCustomizations();
        return mapper.toContactCustomizationsDto(settings);
    }

    @PutMapping("/contact")
    public void updateContactCustomizations(@RequestBody ContactCustomizationsDto dto) {
        var settings = mapper.toContactCustomizations(dto);
        customizationsService.updateContactCustomizations(settings);
    }

    @GetMapping("/note")
    public NoteCustomizationsDto getNoteCustomizations() {
        var settings = customizationsService.getNoteCustomizations();
        return mapper.toNoteCustomizationsDto(settings);
    }

    @PutMapping("/note")
    public void updateNoteCustomizations(@RequestBody NoteCustomizationsDto dto) {
        var settings = mapper.toNoteCustomizations(dto);
        customizationsService.updateNoteCustomizations(settings);
    }
}
