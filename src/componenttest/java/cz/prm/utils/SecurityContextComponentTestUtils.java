package cz.prm.utils;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static org.springframework.security.oauth2.jwt.Jwt.withTokenValue;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class SecurityContextComponentTestUtils {

   public static void ensureUser1Context() {
      ensureUserContext(USER_1);
   }

   public static void ensureUser2Context() {
      ensureUserContext(USER_2);
   }

   public static void ensureUser3Context() {
      ensureUserContext(USER_3);
   }

   public static void clearContext() {
      SecurityContextHolder.setContext(null);
   }

   private static void ensureUserContext(ComponentTestUser user) {
      var jwt = jwt(user);
      var token = new JwtAuthenticationToken(jwt);
      var context = new SecurityContextImpl();
      context.setAuthentication(token);
      SecurityContextHolder.setContext(context);
   }

   private static Jwt jwt(ComponentTestUser user) {
      var builder = withTokenValue("s");
      builder.header("typ", "JWT");
      builder.claim("sub", user.getUsername());
      return builder.build();
   }
}
