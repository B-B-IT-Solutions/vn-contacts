package cz.prm.config.keycloak;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.TestUtils.uuid;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

@ExtendWith(MockitoExtension.class)
class KeycloakGrantedAuthoritiesMapperTest {

   private static final String GROUPS = "groups";
   private static final String REALM_ACCESS_CLAIM = "realm_access";
   private static final String ROLES_CLAIM = "roles";

   private static String ROLE_ADMIN = "admin";
   private static String ROLE_PRM_USER = "prm-user";
   private static String ROLE_DELEGATE = "delegate";
   private static String ROLE_ANONYMOUS = "anonymous";

   @Mock
   private OidcUserInfo oidcUserInfo;
   @Mock
   private OidcIdToken oidcIdToken;

   private KeycloakGrantedAuthoritiesMapper authoritiesMapper;

   @BeforeEach
   void setUp() {
      authoritiesMapper = new KeycloakGrantedAuthoritiesMapper();
   }

   @Test
   void mapAuthorities_OidcUserRealmAccessRoleClaims() {
      var roles = newArrayList(ROLE_ADMIN, ROLE_PRM_USER, ROLE_DELEGATE);
      var realmClaims = new HashMap<String, Object>();
      realmClaims.put(ROLES_CLAIM, roles);

      when(oidcUserInfo.getClaims()).thenReturn(realmClaims);
      when(oidcUserInfo.hasClaim(REALM_ACCESS_CLAIM)).thenReturn(true);
      when(oidcUserInfo.getClaimAsMap(REALM_ACCESS_CLAIM)).thenReturn(realmClaims);

      var authorities = new ArrayList<OidcUserAuthority>();
      var auth = new OidcUserAuthority(oidcIdToken, oidcUserInfo);
      authorities.add(auth);

      var result = (Collection<GrantedAuthority>) authoritiesMapper.mapAuthorities(authorities);
      var expectedRoles = toSpringRoles(ROLE_ADMIN, ROLE_PRM_USER, ROLE_DELEGATE);
      assertThat(result).hasSize(3).containsExactlyInAnyOrderElementsOf(expectedRoles);
   }

   @Test
   void mapAuthorities_OidcUserGroupRoleClaims() {
      var roles = newArrayList(ROLE_ADMIN, ROLE_PRM_USER, ROLE_DELEGATE);
      var claims = new HashMap<String, Object>();
      claims.put(ROLES_CLAIM, roles);

      when(oidcUserInfo.getClaims()).thenReturn(claims);
      when(oidcUserInfo.hasClaim(REALM_ACCESS_CLAIM)).thenReturn(false);
      when(oidcUserInfo.hasClaim(GROUPS)).thenReturn(true);
      when(oidcUserInfo.getClaim(GROUPS)).thenReturn(roles);

      var authorities = new ArrayList<OidcUserAuthority>();
      var auth = new OidcUserAuthority(oidcIdToken, oidcUserInfo);
      authorities.add(auth);

      var result = (Collection<GrantedAuthority>) authoritiesMapper.mapAuthorities(authorities);
      var expectedRoles = toSpringRoles(ROLE_ADMIN, ROLE_PRM_USER, ROLE_DELEGATE);
      assertThat(result).hasSize(3).containsExactlyInAnyOrderElementsOf(expectedRoles);
   }

   @Test
   void mapAuthorities_NoRoleClaims() {
      var roles = newArrayList(ROLE_ADMIN, ROLE_PRM_USER, ROLE_DELEGATE);
      var claims = new HashMap<String, Object>();
      claims.put(ROLES_CLAIM, roles);

      when(oidcUserInfo.getClaims()).thenReturn(claims);
      when(oidcUserInfo.hasClaim(REALM_ACCESS_CLAIM)).thenReturn(false);
      when(oidcUserInfo.hasClaim(GROUPS)).thenReturn(false);

      var authorities = new ArrayList<OidcUserAuthority>();
      var auth = new OidcUserAuthority(oidcIdToken, oidcUserInfo);
      authorities.add(auth);

      var result = authoritiesMapper.mapAuthorities(authorities);
      assertThat(result).isEmpty();
   }

   @Test
   void mapAuthorities_Oauth2UserRealmAccessRoleClaims() {
      var roles = newArrayList(ROLE_ADMIN, ROLE_PRM_USER, ROLE_DELEGATE, ROLE_ANONYMOUS);
      var realmClaims = new HashMap<String, Object>();
      realmClaims.put(ROLES_CLAIM, roles);

      var attributes = new HashMap<String, Object>();
      attributes.put(REALM_ACCESS_CLAIM, realmClaims);

      var authorities = new ArrayList<OAuth2UserAuthority>();
      var auth = new OAuth2UserAuthority(attributes);
      authorities.add(auth);

      var result = (Collection<GrantedAuthority>) authoritiesMapper.mapAuthorities(authorities);
      var expectedRoles = toSpringRoles(ROLE_ADMIN, ROLE_PRM_USER, ROLE_DELEGATE, ROLE_ANONYMOUS);
      assertThat(result).hasSize(4).containsExactlyInAnyOrderElementsOf(expectedRoles);
   }

   @Test
   void mapAuthorities_Oauth2UserNoRoleClaims() {
      var attributes = new HashMap<String, Object>();
      attributes.put(uuid(), uuid());

      var authorities = new ArrayList<OAuth2UserAuthority>();
      var auth = new OAuth2UserAuthority(attributes);
      authorities.add(auth);

      var result = authoritiesMapper.mapAuthorities(authorities);
      assertThat(result).isEmpty();
   }

   private Set<GrantedAuthority> toSpringRoles(String... roles) {
      return Stream.of(roles).map(this::toSpringRole).collect(toSet());
   }

   private GrantedAuthority toSpringRole(String role) {
      return new SimpleGrantedAuthority("ROLE_" + role);
   }
}