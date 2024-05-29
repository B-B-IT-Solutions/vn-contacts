package cz.prm.controllers.dto.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {

    @JsonProperty("noteId")
    private Long noteId;

    @JsonProperty("contactId")
    private Long contactId;

    @JsonProperty("text")
    private String text;

    @JsonProperty("lastEditDate")
    private Instant lastEditDate;

    @JsonProperty("creationDate")
    private Instant creationDate;
}
