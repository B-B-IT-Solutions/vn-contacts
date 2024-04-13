package cz.prm.controllers;

import cz.prm.controllers.dto.user.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("users")
@RestController
public class UserController {

   @GetMapping("current-user")
   public UserDto getCurrentUser(Authentication authToken) {
      var oidcUser = (OidcUser) authToken.getPrincipal();
      return new UserDto(oidcUser.getUserInfo());
   }
}
