package cz.prm.controllers;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.contact.AboutDto;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.contact.DecoratedContactDto;
import cz.prm.controllers.dto.contact.query.ContactsQueryDto;
import cz.prm.controllers.mappers.ContactMapper;
import cz.prm.services.contact.ContactClearingHouse;
import org.springframework.beans.factory.annotation.Autowired;
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

    private ContactClearingHouse clearingHouse;
    private ContactMapper mapper;

    @Autowired
    public ContactController(ContactClearingHouse clearingHouse, ContactMapper mapper) {
        this.clearingHouse = clearingHouse;
        this.mapper = mapper;
    }

    @GetMapping("/{contactId}")
    public DecoratedContactDto getDecoratedContact(@PathVariable("contactId") Long contactId) {
        var dc = clearingHouse.getDecoratedContact(contactId);
        return mapper.toDecoratedContactDto(dc);
    }

    @PostMapping
    public DecoratedContactDto createDecoratedContact(@RequestBody DecoratedContactDto dto) {
        var contactEdit = mapper.toDecoratedContact(dto);
        var response = clearingHouse.createDecoratedContact(contactEdit);
        return mapper.toDecoratedContactDto(response);
    }

    @PutMapping("/{contactId}")
    public DecoratedContactDto updateDecoratedContact(@PathVariable("contactId") Long contactId, @RequestBody DecoratedContactDto dto) {
        var contactEdit = mapper.toDecoratedContact(dto);
        var response = clearingHouse.updateDecoratedContact(contactId, contactEdit);
        return mapper.toDecoratedContactDto(response);
    }

    @DeleteMapping("/{contactId}")
    public void deleteDecoratedContact(@PathVariable("contactId") Long contactId) {
        clearingHouse.deleteDecoratedContact(contactId);
    }

    @GetMapping("/contact")
    public PageDto<ContactDto> getContacts(ContactsQueryDto queryDto) {
        var query = mapper.toNullSafeContactsQuery(queryDto);
        var contacts = clearingHouse.getContacts(query);
        return mapper.toPageDto(contacts);
    }

    @GetMapping("/{contactId}/contact")
    public ContactDto getContact(@PathVariable("contactId") Long contactId) {
        var contact = clearingHouse.getContact(contactId);
        return mapper.toContactDto(contact);
    }

    @GetMapping("/{contactId}/about")
    public AboutDto getAbout(@PathVariable("contactId") Long contactId) {
        var about = clearingHouse.getAbout(contactId);
        return mapper.toAboutDto(about);
    }

    @PutMapping("/{contactId}/about")
    public void updateAbout(@PathVariable("contactId") Long contactId, @RequestBody AboutDto dto) {
        var about = mapper.toAbout(dto);
        clearingHouse.updateAbout(contactId, about);
    }
}
