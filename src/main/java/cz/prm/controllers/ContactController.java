package cz.prm.controllers;

import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.mappers.ContactMapper;
import cz.prm.services.contact.ContactService;
import java.util.List;
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
   public ContactMapper mapper;

   public ContactController(ContactService contactService, ContactMapper mapper) {
      this.contactService = contactService;
      this.mapper = mapper;
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

   @GetMapping
   public List<ContactDto> getContacts() {
      var contacts = contactService.getContacts();
      return mapper.toContactsDto(contacts);
   }

   @GetMapping("/{contactId}")
   public ContactDto getContact(@PathVariable("contactId") Long contactId) {
      var contact = contactService.getContact(contactId);
      return mapper.toContactDto(contact);
   }

}
