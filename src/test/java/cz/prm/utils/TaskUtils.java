package cz.prm.utils;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.domain.common.Priority.HIGH;
import static cz.prm.domain.contacts.task.TaskStatus.TO_DO;
import static cz.prm.utils.CommonUtils.pagination;
import static cz.prm.utils.CommonUtils.paginationDto;
import static cz.prm.utils.CommonUtils.user;
import static cz.prm.utils.RecurrenceUtils.recurrences;
import static cz.prm.utils.RecurrenceUtils.reminderRules;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.TestUtils.uuid;
import static java.time.Instant.now;

import cz.prm.controllers.dto.contacts.task.TaskDto;
import cz.prm.controllers.dto.contacts.task.query.TasksFilterDto;
import cz.prm.controllers.dto.contacts.task.query.TasksQueryDto;
import cz.prm.domain.contacts.task.Task;
import cz.prm.domain.contacts.task.query.TasksFilter;
import cz.prm.domain.contacts.task.query.TasksQuery;
import java.util.List;

public class TaskUtils {

    public static List<Task> tasks() {
        return newArrayList(task(), task(), task());
    }

    public static Task task() {
        var task = new Task();
        task.setTaskId(randomLong());
        task.setContactId(randomLong());
        task.setReferralId(randomLong());
        task.setName(uuid());
        task.setDescription(uuid());
        task.setOutcomes(uuid());
        task.setStatus(TO_DO);
        task.setPriority(HIGH);
        task.setStartDate(now());
        task.setEndDate(now());
        task.setReminders(recurrences());
        task.setLastEditDate(now());
        task.setCreationDate(now());
        task.setOwner(user());
        return task;
    }

    public static TaskDto taskDto() {
        var task = new TaskDto();
        task.setTaskId(randomLong());
        task.setContactId(randomLong());
        task.setReferralId(randomLong());
        task.setName(uuid());
        task.setDescription(uuid());
        task.setOutcomes(uuid());
        task.setStatus(TO_DO);
        task.setPriority(HIGH);
        task.setStartDate(now());
        task.setEndDate(now());
        task.setReminderRules(reminderRules());
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
        filter.setContactId(randomLong());
        filter.setReferralId(randomLong());
        filter.setName(uuid());
        filter.setStatus("TO_DO");
        filter.setEndDate(uuid());
        return filter;
    }

    public static TasksFilterDto tasksFilterDto() {
        var filter = new TasksFilterDto();
        filter.setGlobalFilter(uuid());
        filter.setContactId(randomLong());
        filter.setReferralId(randomLong());
        filter.setName(uuid());
        filter.setStatus("IN_PROGRESS");
        filter.setEndDate(uuid());
        return filter;
    }
}
