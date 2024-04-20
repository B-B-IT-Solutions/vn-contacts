package cz.prm.security;

import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@ExtendWith(MockitoExtension.class)
class SecurityContextUtilsTest {

   @Mock
   private SecurityContext context;
   @Mock
   private JwtAuthenticationToken authentication;

   @Test
   void getUsername() {
      try (MockedStatic<SecurityContextHolder> contextHolder = Mockito.mockStatic(SecurityContextHolder.class)) {
         var username = uuid();
         when(authentication.getName()).thenReturn(username);
         when(context.getAuthentication()).thenReturn(authentication);
         contextHolder.when(() -> SecurityContextHolder.getContext()).thenReturn(context);
         var result = SecurityContextUtils.getUsername();
         assertThat(result).isEqualTo(username);
      }
   }
}