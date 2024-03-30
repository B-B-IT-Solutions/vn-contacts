package cz.prm.controllers;

import cz.prm.domain.User;
import cz.prm.services.UserService;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

   private UserService userService;

   public UserController(UserService userService) {
      this.userService = userService;
   }

   public List<User> getUsers() {
      return userService.getUsers();
   }
}
