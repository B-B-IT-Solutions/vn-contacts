package cz.prm.controllers.dto.settings.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactSettingsDto {

    @JsonProperty("settingsId")
    private Long settingsId;

    @JsonProperty("labels")
    private List<LabelDto> labels;

    @JsonProperty("industries")
    private List<IndustryDto> industries;

    @JsonProperty("skills")
    private List<SkillDto> skills;

    @JsonProperty("lastEditDate")
    private Instant lastEditDate;
}
