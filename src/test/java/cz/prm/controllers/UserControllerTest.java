package cz.prm.controllers;

import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.EMAIL;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.FAMILY_NAME;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.GIVEN_NAME;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.NAME;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.PREFERRED_USERNAME;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

   @Mock
   private JwtAuthenticationToken jwtAuthenticationToken;
   @Mock
   private Jwt jwt;

   private UserController controller;

   @BeforeEach
   void setUp() {
      controller = new UserController();
   }

   @Test
   void getCurrentUserJwtAuthenticationToken() {
      var username = uuid();
      var email = uuid();
      var fullName = uuid();
      var firstName = uuid();
      var lastName = uuid();

      when(jwtAuthenticationToken.getPrincipal()).thenReturn(jwt);
      when(jwt.getClaimAsString(PREFERRED_USERNAME)).thenReturn(username);
      when(jwt.getClaimAsString(EMAIL)).thenReturn(email);
      when(jwt.getClaimAsString(NAME)).thenReturn(fullName);
      when(jwt.getClaimAsString(GIVEN_NAME)).thenReturn(firstName);
      when(jwt.getClaimAsString(FAMILY_NAME)).thenReturn(lastName);

      var result = controller.getCurrentUser(jwtAuthenticationToken);
      assertThat(result.getUsername()).isEqualTo(username);
      assertThat(result.getEmail()).isEqualTo(email);
      assertThat(result.getFullName()).isEqualTo(fullName);
      assertThat(result.getFirstName()).isEqualTo(firstName);
      assertThat(result.getLastName()).isEqualTo(lastName);
   }
}