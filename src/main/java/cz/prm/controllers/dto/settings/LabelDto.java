package cz.prm.controllers.dto.settings;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabelDto {

    @JsonProperty("value")
    private String value;

    @JsonProperty("color")
    private String color;
}
