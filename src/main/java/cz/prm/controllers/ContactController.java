package cz.prm.controllers;

import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.mappers.ContactMapper;
import cz.prm.services.contact.UserService;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("contacts")
@RestController
public class ContactController {

   private UserService userService;
   public ContactMapper mapper;

   public ContactController(UserService userService, ContactMapper mapper) {
      this.userService = userService;
      this.mapper = mapper;
   }

   @GetMapping
   public List<ContactDto> getContacts(Authentication authToken) {
      var contacts = userService.getContacts();
      return mapper.toContactsDto(contacts);
   }

}
