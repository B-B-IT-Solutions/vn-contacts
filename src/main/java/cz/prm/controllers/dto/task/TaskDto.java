package cz.prm.controllers.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.prm.domain.task.Priority;
import cz.prm.domain.task.Status;
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

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("outcomes")
    private String outcomes;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("priority")
    private Priority priority;

    @JsonProperty("dueDate")
    private Instant dueDate;

    @JsonProperty("lastEditDate")
    private Instant lastEditDate;

    @JsonProperty("creationDate")
    private Instant creationDate;
}
