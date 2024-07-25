package cz.prm.controllers.dto.settings;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralSettingsDto {

    @JsonProperty("settingsId")
    private Long settingsId;

    @JsonProperty("appLanguage")
    private String appLanguage;
}
