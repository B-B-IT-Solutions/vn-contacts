package cz.prm.controllers.dto.user;

import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.EMAIL;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.FAMILY_NAME;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.GIVEN_NAME;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.NAME;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.PREFERRED_USERNAME;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;

@ExtendWith(MockitoExtension.class)
class UserDtoTest {

   @Mock
   private OidcUser oidcUser;
   @Mock
   private Jwt jwt;

   @Test
   void newInstanceOidcUserInfo() {
      var username = uuid();
      var email = uuid();
      var fullName = uuid();
      var firstName = uuid();
      var lastName = uuid();

      when(oidcUser.getClaimAsString(PREFERRED_USERNAME)).thenReturn(username);
      when(oidcUser.getClaimAsString(EMAIL)).thenReturn(email);
      when(oidcUser.getClaimAsString(NAME)).thenReturn(fullName);
      when(oidcUser.getClaimAsString(GIVEN_NAME)).thenReturn(firstName);
      when(oidcUser.getClaimAsString(FAMILY_NAME)).thenReturn(lastName);

      var dto = new UserDto(oidcUser);
      assertThat(dto.getUsername()).isEqualTo(username);
      assertThat(dto.getEmail()).isEqualTo(email);
      assertThat(dto.getFullName()).isEqualTo(fullName);
      assertThat(dto.getFirstName()).isEqualTo(firstName);
      assertThat(dto.getLastName()).isEqualTo(lastName);
   }

   @Test
   void newInstanceJwt() {
      var username = uuid();
      var email = uuid();
      var fullName = uuid();
      var firstName = uuid();
      var lastName = uuid();

      when(jwt.getClaimAsString(PREFERRED_USERNAME)).thenReturn(username);
      when(jwt.getClaimAsString(EMAIL)).thenReturn(email);
      when(jwt.getClaimAsString(NAME)).thenReturn(fullName);
      when(jwt.getClaimAsString(GIVEN_NAME)).thenReturn(firstName);
      when(jwt.getClaimAsString(FAMILY_NAME)).thenReturn(lastName);

      var dto = new UserDto(jwt);
      assertThat(dto.getUsername()).isEqualTo(username);
      assertThat(dto.getEmail()).isEqualTo(email);
      assertThat(dto.getFullName()).isEqualTo(fullName);
      assertThat(dto.getFirstName()).isEqualTo(firstName);
      assertThat(dto.getLastName()).isEqualTo(lastName);
   }
}