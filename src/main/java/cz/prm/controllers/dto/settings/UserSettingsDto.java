package cz.prm.controllers.dto.settings;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSettingsDto {

    @JsonProperty("settingsId")
    private Long settingsId;

    @JsonProperty("labels")
    private List<LabelDto> labels;

    @JsonProperty("lastEditDate")
    private Instant lastEditDate;
}
