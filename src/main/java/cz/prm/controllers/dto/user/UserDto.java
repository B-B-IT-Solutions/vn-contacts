package cz.prm.controllers.dto.user;

import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.EMAIL;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.FAMILY_NAME;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.GIVEN_NAME;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.NAME;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.PREFERRED_USERNAME;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.ClaimAccessor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

   @JsonProperty("userId")
   private Long userId;

   @JsonProperty("firstName")
   private String firstName;

   @JsonProperty("lastName")
   private String lastName;

   @JsonProperty("username")
   private String username;

   @JsonProperty("email")
   private String email;

   @JsonProperty("fullName")
   private String fullName;

   public UserDto(ClaimAccessor jwt) {
      this.username = jwt.getClaimAsString(PREFERRED_USERNAME);
      this.email = jwt.getClaimAsString(EMAIL);
      this.fullName = jwt.getClaimAsString(NAME);
      this.firstName = jwt.getClaimAsString(GIVEN_NAME);
      this.lastName = jwt.getClaimAsString(FAMILY_NAME);
   }
}
