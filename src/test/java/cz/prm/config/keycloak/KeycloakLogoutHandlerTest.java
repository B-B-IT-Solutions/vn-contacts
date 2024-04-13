package cz.prm.config.keycloak;

import static cz.prm.utils.TestUtils.uuid;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatusCode.valueOf;

import java.net.URL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class KeycloakLogoutHandlerTest {

   private static final String ISSUER_URL = "http://locahost:8095";

   @Mock
   private Authentication authToken;
   @Mock
   private OidcUser oidcUser;
   @Mock
   private OidcIdToken oidcIdToken;
   @Mock
   private ResponseEntity<String> response;
   @Mock
   private RestTemplate restTemplate;

   private KeycloakLogoutHandler logoutHandler;

   @BeforeEach
   void setUp() {
      logoutHandler = new KeycloakLogoutHandler(restTemplate);
   }

   @Test
   void logoutSuccessful() throws Exception {
      whenLogoutUrlCallIsMocked();
      when(response.getStatusCode()).thenReturn(valueOf(200));
      logoutHandler.logout(null, null, authToken);
   }

   @Test
   void logoutFailed() throws Exception {
      whenLogoutUrlCallIsMocked();
      when(response.getStatusCode()).thenReturn(valueOf(400));
      logoutHandler.logout(null, null, authToken);
   }

   private void whenLogoutUrlCallIsMocked() throws Exception {
      var issuer = new URL(ISSUER_URL);
      var idTokenValue = uuid();
      var logoutUrl = logoutUrl(idTokenValue);
      when(authToken.getPrincipal()).thenReturn(oidcUser);
      when(oidcUser.getIssuer()).thenReturn(issuer);
      when(oidcUser.getIdToken()).thenReturn(oidcIdToken);
      when(oidcIdToken.getTokenValue()).thenReturn(idTokenValue);
      when(restTemplate.getForEntity(logoutUrl, String.class)).thenReturn(response);
   }

   private String logoutUrl(String idTokenValue) {
      return String.format("%s/protocol/openid-connect/logout?id_token_hint=%s", ISSUER_URL, idTokenValue);
   }
}