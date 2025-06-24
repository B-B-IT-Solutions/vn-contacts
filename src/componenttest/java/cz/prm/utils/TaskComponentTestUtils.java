package cz.prm.utils;

import static cz.prm.domain.common.Priority.HIGH;
import static cz.prm.domain.task.TaskStatus.TO_DO;
import static cz.prm.utils.ComponentTestUtils.randomLong;
import static cz.prm.utils.ComponentTestUtils.uuid;
import static cz.prm.utils.RecurrenceComponentTestUtils.recurrences;
import static cz.prm.utils.RecurrenceComponentTestUtils.reminderRules;
import static cz.prm.utils.TimeComponentTestUtils.todayStartOfDay;
import static java.lang.String.format;
import static org.assertj.core.util.Lists.newArrayList;

import cz.prm.controllers.dto.common.PaginationDto;
import cz.prm.controllers.dto.task.TaskDto;
import cz.prm.controllers.dto.task.query.TasksFilterDto;
import cz.prm.controllers.dto.task.query.TasksQueryDto;
import cz.prm.domain.task.Task;
import cz.prm.domain.task.TaskReminder;
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
        task.setName(format("Title-%s-End", uuid()));
        task.setDescription(format("Text%s", uuid()));
        task.setOutcomes(uuid());
        task.setStatus(TO_DO);
        task.setPriority(HIGH);
        task.setStartDate(todayStartOfDay());
        task.setEndDate(todayStartOfDay());
        task.setReminder(taskReminder());
        return task;
    }

    public static TaskDto taskDto(long contactId) {
        var dto = new TaskDto();
        dto.setContactId(contactId);
        dto.setName(uuid());
        dto.setDescription(uuid());
        dto.setOutcomes(TestUtils.uuid());
        dto.setStatus(TO_DO);
        dto.setPriority(HIGH);
        dto.setStartDate(todayStartOfDay());
        dto.setEndDate(todayStartOfDay());
        dto.setReminderRules(reminderRules());
        return dto;
    }

    public static TaskReminder taskReminder() {
        var tr = new TaskReminder();
        tr.setReminderRules(recurrences());
        return tr;
    }

    public static TasksQueryDto tasksQueryDto() {
        var query = new TasksQueryDto();
        query.setPagination(new PaginationDto());
        query.setFilter(new TasksFilterDto());
        return query;
    }
}
