package cz.prm.controllers.mapper.contacts;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.CommonUtils.DEFAULT_PAGE_SIZE;
import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.assertions.RecurrenceAssertions.assertRecurrencesDto;
import static cz.prm.utils.assertions.TaskAssertions.assertPage;
import static cz.prm.utils.assertions.TaskAssertions.assertTask;
import static cz.prm.utils.assertions.TaskAssertions.assertTasksQuery;
import static cz.prm.utils.data.contacts.RecurrenceUtils.recurrences;
import static cz.prm.utils.data.contacts.RecurrenceUtils.reminderRules;
import static cz.prm.utils.data.contacts.TaskUtils.task;
import static cz.prm.utils.data.contacts.TaskUtils.taskDto;
import static cz.prm.utils.data.contacts.TaskUtils.tasks;
import static cz.prm.utils.data.contacts.TaskUtils.tasksQueryDto;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.contacts.task.query.TasksQueryDto;
import cz.prm.domain.contacts.task.query.TasksQuery;
import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.Test;

class TaskMapperTest {

    public static final String DEFAULT_TASKS_SORT = "desc(creationDate)";

    private TaskMapper mapper = MapperUtils.getTaskMapper();

    @Test
    void toPageDto() {
        var page = page(tasks());
        var dtos = mapper.toPageDto(page);
        assertPage(page, dtos);
    }

    @Test
    void toTaskDto() {
        var task = task();
        var dto = mapper.toTaskDto(task);
        assertTask(task, dto);
    }

    @Test
    void toTask() {
        var dto = taskDto();
        var task = mapper.toTask(dto);
        assertTask(task, dto);
    }

    @Test
    void toReminderRules() {
        var reminders = recurrences();
        var rRules = mapper.toReminderRules(reminders);
        assertRecurrencesDto(reminders, rRules);
    }

    @Test
    void toReminderRules_EmptyRules() {
        var rRules = mapper.toReminderRules(newArrayList());
        assertThat(rRules).isNotNull().isEmpty();
    }

    @Test
    void toReminderRules_NullReminders() {
        var rRules = mapper.toReminderRules(null);
        assertThat(rRules).isNotNull().isEmpty();
    }

    @Test
    void toReminders() {
        var rRules = reminderRules();
        var reminders = mapper.toReminders(rRules);
        assertRecurrencesDto(reminders, rRules);
    }

    @Test
    void toReminders_EmptyRules() {
        var reminders = mapper.toReminders(newArrayList());
        assertThat(reminders).isNotNull().isEmpty();
    }

    @Test
    void toReminders_NullRules() {
        var reminders = mapper.toReminders(null);
        assertThat(reminders).isNotNull().isEmpty();
    }

    @Test
    void toTasksQuery() {
        var dto = tasksQueryDto();
        var query = mapper.toTasksQuery(dto);
        assertTasksQuery(query, dto);
    }

    @Test
    void toNullSafeTasksQueryNullQuery() {
        var query = mapper.toNullSafeTasksQuery(null);
        assertNullSafeTaskQuery(query);
    }

    @Test
    void toNullSafeTasksQueryNotNullQuery() {
        var dto = tasksQueryDto();
        var query = mapper.toNullSafeTasksQuery(dto);
        assertTasksQuery(query, dto);
    }

    @Test
    void toNullSafeTasksQueryNullPagination() {
        var dto = new TasksQueryDto();
        dto.setPagination(null);
        var query = mapper.toNullSafeTasksQuery(dto);
        assertNullSafeTaskQuery(query);
    }

    @Test
    void afterTasksQuery() {
        var target = new TasksQuery();
        target.setPagination(null);
        mapper.afterTasksQuery(null, target);
        assertNullSafeTaskQuery(target);
    }

    private void assertNullSafeTaskQuery(TasksQuery query) {
        assertThat(query.getPagination()).isNotNull();
        assertThat(query.getFilter()).isNotNull();
        assertThat(query.getSort()).isEqualTo(DEFAULT_TASKS_SORT);
        var pagination = query.getPagination();
        assertThat(pagination.getPageNumber()).isZero();
        assertThat(pagination.getPageSize()).isEqualTo(DEFAULT_PAGE_SIZE);
    }
}