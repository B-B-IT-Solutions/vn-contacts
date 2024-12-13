package cz.prm.controllers.dto.task.query;

import cz.prm.controllers.dto.common.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TasksQueryDto extends QueryDto {

    private TasksFilterDto filter;
}
