package cz.prm.controllers.mappers;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.domain.task.query.TasksQuery.DEFAULT_TASKS_SORT;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.task.TaskDto;
import cz.prm.controllers.dto.task.query.TasksQueryDto;
import cz.prm.domain.common.query.Page;
import cz.prm.domain.common.query.Pagination;
import cz.prm.domain.recurrence.Recurrence;
import cz.prm.domain.task.Task;
import cz.prm.domain.task.TaskReminder;
import cz.prm.domain.task.query.TasksFilter;
import cz.prm.domain.task.query.TasksQuery;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    PageDto<TaskDto> toPageDto(Page<Task> tasks);

    @Mapping(target = "reminderRules", source = "reminder", qualifiedByName = "toReminderRules")
    TaskDto toTaskDto(Task task);

    @Mapping(target = "reminder", source = "reminderRules", qualifiedByName = "toTaskReminder")
    @Mapping(target = "owner", ignore = true)
    Task toTask(TaskDto dto);

    TasksQuery toTasksQuery(TasksQueryDto dto);

    @Named("toReminderRules")
    default List<String> toReminderRules(TaskReminder tr) {
        if (nonNull(tr)) {
            return tr.getReminderRuleValues();
        }
        return newArrayList();
    }

    @Named("toTaskReminder")
    default TaskReminder toTaskReminder(List<String> reminderRules) {
        if (isNotEmpty(reminderRules)) {
            var tr = new TaskReminder();
            var recurrences = reminderRules.stream().map(r -> new Recurrence(r)).collect(toList());
            tr.setReminderRules(recurrences);
            return tr;
        }
        return null;
    }

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
