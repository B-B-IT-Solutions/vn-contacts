package cz.prm.controllers.dto.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AboutDto {

    @JsonProperty("description")
    private String description;

    @JsonProperty("contactId")
    private Long contactId;
}
