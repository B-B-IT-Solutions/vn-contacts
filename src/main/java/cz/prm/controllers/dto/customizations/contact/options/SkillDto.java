package cz.prm.controllers.dto.customizations.contact.options;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillDto {

    @JsonProperty("value")
    private String value;

    @JsonProperty("description")
    private String description;
}
