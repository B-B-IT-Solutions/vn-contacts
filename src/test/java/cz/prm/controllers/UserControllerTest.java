package cz.prm.controllers;

import static cz.prm.utils.UserUtils.users;
import static cz.prm.utils.assertions.UserAssertions.assertUsersDto;
import static org.mockito.Mockito.when;

import cz.prm.controllers.mapppers.UserMapper;
import cz.prm.services.UserService;
import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

   @Mock
   private UserService userService;
   private UserMapper mapper = MapperUtils.getUserMapper();

   private UserController controller;

   @BeforeEach
   void setUp() {
      controller = new UserController(userService, mapper);
   }

   @Test
   void getUsers() {
      var users = users();
      when(userService.getUsers()).thenReturn(users);
      var result = controller.getUsers();
      assertUsersDto(users, result);
   }
}