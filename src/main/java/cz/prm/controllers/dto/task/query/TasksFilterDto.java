package cz.prm.controllers.dto.task.query;

import cz.prm.domain.task.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TasksFilterDto {

    private String globalFilter;

    private String name;

    private Status status;

    private String dueDate;
}
