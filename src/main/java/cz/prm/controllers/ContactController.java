package cz.prm.controllers;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.contact.AboutDto;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.contact.query.ContactsQueryDto;
import cz.prm.controllers.mappers.ContactMapper;
import cz.prm.services.AboutService;
import cz.prm.services.ContactService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("contacts")
@RestController
public class ContactController {

    private ContactService contactService;
    private AboutService aboutService;
    private ContactMapper mapper;

    public ContactController(ContactService contactService, AboutService aboutService, ContactMapper mapper) {
        this.contactService = contactService;
        this.aboutService = aboutService;
        this.mapper = mapper;
    }

    @GetMapping
    public PageDto<ContactDto> getContacts(ContactsQueryDto queryDto) {
        var query = mapper.toNullSafeContactsQuery(queryDto);
        var contacts = contactService.getContacts(query);
        return mapper.toPageDto(contacts);
    }

    @GetMapping("/{contactId}")
    public ContactDto getContact(@PathVariable("contactId") Long contactId) {
        var contact = contactService.getContact(contactId);
        return mapper.toContactDto(contact);
    }

    @PostMapping
    public void createContact(@RequestBody ContactDto dto) {
        var contact = mapper.toContact(dto);
        contactService.createContact(contact);
    }

    @PutMapping("/{contactId}")
    public void updateContact(@PathVariable("contactId") Long contactId, @RequestBody ContactDto dto) {
        var contact = mapper.toContact(dto);
        contactService.updateContact(contactId, contact);
    }

    @DeleteMapping("/{contactId}")
    public void deleteContact(@PathVariable("contactId") Long contactId) {
        contactService.deleteContact(contactId);
    }

    @GetMapping("/{contactId}/about")
    public AboutDto getAbout(@PathVariable("contactId") Long contactId) {
        var about = aboutService.getAbout(contactId);
        return mapper.toAboutDto(about);
    }

    @PutMapping("/{contactId}/about")
    public void updateAbout(@PathVariable("contactId") Long contactId, @RequestBody AboutDto dto) {
        var about = mapper.toAbout(dto);
        aboutService.updateAbout(contactId, about);
    }
}
