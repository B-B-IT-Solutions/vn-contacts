package cz.prm.utils;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.CommonUtils.pagination;
import static cz.prm.utils.CommonUtils.paginationDto;
import static cz.prm.utils.CommonUtils.user;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.TestUtils.uuid;
import static java.time.Instant.now;

import cz.prm.controllers.dto.task.TaskDto;
import cz.prm.controllers.dto.task.query.TasksFilterDto;
import cz.prm.controllers.dto.task.query.TasksQueryDto;
import cz.prm.domain.task.Task;
import cz.prm.domain.task.query.TasksFilter;
import cz.prm.domain.task.query.TasksQuery;
import java.util.List;

public class TaskUtils {

    public static List<Task> tasks() {
        return newArrayList(task(), task(), task());
    }

    public static Task task() {
        var task = new Task();
        task.setTaskId(randomLong());
        task.setContactId(randomLong());
        task.setTitle(uuid());
        task.setDescription(uuid());
        task.setCompleted(true);
        task.setLastEditDate(now());
        task.setCreationDate(now());
        task.setOwner(user());
        return task;
    }

    public static TaskDto taskDto() {
        var task = new TaskDto();
        task.setTaskId(randomLong());
        task.setContactId(randomLong());
        task.setTitle(uuid());
        task.setDescription(uuid());
        task.setCompleted(true);
        task.setLastEditDate(now());
        task.setCreationDate(now());
        return task;
    }

    public static TasksQuery tasksQuery() {
        var query = new TasksQuery();
        query.setPagination(pagination());
        query.setSort(uuid());
        query.setFilter(tasksFilter());
        return query;
    }

    public static TasksQueryDto tasksQueryDto() {
        var query = new TasksQueryDto();
        query.setPagination(paginationDto());
        query.setSort(uuid());
        query.setFilter(tasksFilterDto());
        return query;
    }

    public static TasksFilter tasksFilter() {
        var filter = new TasksFilter();
        filter.setGlobalFilter(uuid());
        filter.setTitle(uuid());
        filter.setCompleted(true);
        return filter;
    }

    public static TasksFilterDto tasksFilterDto() {
        var filter = new TasksFilterDto();
        filter.setGlobalFilter(uuid());
        filter.setTitle(uuid());
        filter.setCompleted(true);
        return filter;
    }
}
