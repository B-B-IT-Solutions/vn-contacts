package cz.prm.config;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.ALWAYS;

import cz.prm.config.keycloak.KeycloakGrantedAuthoritiesMapper;
import cz.prm.config.keycloak.KeycloakLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

   private KeycloakLogoutHandler keycloakLogoutHandler;

   @Autowired
   public SecurityConfig(KeycloakLogoutHandler keycloakLogoutHandler) {
      this.keycloakLogoutHandler = keycloakLogoutHandler;
   }

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
          .oauth2ResourceServer((oauth2) -> oauth2.jwt(withDefaults()))
          .oauth2Login(withDefaults())
          .logout(logout -> logout.addLogoutHandler(keycloakLogoutHandler).logoutSuccessUrl("/"))
          .csrf(csrf -> csrf.disable())
          .sessionManagement(session -> session.sessionCreationPolicy(ALWAYS));
      return http.build();
   }

   @Bean
   public SessionRegistry sessionRegistry() {
      return new SessionRegistryImpl();
   }

   @Bean
   protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
      return new RegisterSessionAuthenticationStrategy(sessionRegistry());
   }

   @Bean
   public HttpSessionEventPublisher httpSessionEventPublisher() {
      return new HttpSessionEventPublisher();
   }

}