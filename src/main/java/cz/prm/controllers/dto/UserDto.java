package cz.prm.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  @JsonProperty("firstName")
  private String firstName;

  @JsonProperty("lastName")
  private String lastName;
}
