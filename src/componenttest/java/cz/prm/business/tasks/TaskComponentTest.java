package cz.prm.business.tasks;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static cz.prm.utils.ComponentTestUtils.betweenFilter;
import static cz.prm.utils.ComponentTestUtils.emptyFilter;
import static cz.prm.utils.ComponentTestUtils.endsWithFilter;
import static cz.prm.utils.ComponentTestUtils.equalsFilter;
import static cz.prm.utils.ComponentTestUtils.greaterThanFilter;
import static cz.prm.utils.ComponentTestUtils.greaterThanOrEqualToFilter;
import static cz.prm.utils.ComponentTestUtils.lessThanFilter;
import static cz.prm.utils.ComponentTestUtils.lessThanOrEqualToFilter;
import static cz.prm.utils.ComponentTestUtils.notEmptyFilter;
import static cz.prm.utils.ComponentTestUtils.notEqualsFilter;
import static cz.prm.utils.ComponentTestUtils.randomLong;
import static cz.prm.utils.ComponentTestUtils.startsWithFilter;
import static cz.prm.utils.ComponentTestUtils.uuid;
import static cz.prm.utils.TaskComponentTestUtils.taskDto;
import static cz.prm.utils.TaskComponentTestUtils.tasksQueryDto;
import static cz.prm.utils.TimeComponentTestUtils.todayStartOfDay;
import static cz.prm.utils.assertions.TaskComponentTestAssertions.assertTask;
import static cz.prm.utils.assertions.TaskComponentTestAssertions.assertTasks;
import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Collections.sort;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.task.TaskDto;
import org.junit.jupiter.api.Test;

public class TaskComponentTest extends TaskComponentTestBase {

    @Test
    void getTasksDataAccess() {
        var queryDto = tasksQueryDto();
        var pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user2GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user1Tasks = createTasks(USER_1);
        pageDto = user1GetTasks(queryDto);
        assertTasks(user1Tasks, pageDto);

        pageDto = user2GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user2Tasks = createTasks(USER_2);
        pageDto = user2GetTasks(queryDto);
        assertTasks(user2Tasks, pageDto);

        pageDto = user1GetTasks(queryDto);
        assertTasks(user1Tasks, pageDto);

        pageDto = user3GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user3Tasks = createTasks(USER_3);
        pageDto = user3GetTasks(queryDto);
        assertTasks(user3Tasks, pageDto);

        pageDto = user1GetTasks(queryDto);
        assertTasks(user1Tasks, pageDto);

        pageDto = user2GetTasks(queryDto);
        assertTasks(user2Tasks, pageDto);
    }

    @Test
    void getTasksByContactIdDataAccess() {
        var queryDto = tasksQueryDto();
        var contactId = randomLong();
        var pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user2GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var tasks = createTasks(USER_1);
        var task = tasks.get(0);
        contactId = task.getContactId();

        pageDto = user1GetContactTasks(contactId, queryDto);
        assertTasks(tasks, pageDto);

        pageDto = user2GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user2Tasks = createTasks(USER_2);
        task = user2Tasks.get(0);
        contactId = task.getContactId();

        pageDto = user2GetContactTasks(contactId, queryDto);
        assertTasks(user2Tasks, pageDto);

        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user3Tasks = createTasks(USER_3);
        task = user3Tasks.get(0);
        contactId = task.getContactId();

        pageDto = user3GetContactTasks(contactId, queryDto);
        assertTasks(user3Tasks, pageDto);

        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user2GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();
    }

    @Test
    void getTasksPagination() {
        var queryDto = tasksQueryDto();
        var pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getTotalPages()).isZero();
        assertThat(pageDto.getTotalElements()).isZero();
        assertThat(pageDto.getPageSize()).isEqualTo(50);
        assertThat(pageDto.getContent()).isEmpty();

        createTasks(USER_1, 21);
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(1);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(50);
        assertThat(pageDto.getContent()).hasSize(21);

        var pagination = queryDto.getPagination();
        pagination.setPageSize(5);
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(5);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(5);
        assertThat(pageDto.getContent()).hasSize(5);

        pagination.setPageNumber(1);
        pagination.setPageSize(10);
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(3);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(10);
        assertThat(pageDto.getContent()).hasSize(10);

        pagination.setPageNumber(2);
        pagination.setPageSize(10);
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(3);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(10);
        assertThat(pageDto.getContent()).hasSize(1);
    }

    @Test
    void getTasksByContactIdPagination() {
        var queryDto = tasksQueryDto();
        var contactId = randomLong();
        var pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isZero();
        assertThat(pageDto.getTotalElements()).isZero();
        assertThat(pageDto.getPageSize()).isEqualTo(50);
        assertThat(pageDto.getContent()).isEmpty();

        var tasks = createTasks(USER_1, 21);
        var task = tasks.get(0);
        contactId = task.getContactId();

        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(1);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(50);
        assertThat(pageDto.getContent()).hasSize(21);

        var pagination = queryDto.getPagination();
        pagination.setPageSize(5);
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(5);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(5);
        assertThat(pageDto.getContent()).hasSize(5);

        pagination.setPageNumber(1);
        pagination.setPageSize(10);
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(3);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(10);
        assertThat(pageDto.getContent()).hasSize(10);

        pagination.setPageNumber(2);
        pagination.setPageSize(10);
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(3);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(10);
        assertThat(pageDto.getContent()).hasSize(1);
    }

    @Test
    void getTasksSorting() {
        var queryDto = tasksQueryDto();
        var pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        createTasks(USER_1, 21);
        queryDto = tasksQueryDto();
        queryDto.setSort(null);
        pageDto = user1GetTasks(queryDto);
        var actual = pageDto.getContent();
        var expected = newArrayList(actual);
        sort(expected, comparing(TaskDto::getCreationDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto = tasksQueryDto();
        queryDto.setSort("asc(lastEditDate)");
        pageDto = user1GetTasks(queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(TaskDto::getLastEditDate));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(lastEditDate)");
        pageDto = user1GetTasks(queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(TaskDto::getLastEditDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("asc(creationDate)");
        pageDto = user1GetTasks(queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(TaskDto::getCreationDate));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(creationDate)");
        pageDto = user1GetTasks(queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(TaskDto::getCreationDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("asc(contactId)");
        pageDto = user1GetTasks(queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(TaskDto::getContactId));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(contactId)");
        pageDto = user1GetTasks(queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(TaskDto::getContactId).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);
    }

    @Test
    void getTasksByContactIdSorting() {
        var queryDto = tasksQueryDto();
        var contactId = randomLong();
        var pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var tasks = createTasks(USER_1, 21);
        var task = tasks.get(0);
        contactId = task.getContactId();

        queryDto = tasksQueryDto();
        queryDto.setSort(null);
        pageDto = user1GetContactTasks(contactId, queryDto);
        var actual = pageDto.getContent();
        var expected = newArrayList(actual);
        sort(expected, comparing(TaskDto::getCreationDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto = tasksQueryDto();
        queryDto.setSort("asc(lastEditDate)");
        pageDto = user1GetContactTasks(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(TaskDto::getLastEditDate));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(lastEditDate)");
        pageDto = user1GetContactTasks(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(TaskDto::getLastEditDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("asc(creationDate)");
        pageDto = user1GetContactTasks(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(TaskDto::getCreationDate));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(creationDate)");
        pageDto = user1GetContactTasks(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(TaskDto::getCreationDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("asc(contactId)");
        pageDto = user1GetContactTasks(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(TaskDto::getContactId));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(contactId)");
        pageDto = user1GetContactTasks(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(TaskDto::getContactId).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);
    }

    @Test
    void getTasksFilters() {
        var queryDto = tasksQueryDto();
        var pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user1Tasks = createTasks(USER_1, 21);
        var userTask1 = user1Tasks.get(0);

        queryDto = tasksQueryDto();
        queryDto.setFilter(null);
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        queryDto = tasksQueryDto();
        var filter = queryDto.getFilter();

        filter.setGlobalFilter(userTask1.getName());
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setGlobalFilter(userTask1.getDescription());
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setGlobalFilter(userTask1.getDescription());
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setGlobalFilter(uuid());
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        queryDto = tasksQueryDto();
        filter = queryDto.getFilter();

        filter.setName(userTask1.getName());
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setName(uuid());
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(startsWithFilter("Title"));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(startsWithFilter(userTask1.getName()));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setName(startsWithFilter("Q"));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(startsWithFilter(uuid()));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(endsWithFilter("End"));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(endsWithFilter(userTask1.getName()));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setName(endsWithFilter("Q"));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(endsWithFilter(uuid()));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(equalsFilter(userTask1.getName()));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setName(equalsFilter("Q"));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(equalsFilter(" "));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(equalsFilter(uuid()));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(notEqualsFilter(userTask1.getName()));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(20);

        filter.setName(notEqualsFilter("Q"));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(notEqualsFilter(uuid()));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(notEqualsFilter(" "));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(notEqualsFilter(uuid()));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(emptyFilter());
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(notEmptyFilter());
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        queryDto = tasksQueryDto();
        filter = queryDto.getFilter();

        filter.setStatus(null);
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setStatus("TO_DO");
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setStatus("IN_PROGRESS");
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setStatus("TO_DO,IN_PROGRESS");
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        queryDto = tasksQueryDto();
        filter = queryDto.getFilter();

        filter.setEndDate(greaterThanFilter(todayStartOfDay().minus(1, DAYS)));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setEndDate(greaterThanFilter(todayStartOfDay()));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setEndDate(greaterThanFilter(todayStartOfDay().plus(1, DAYS)));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setEndDate(greaterThanOrEqualToFilter(todayStartOfDay().minus(1, DAYS)));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setEndDate(greaterThanOrEqualToFilter(todayStartOfDay()));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setEndDate(greaterThanOrEqualToFilter(todayStartOfDay().plus(1, DAYS)));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setEndDate(lessThanFilter(todayStartOfDay().plus(1, DAYS)));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setEndDate(lessThanFilter(todayStartOfDay()));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setEndDate(lessThanFilter(todayStartOfDay().minus(1, DAYS)));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setEndDate(lessThanOrEqualToFilter(todayStartOfDay().plus(1, DAYS)));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setEndDate(lessThanOrEqualToFilter(todayStartOfDay()));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setEndDate(lessThanOrEqualToFilter(todayStartOfDay().minus(1, DAYS)));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setEndDate(betweenFilter(todayStartOfDay().minus(1, DAYS), now().plus(5, DAYS)));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setEndDate(betweenFilter(todayStartOfDay(), now().plus(5, DAYS)));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setEndDate(betweenFilter(todayStartOfDay().plus(1, DAYS), now().plus(5, DAYS)));
        pageDto = user1GetTasks(queryDto);
        assertThat(pageDto.getContent()).isEmpty();
    }

    @Test
    void getTasksByContactIdFilters() {
        var queryDto = tasksQueryDto();
        var contactId = randomLong();
        var pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user1Tasks = createTasks(USER_1, 21);
        var userTask1 = user1Tasks.get(0);
        var userTask = user1Tasks.get(0);
        contactId = userTask.getContactId();

        queryDto = tasksQueryDto();
        queryDto.setFilter(null);
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        queryDto = tasksQueryDto();
        var filter = queryDto.getFilter();

        filter.setGlobalFilter(userTask1.getName());
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setGlobalFilter(userTask1.getDescription());
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setGlobalFilter(userTask1.getDescription());
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setGlobalFilter(uuid());
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        queryDto = tasksQueryDto();
        filter = queryDto.getFilter();

        filter.setName(userTask1.getName());
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setName(uuid());
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(startsWithFilter("Title"));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(startsWithFilter(userTask1.getName()));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setName(startsWithFilter("Q"));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(startsWithFilter(uuid()));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(endsWithFilter("End"));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(endsWithFilter(userTask1.getName()));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setName(endsWithFilter("Q"));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(endsWithFilter(uuid()));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(equalsFilter(userTask1.getName()));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setName(equalsFilter("Q"));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(equalsFilter(" "));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(equalsFilter(uuid()));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(notEqualsFilter(userTask1.getName()));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(20);

        filter.setName(notEqualsFilter("Q"));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(notEqualsFilter(uuid()));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(notEqualsFilter(" "));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(notEqualsFilter(uuid()));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(emptyFilter());
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(notEmptyFilter());
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        queryDto = tasksQueryDto();
        filter = queryDto.getFilter();

        filter.setStatus(null);
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setStatus("TO_DO");
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setStatus("IN_PROGRESS");
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setStatus("TO_DO,IN_PROGRESS");
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        queryDto = tasksQueryDto();
        filter = queryDto.getFilter();

        filter.setEndDate(greaterThanFilter(todayStartOfDay().minus(1, DAYS)));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setEndDate(greaterThanFilter(todayStartOfDay()));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setEndDate(greaterThanFilter(todayStartOfDay().plus(1, DAYS)));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setEndDate(greaterThanOrEqualToFilter(todayStartOfDay().minus(1, DAYS)));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setEndDate(greaterThanOrEqualToFilter(todayStartOfDay()));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setEndDate(greaterThanOrEqualToFilter(todayStartOfDay().plus(1, DAYS)));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setEndDate(lessThanFilter(todayStartOfDay().plus(1, DAYS)));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setEndDate(lessThanFilter(todayStartOfDay()));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setEndDate(lessThanFilter(todayStartOfDay().minus(1, DAYS)));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setEndDate(lessThanOrEqualToFilter(todayStartOfDay().plus(1, DAYS)));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setEndDate(lessThanOrEqualToFilter(todayStartOfDay()));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setEndDate(lessThanOrEqualToFilter(todayStartOfDay().minus(1, DAYS)));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setEndDate(betweenFilter(todayStartOfDay().minus(1, DAYS), now().plus(5, DAYS)));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setEndDate(betweenFilter(todayStartOfDay(), now().plus(5, DAYS)));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setEndDate(betweenFilter(todayStartOfDay().plus(1, DAYS), now().plus(5, DAYS)));
        pageDto = user1GetContactTasks(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();
    }

    @Test
    void getTask() {
        var task = createTask(USER_1);
        var taskId = task.getTaskId();

        var taskDto = user1GetTask(taskId);
        assertTask(task, taskDto);
        user2GetTaskExpectNotFound(taskId);
        user3GetTaskExpectNotFound(taskId);

        task = createTask(USER_2);
        taskId = task.getTaskId();
        taskDto = user2GetTask(taskId);
        assertTask(task, taskDto);
        user1GetTaskExpectNotFound(taskId);
        user3GetTaskExpectNotFound(taskId);

        task = createTask(USER_3);
        taskId = task.getTaskId();
        taskDto = user3GetTask(taskId);
        assertTask(task, taskDto);
        user1GetTaskExpectNotFound(taskId);
        user2GetTaskExpectNotFound(taskId);
    }

    @Test
    void createTask() {
        var contact = createContact(USER_1);
        var toCreateDto = taskDto(contact.getContactId());
        user1CreateTask(toCreateDto);
        var task = getTaskFromDb(toCreateDto);
        var taskId = task.getTaskId();

        var createdDto = user1GetTask(taskId);
        assertTask(task, createdDto);
        user2GetTaskExpectNotFound(taskId);
        user3GetTaskExpectNotFound(taskId);

        contact = createContact(USER_2);
        toCreateDto = taskDto(contact.getContactId());
        user2CreateTask(toCreateDto);
        task = getTaskFromDb(toCreateDto);
        taskId = task.getTaskId();

        createdDto = user2GetTask(taskId);
        assertTask(task, createdDto);
        user1GetTaskExpectNotFound(taskId);
        user3GetTaskExpectNotFound(taskId);

        contact = createContact(USER_3);
        toCreateDto = taskDto(contact.getContactId());
        user3CreateTask(toCreateDto);
        task = getTaskFromDb(toCreateDto);
        taskId = task.getTaskId();

        createdDto = user3GetTask(taskId);
        assertTask(task, createdDto);
        user1GetTaskExpectNotFound(taskId);
        user2GetTaskExpectNotFound(taskId);
    }

    @Test
    void updateTask() {
        var task = createTask(USER_1);
        var taskId = task.getTaskId();
        var updateDto = user1GetTask(taskId);

        updateDto.setDescription(uuid());
        user1UpdateTask(taskId, updateDto);
        task = getTaskFromDb(updateDto);
        assertTask(task, updateDto);

        user2UpdateTaskExpectNotFound(taskId, updateDto);
        user3UpdateTaskExpectNotFound(taskId, updateDto);

        task = createTask(USER_2);
        taskId = task.getTaskId();
        updateDto = user2GetTask(taskId);

        updateDto.setDescription(uuid());
        user2UpdateTask(taskId, updateDto);
        task = getTaskFromDb(updateDto);
        assertTask(task, updateDto);

        user1UpdateTaskExpectNotFound(taskId, updateDto);
        user3UpdateTaskExpectNotFound(taskId, updateDto);

        task = createTask(USER_3);
        taskId = task.getTaskId();
        updateDto = user3GetTask(taskId);

        updateDto.setDescription(uuid());
        user3UpdateTask(taskId, updateDto);
        task = getTaskFromDb(updateDto);
        assertTask(task, updateDto);

        user1UpdateTaskExpectNotFound(taskId, updateDto);
        user2UpdateTaskExpectNotFound(taskId, updateDto);
    }

    @Test
    void deleteTask() {
        var task = createTask(USER_1);
        var taskId = task.getTaskId();
        var taskDto = user1GetTask(taskId);
        assertTask(task, taskDto);

        user2DeleteTaskExpectNotFound(taskId);
        user3DeleteTaskExpectNotFound(taskId);
        user1DeleteTask(taskId);
        user1GetTaskExpectNotFound(taskId);

        task = createTask(USER_2);
        taskId = task.getTaskId();
        taskDto = user2GetTask(taskId);
        assertTask(task, taskDto);

        user1DeleteTaskExpectNotFound(taskId);
        user3DeleteTaskExpectNotFound(taskId);
        user2DeleteTask(taskId);
        user2GetTaskExpectNotFound(taskId);

        task = createTask(USER_3);
        taskId = task.getTaskId();
        taskDto = user3GetTask(taskId);
        assertTask(task, taskDto);

        user1DeleteTaskExpectNotFound(taskId);
        user2DeleteTaskExpectNotFound(taskId);
        user3DeleteTask(taskId);
        user3GetTaskExpectNotFound(taskId);
    }
}
