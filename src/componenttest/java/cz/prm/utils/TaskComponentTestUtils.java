package cz.prm.utils;

import static cz.prm.utils.ComponentTestUtils.randomLong;
import static cz.prm.utils.ComponentTestUtils.uuid;
import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static org.assertj.core.util.Lists.newArrayList;

import cz.prm.controllers.dto.common.PaginationDto;
import cz.prm.controllers.dto.task.TaskDto;
import cz.prm.controllers.dto.task.query.TasksFilterDto;
import cz.prm.controllers.dto.task.query.TasksQueryDto;
import cz.prm.domain.task.Task;
import java.util.List;

public class TaskComponentTestUtils {

    public static List<Task> tasks() {
        return newArrayList(task(), task(), task());
    }

    public static Task task() {
        return task(randomLong());
    }

    public static Task task(long contactId) {
        var task = new Task();
        task.setContactId(contactId);
        task.setTitle(format("Title-%s-End", uuid()));
        task.setDescription(format("Text%s", uuid()));
        task.setCompleted(true);
        task.setDueDate(now());
        return task;
    }

    public static TaskDto taskDto(long contactId) {
        var dto = new TaskDto();
        dto.setContactId(contactId);
        dto.setTitle(uuid());
        dto.setDescription(uuid());
        dto.setCompleted(true);
        dto.setDueDate(now());
        return dto;
    }

    public static TasksQueryDto tasksQueryDto() {
        var query = new TasksQueryDto();
        query.setPagination(new PaginationDto());
        query.setFilter(new TasksFilterDto());
        return query;
    }
}
