package cz.prm.controllers;

import cz.prm.controllers.dto.user.UserDto;
import cz.prm.controllers.mappers.UserMapper;
import cz.prm.services.user.UserService;
import java.util.List;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
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

   @GetMapping("current-user")
   public UserDto getCurrentUser(OAuth2AuthenticationToken authToken) {
      var oidcUser = (DefaultOidcUser) authToken.getPrincipal();
      return new UserDto(oidcUser.getUserInfo());
   }
}
