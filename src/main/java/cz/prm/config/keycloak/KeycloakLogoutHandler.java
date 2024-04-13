package cz.prm.config.keycloak;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class KeycloakLogoutHandler implements LogoutHandler {

   private RestTemplate restTemplate;

   public KeycloakLogoutHandler(RestTemplate restTemplate) {
      this.restTemplate = restTemplate;
   }

   @Override
   public void logout(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
      logoutFromKeycloak((OidcUser) auth.getPrincipal());
   }

   private void logoutFromKeycloak(OidcUser user) {
      var logoutUrl = logoutUrl(user);
      var response = restTemplate.getForEntity(logoutUrl, String.class);
      if (response.getStatusCode().is2xxSuccessful()) {
         log.info("Successfully logged out from Keycloak");
      } else {
         log.error("Could not propagate logout to Keycloak");
      }
   }

   private String logoutUrl(OidcUser user) {
      var endSessionEndpoint = user.getIssuer() + "/protocol/openid-connect/logout";
      var builder = fromUriString(endSessionEndpoint).queryParam("id_token_hint", user.getIdToken().getTokenValue());
      return builder.toUriString();
   }
}