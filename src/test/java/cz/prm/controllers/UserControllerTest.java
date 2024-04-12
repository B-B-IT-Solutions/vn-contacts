package cz.prm.controllers;

import static cz.prm.utils.TestUtils.uuid;
import static cz.prm.utils.UserUtils.users;
import static cz.prm.utils.assertions.UserAssertions.assertUsersDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import cz.prm.controllers.mappers.UserMapper;
import cz.prm.services.user.UserService;
import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

   @Mock
   private OAuth2AuthenticationToken authToken;
   @Mock
   private DefaultOidcUser oidcUser;
   @Mock
   private OidcUserInfo oidcUserInfo;
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

   @Test
   void getCurrentUser() {
      var username = uuid();
      var email = uuid();
      var fullName = uuid();
      var firstName = uuid();
      var lastName = uuid();

      when(authToken.getPrincipal()).thenReturn(oidcUser);
      when(oidcUser.getUserInfo()).thenReturn(oidcUserInfo);
      when(oidcUserInfo.getEmail()).thenReturn(email);
      when(oidcUserInfo.getPreferredUsername()).thenReturn(username);
      when(oidcUserInfo.getFullName()).thenReturn(fullName);
      when(oidcUserInfo.getGivenName()).thenReturn(firstName);
      when(oidcUserInfo.getFamilyName()).thenReturn(lastName);

      var result = controller.getCurrentUser(authToken);
      assertThat(result.getUsername()).isEqualTo(username);
      assertThat(result.getEmail()).isEqualTo(email);
      assertThat(result.getFullName()).isEqualTo(fullName);
      assertThat(result.getFirstName()).isEqualTo(firstName);
      assertThat(result.getLastName()).isEqualTo(lastName);

   }
}