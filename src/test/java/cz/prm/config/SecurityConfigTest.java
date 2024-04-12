package cz.prm.config;

import static com.google.common.collect.Maps.newHashMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.config.http.SessionCreationPolicy.ALWAYS;

import cz.prm.config.keycloak.KeycloakGrantedAuthoritiesMapper;
import cz.prm.config.keycloak.KeycloakLogoutHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizedUrl;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

   @Mock
   private KeycloakGrantedAuthoritiesMapper authoritiesMapper;
   @Mock
   private KeycloakLogoutHandler logoutHandler;
   @Mock
   private ObjectPostProcessor objectPostProcessor;
   @Mock
   private AuthenticationManager authenticationManager;
   @Mock
   private AuthenticationManagerBuilder authenticationManagerBuilder;
   @Mock
   private ApplicationContext applicationContext;
   @Mock
   private AuthorizeHttpRequestsConfigurer authorizeHttpRequestsConfigurer;
   @Mock
   private SessionManagementConfigurer sessionManagementConfigurer;
   @Mock
   private CsrfConfigurer csrfConfigurer;
   @Mock
   private AuthorizationManagerRequestMatcherRegistry requestMatcherRegistry;
   @Mock
   private AuthorizedUrl authorizedUrl;
   @Mock
   private Customizer dummyCustomize;

   private Map<Class<?>, Object> sharedObjects;
   private HttpSecurity httpSecurity;
   private SecurityConfig securityConfig;

   @BeforeEach
   void setUp() throws Exception {
      sharedObjects = newHashMap();
      sharedObjects.put(ApplicationContext.class, applicationContext);
      httpSecurity = new HttpSecurity(objectPostProcessor, authenticationManagerBuilder, sharedObjects);
      httpSecurity.with(authorizeHttpRequestsConfigurer, dummyCustomize);
      httpSecurity.with(sessionManagementConfigurer, dummyCustomize);
      httpSecurity.with(csrfConfigurer, dummyCustomize);

      securityConfig = new SecurityConfig(authoritiesMapper, logoutHandler);
   }

   @Test
   void filterChain() throws Exception {
      when(objectPostProcessor.postProcess(any())).thenAnswer((arg) -> arg.getArgument(0));
      when(authenticationManagerBuilder.build()).thenReturn(authenticationManager);
      when(applicationContext.getBeanNamesForType(any(Class.class))).thenReturn(new String[0]);
      when(authorizeHttpRequestsConfigurer.getRegistry()).thenReturn(requestMatcherRegistry);
      when(requestMatcherRegistry.requestMatchers(any(String[].class))).thenReturn(authorizedUrl);
      when(requestMatcherRegistry.anyRequest()).thenReturn(authorizedUrl);
      when(authorizedUrl.permitAll()).thenReturn(requestMatcherRegistry);
      when(authorizedUrl.hasAnyRole(any(String[].class))).thenReturn(requestMatcherRegistry);

      securityConfig.filterChain(httpSecurity);
      verify(sessionManagementConfigurer).sessionCreationPolicy(ALWAYS);
      verify(csrfConfigurer).disable();
   }

   @Test
   void keycloakGrantedAuthoritiesMapper() {
      var result = securityConfig.keycloakGrantedAuthoritiesMapper();
      assertThat(result).isNotNull().isSameAs(authoritiesMapper);
   }

   @Test
   void sessionRegistry() {
      var result = securityConfig.sessionRegistry();
      assertThat(result).isNotNull().isInstanceOf(SessionRegistryImpl.class);
   }

   @Test
   void sessionAuthenticationStrategy() {
      var result = securityConfig.sessionAuthenticationStrategy();
      assertThat(result).isNotNull().isInstanceOf(RegisterSessionAuthenticationStrategy.class);
   }

   @Test
   void httpSessionEventPublisher() {
      var result = securityConfig.httpSessionEventPublisher();
      assertThat(result).isNotNull().isInstanceOf(HttpSessionEventPublisher.class);
   }

}