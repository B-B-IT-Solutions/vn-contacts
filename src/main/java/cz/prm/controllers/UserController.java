package cz.prm.controllers;

import cz.prm.controllers.dto.UserDto;
import cz.prm.controllers.mapppers.UserMapper;
import cz.prm.services.UserService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("users")
@RestController
public class UserController {

   private UserService userService;
   public UserMapper mapper;

   public UserController(UserService userService, UserMapper mapper) {
      this.userService = userService;
      this.mapper = mapper;
   }

   @GetMapping
   public List<UserDto> getUsers() {
      var users = userService.getUsers();
      return mapper.toUsersDto(users);
   }
}
