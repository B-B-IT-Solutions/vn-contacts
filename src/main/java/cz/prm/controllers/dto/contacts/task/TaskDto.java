package cz.prm.controllers.dto.contacts.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.prm.domain.common.Priority;
import cz.prm.domain.contacts.task.TaskStatus;
import java.time.Instant;
import java.util.List;
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

    @JsonProperty("referralId")
    private Long referralId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("outcomes")
    private String outcomes;

    @JsonProperty("status")
    private TaskStatus status;

    @JsonProperty("priority")
    private Priority priority;

    @JsonProperty("startDate")
    private Instant startDate;

    @JsonProperty("endDate")
    private Instant endDate;

    @JsonProperty("reminderRules")
    private List<String> reminderRules;

    @JsonProperty("lastEditDate")
    private Instant lastEditDate;

    @JsonProperty("creationDate")
    private Instant creationDate;
}
