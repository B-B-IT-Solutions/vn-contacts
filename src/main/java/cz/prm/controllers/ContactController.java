package cz.prm.controllers;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.contact.AboutDto;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.contact.ContactEditDto;
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

    @GetMapping
    public PageDto<ContactDto> getContacts(ContactsQueryDto queryDto) {
        var query = mapper.toNullSafeContactsQuery(queryDto);
        var contacts = clearingHouse.getContacts(query);
        return mapper.toPageDto(contacts);
    }

    @GetMapping("/{contactId}")
    public ContactDto getContact(@PathVariable("contactId") Long contactId) {
        var contact = clearingHouse.getContact(contactId);
        return mapper.toContactDto(contact);
    }

    @PostMapping
    public ContactEditDto createContact(@RequestBody ContactEditDto dto) {
        var ce = mapper.toContactEdit(dto);
        var response = clearingHouse.createContact(ce);
        return mapper.toContactEditDto(response);
    }

    @PutMapping("/{contactId}")
    public void updateContact(@PathVariable("contactId") Long contactId, @RequestBody ContactDto dto) {
        var contact = mapper.toContact(dto);
        clearingHouse.updateContact(contactId, contact);
    }

    @DeleteMapping("/{contactId}")
    public void deleteContact(@PathVariable("contactId") Long contactId) {
        clearingHouse.deleteContact(contactId);
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
