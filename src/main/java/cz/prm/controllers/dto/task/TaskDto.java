package cz.prm.controllers.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    @JsonProperty("taskId")
    private Long taskId;

    @JsonProperty("contactId")
    private Long contactId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("lastEditDate")
    private Instant lastEditDate;

    @JsonProperty("creationDate")
    private Instant creationDate;
}
