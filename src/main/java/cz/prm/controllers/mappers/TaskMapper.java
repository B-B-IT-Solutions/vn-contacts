package cz.prm.controllers.mappers;

import static cz.prm.domain.task.query.TasksQuery.DEFAULT_TASKS_SORT;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.task.TaskDto;
import cz.prm.controllers.dto.task.query.TasksQueryDto;
import cz.prm.domain.common.query.Page;
import cz.prm.domain.common.query.Pagination;
import cz.prm.domain.task.Task;
import cz.prm.domain.task.query.TasksFilter;
import cz.prm.domain.task.query.TasksQuery;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    PageDto<TaskDto> toPageDto(Page<Task> tasks);

    TaskDto toTaskDto(Task task);

    @Mapping(target = "owner", ignore = true)
    Task toTask(TaskDto dto);

    TasksQuery toTasksQuery(TasksQueryDto dto);

    default TasksQuery toNullSafeTasksQuery(TasksQueryDto dto) {
        if (isNull(dto)) {
            return new TasksQuery();
        }
        return toTasksQuery(dto);
    }

    @AfterMapping
    default void afterTasksQuery(TasksQueryDto source, @MappingTarget TasksQuery target) {
        if (isNull(target.getPagination())) {
            target.setPagination(new Pagination());
        }
        if (isNull(target.getFilter())) {
            target.setFilter(new TasksFilter());
        }
        if (isBlank(target.getSort())) {
            target.setSort(DEFAULT_TASKS_SORT);
        }
    }
}
