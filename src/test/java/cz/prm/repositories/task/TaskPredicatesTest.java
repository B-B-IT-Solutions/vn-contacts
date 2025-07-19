package cz.prm.repositories.task;

import static cz.prm.utils.CommonUtils.user;
import static cz.prm.utils.TimeUtils.useMockTimeZone;
import static cz.prm.utils.TimeUtils.useSystemDefaultTimeZone;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.task.query.TasksFilter;
import cz.prm.security.SecurityContextUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class TaskPredicatesTest {

    private TaskPredicates predicates;

    @BeforeEach
    void setUp() {
        useMockTimeZone();
        predicates = new TaskPredicates();
    }

    @AfterEach
    void tearDown() {
        useSystemDefaultTimeZone();
    }

    @Test
    void tasksNoFilters() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            var filter = new TasksFilter();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var query = predicates.tasks(filter);
            var expectedString = format("task.owner.username = %s", user.getUsername());
            assertThat(query).hasToString(expectedString);
        }
    }

    @Test
    void tasksWithFilters() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            var filter = new TasksFilter();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var predicate = predicates.tasks(filter);
            var expectedString = format("task.owner.username = %s", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setGlobalFilter("globalFilter_01");
            predicate = predicates.tasks(filter);
            expectedString = format(
                "task.owner.username = %s && (containsIc(task.name,globalFilter_01) || containsIc" + "(task.description,globalFilter_01))",
                user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setGlobalFilter("globalFilter_02");
            predicate = predicates.tasks(filter);
            expectedString = format(
                "task.owner.username = %s && (containsIc(task.name,globalFilter_02) || containsIc" + "(task.description,globalFilter_02))",
                user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setGlobalFilter(null);
            filter.setContactId(11L);
            predicate = predicates.tasks(filter);
            expectedString = format("task.owner.username = %s && task.contactId = 11", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setContactId(null);
            filter.setReferralId(15L);
            predicate = predicates.tasks(filter);
            expectedString = format("task.owner.username = %s && task.referralId = 15", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setReferralId(null);
            filter.setName("title_01");
            predicate = predicates.tasks(filter);
            expectedString = format("task.owner.username = %s && containsIc(task.name,title_01)", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setStatus("TO_DO");
            predicate = predicates.tasks(filter);
            expectedString = format("task.owner.username = %s && containsIc(task.name,title_01) && task.status = TO_DO", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setStatus("IN_PROGRESS");
            predicate = predicates.tasks(filter);
            expectedString = format("task.owner.username = %s && containsIc(task.name,title_01) && task.status = IN_PROGRESS", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setName(null);
            filter.setStatus(null);
            filter.setEndDate("greaterThan(15 Dec 2024)");
            predicate = predicates.tasks(filter);
            expectedString = format("task.owner.username = %s && task.endDate > 2024-12-14T23:00:00Z", user.getUsername());
            assertThat(predicate).hasToString(expectedString);
        }
    }

    @Test
    void contactTasksNoFilters() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            var filter = new TasksFilter();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var query = predicates.contactTasks(11L, filter);
            var expectedString = format("task.owner.username = %s && task.contactId = 11", user.getUsername());
            assertThat(query).hasToString(expectedString);
        }
    }

    @Test
    void contactTasksWithFilters() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            var filter = new TasksFilter();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var predicate = predicates.contactTasks(15L, filter);
            var expectedString = format("task.owner.username = %s && task.contactId = 15", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setGlobalFilter("globalFilter_01");
            predicate = predicates.contactTasks(16L, filter);
            expectedString = format("task.owner.username = %s && (containsIc(task.name,globalFilter_01) || containsIc"
                + "(task.description,globalFilter_01)) && task.contactId = 16", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setGlobalFilter("globalFilter_02");
            predicate = predicates.contactTasks(17L, filter);
            expectedString = format("task.owner.username = %s && (containsIc(task.name,globalFilter_02) || containsIc"
                + "(task.description,globalFilter_02)) && task.contactId = 17", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setGlobalFilter(null);
            filter.setName("title_01");
            predicate = predicates.contactTasks(17L, filter);
            expectedString = format("task.owner.username = %s && containsIc(task.name,title_01) && task.contactId = 17", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setStatus("TO_DO");
            predicate = predicates.contactTasks(17L, filter);
            expectedString = format("task.owner.username = %s && containsIc(task.name,title_01) && task.status = TO_DO && task.contactId = 17",
                user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setStatus("IN_PROGRESS");
            predicate = predicates.contactTasks(17L, filter);
            expectedString = format("task.owner.username = %s && containsIc(task.name,title_01) && task.status = IN_PROGRESS && task.contactId = 17",
                user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setName(null);
            filter.setStatus(null);
            filter.setEndDate("greaterThan(15 Dec 2024)");
            predicate = predicates.contactTasks(17L, filter);
            expectedString = format("task.owner.username = %s && task.endDate > 2024-12-14T23:00:00Z && task.contactId = 17", user.getUsername());
            assertThat(predicate).hasToString(expectedString);
        }
    }

    @Test
    void byTaskId() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var query = predicates.byTaskId(10L);
            var expectedString = format("task.owner.username = %s && task.taskId = 10", user.getUsername());
            assertThat(query).hasToString(expectedString);
        }
    }
}