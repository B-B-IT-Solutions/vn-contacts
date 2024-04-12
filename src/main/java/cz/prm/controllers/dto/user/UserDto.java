package cz.prm.controllers.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

   @JsonProperty("email")
   private String email;
}
