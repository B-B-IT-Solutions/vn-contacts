package cz.prm.controllers.dto.task.query;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TasksFilterDto {

    private String globalFilter;

    private String title;

    private Boolean completed;

    private String dueDate;
}
