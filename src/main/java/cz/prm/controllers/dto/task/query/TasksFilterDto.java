package cz.prm.controllers.dto.task.query;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TasksFilterDto {

    private String globalFilter;

    private String name;

    private String status;

    private String dueDate;
}
