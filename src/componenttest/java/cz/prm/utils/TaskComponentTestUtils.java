package cz.prm.utils;

import static cz.prm.domain.common.Priority.HIGH;
import static cz.prm.domain.task.TaskStatus.TO_DO;
import static cz.prm.utils.ComponentTestUtils.uuid;
import static cz.prm.utils.RecurrenceComponentTestUtils.recurrences;
import static cz.prm.utils.RecurrenceComponentTestUtils.reminderRules;
import static cz.prm.utils.TimeComponentTestUtils.todayStartOfDay;
import static java.lang.String.format;

import cz.prm.controllers.dto.common.PaginationDto;
import cz.prm.controllers.dto.task.TaskDto;
import cz.prm.controllers.dto.task.query.TasksFilterDto;
import cz.prm.controllers.dto.task.query.TasksQueryDto;
import cz.prm.domain.task.Task;

public class TaskComponentTestUtils {

    public static Task task(Long contactId, Long referralId) {
        var task = new Task();
        task.setContactId(contactId);
        task.setReferralId(referralId);
        task.setName(format("Title-%s-End", uuid()));
        task.setDescription(format("Text%s", uuid()));
        task.setOutcomes(uuid());
        task.setStatus(TO_DO);
        task.setPriority(HIGH);
        task.setStartDate(todayStartOfDay());
        task.setEndDate(todayStartOfDay());
        task.setReminders(recurrences());
        return task;
    }

    public static TaskDto taskDto(Long contactId) {
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

    public static TasksQueryDto tasksQueryDto() {
        var query = new TasksQueryDto();
        query.setPagination(new PaginationDto());
        query.setFilter(new TasksFilterDto());
        return query;
    }
}
