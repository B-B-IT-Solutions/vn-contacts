package cz.prm.utils;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.domain.task.Priority.HIGH;
import static cz.prm.domain.task.Status.IN_PROGRESS;
import static cz.prm.domain.task.Status.TO_DO;
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
        task.setName(uuid());
        task.setDescription(uuid());
        task.setOutcomes(uuid());
        task.setStatus(TO_DO);
        task.setPriority(HIGH);
        task.setDueDate(now());
        task.setLastEditDate(now());
        task.setCreationDate(now());
        task.setOwner(user());
        return task;
    }

    public static TaskDto taskDto() {
        var task = new TaskDto();
        task.setTaskId(randomLong());
        task.setContactId(randomLong());
        task.setName(uuid());
        task.setDescription(uuid());
        task.setOutcomes(uuid());
        task.setStatus(TO_DO);
        task.setPriority(HIGH);
        task.setDueDate(now());
        task.setLastEditDate(now());
        task.setCreationDate(now());
        return task;
    }

    public static TasksQuery tasksQuery() {
        var query = new TasksQuery();
        query.setPagination(pagination());
        query.setFilter(tasksFilter());
        query.setSort(uuid());
        return query;
    }

    public static TasksQueryDto tasksQueryDto() {
        var query = new TasksQueryDto();
        query.setPagination(paginationDto());
        query.setFilter(tasksFilterDto());
        query.setSort(uuid());
        return query;
    }

    public static TasksFilter tasksFilter() {
        var filter = new TasksFilter();
        filter.setGlobalFilter(uuid());
        filter.setName(uuid());
        filter.setStatus(TO_DO);
        filter.setDueDate(uuid());
        return filter;
    }

    public static TasksFilterDto tasksFilterDto() {
        var filter = new TasksFilterDto();
        filter.setGlobalFilter(uuid());
        filter.setName(uuid());
        filter.setStatus(IN_PROGRESS);
        filter.setDueDate(uuid());
        return filter;
    }
}
