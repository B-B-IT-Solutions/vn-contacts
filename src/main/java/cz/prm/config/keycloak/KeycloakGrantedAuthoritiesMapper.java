package cz.prm.config.keycloak;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Component;

@Component
public class KeycloakGrantedAuthoritiesMapper implements GrantedAuthoritiesMapper {

    private static final String GROUPS = "groups";
    private static final String REALM_ACCESS_CLAIM = "realm_access";
    private static final String ROLES_CLAIM = "roles";

    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
        var mappedAuthorities = new HashSet<GrantedAuthority>();
        var authority = authorities.iterator().next();
        var isOidc = authority instanceof OidcUserAuthority;

        if (isOidc) {
            var oidcUserAuthority = (OidcUserAuthority) authority;
            var oidcAuthorities = mapOidcUserAuthorities(oidcUserAuthority);
            mappedAuthorities.addAll(oidcAuthorities);
        } else {
            var oauth2UserAuthority = (OAuth2UserAuthority) authority;
            var oauth2Authorities = mapOAuth2UserAuthorities(oauth2UserAuthority);
            mappedAuthorities.addAll(oauth2Authorities);
        }
        return mappedAuthorities;
    }

    private List<GrantedAuthority> mapOAuth2UserAuthorities(OAuth2UserAuthority oauth2UserAuthority) {
        var userAttributes = oauth2UserAuthority.getAttributes();
        if (userAttributes.containsKey(REALM_ACCESS_CLAIM)) {
            var realmAccess = (Map<String, Object>) userAttributes.get(REALM_ACCESS_CLAIM);
            var roles = (Collection<String>) realmAccess.get(ROLES_CLAIM);
            return generateAuthoritiesFromClaim(roles);
        }
        return new ArrayList<>();
    }

    private List<GrantedAuthority> mapOidcUserAuthorities(OidcUserAuthority oidcUserAuthority) {
        var userInfo = oidcUserAuthority.getUserInfo();
        if (userInfo.hasClaim(REALM_ACCESS_CLAIM)) {
            var realmAccess = userInfo.getClaimAsMap(REALM_ACCESS_CLAIM);
            var roles = (Collection<String>) realmAccess.get(ROLES_CLAIM);
            return generateAuthoritiesFromClaim(roles);
        } else if (userInfo.hasClaim(GROUPS)) {
            var roles = (Collection<String>) userInfo.getClaim(GROUPS);
            return generateAuthoritiesFromClaim(roles);
        }
        return new ArrayList<>();
    }

    private List<GrantedAuthority> generateAuthoritiesFromClaim(Collection<String> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(toList());
    }
}
