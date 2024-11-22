package cz.prm.controllers.dto.settings.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteSettingsDto {

    @JsonProperty("settingsId")
    private Long settingsId;

    @JsonProperty("categories")
    private List<CategoryDto> categories;

    @JsonProperty("lastEditDate")
    private Instant lastEditDate;
}
