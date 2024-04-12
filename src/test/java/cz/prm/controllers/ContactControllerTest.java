package cz.prm.controllers;

import static cz.prm.utils.ContactUtils.contacts;
import static cz.prm.utils.assertions.UserAssertions.assertContactsDto;
import static org.mockito.Mockito.when;

import cz.prm.controllers.mappers.ContactMapper;
import cz.prm.services.contact.UserService;
import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContactControllerTest {

   @Mock
   private UserService userService;
   private ContactMapper mapper = MapperUtils.getUserMapper();

   private ContactController controller;

   @BeforeEach
   void setUp() {
      controller = new ContactController(userService, mapper);
   }

   @Test
   void getContacts() {
      var users = contacts();
      when(userService.getContacts()).thenReturn(users);
      var result = controller.getContacts();
      assertContactsDto(users, result);
   }

}