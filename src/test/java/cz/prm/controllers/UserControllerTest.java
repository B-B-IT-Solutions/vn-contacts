package cz.prm.controllers;

import static cz.prm.utils.UserUtils.users;
import static cz.prm.utils.assertions.UserAssertions.assertUsers;
import static org.mockito.Mockito.when;

import cz.prm.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

   @Mock
   private UserService userService;

   private UserController controller;

   @BeforeEach
   void setUp() {
      controller = new UserController(userService);
   }

   @Test
   void getUsers() {
      var users = users();
      when(userService.getUsers()).thenReturn(users);
      var result = controller.getUsers();
      assertUsers(result, users);
   }
}