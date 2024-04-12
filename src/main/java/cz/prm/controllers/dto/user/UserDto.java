package cz.prm.controllers.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

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

   public UserDto(OidcUserInfo userInfo) {
      this.username = userInfo.getPreferredUsername();
      this.email = userInfo.getEmail();
      this.fullName = userInfo.getFullName();
      this.firstName = userInfo.getGivenName();
      this.lastName = userInfo.getFamilyName();
   }
}
