package cz.prm.controllers.dto.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

   @JsonProperty("contactId")
   private Long contactId;

   @JsonProperty("firstName")
   private String firstName;

   @JsonProperty("lastName")
   private String lastName;

   @JsonProperty("email")
   private String email;

}
