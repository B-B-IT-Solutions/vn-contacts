package cz.prm.controllers.dto.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

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

}
