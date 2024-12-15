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
    void byTaskId() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var query = predicates.byTaskId(10L);
            var expectedString = format("task.owner.username = %s && task.taskId = 10", user.getUsername());
            assertThat(query).hasToString(expectedString);
        }
    }

    @Test
    void byContactIdNoFilters() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            var filter = new TasksFilter();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var query = predicates.byContactId(11L, filter);
            var expectedString = format("task.owner.username = %s && task.contactId = 11", user.getUsername());
            assertThat(query).hasToString(expectedString);
        }
    }

    @Test
    void byContactIdWithFilters() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            var filter = new TasksFilter();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var predicate = predicates.byContactId(15L, filter);
            var expectedString = format("task.owner.username = %s && task.contactId = 15", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setGlobalFilter("globalFilter_01");
            predicate = predicates.byContactId(16L, filter);
            expectedString = format("task.owner.username = %s && (containsIc(task.title,globalFilter_01) || containsIc"
                + "(task.description,globalFilter_01)) && task.contactId = 16", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setGlobalFilter("globalFilter_02");
            predicate = predicates.byContactId(17L, filter);
            expectedString = format("task.owner.username = %s && (containsIc(task.title,globalFilter_02) || containsIc"
                + "(task.description,globalFilter_02)) && task.contactId = 17", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setGlobalFilter(null);
            filter.setTitle("title_01");
            predicate = predicates.byContactId(17L, filter);
            expectedString = format("task.owner.username = %s && containsIc(task.title,title_01) && task.contactId = 17", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setCompleted(true);
            predicate = predicates.byContactId(17L, filter);
            expectedString = format("task.owner.username = %s && containsIc(task.title,title_01) && task.completed = true && task.contactId = 17",
                user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setCompleted(false);
            predicate = predicates.byContactId(17L, filter);
            expectedString = format("task.owner.username = %s && containsIc(task.title,title_01) && task.completed = false && task.contactId = 17",
                user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setTitle(null);
            filter.setCompleted(null);
            filter.setDueDate("greaterThan(15 Dec 2024)");
            predicate = predicates.byContactId(17L, filter);
            expectedString = format("task.owner.username = %s && task.dueDate > 2024-12-15 && task.contactId = 17", user.getUsername());
            assertThat(predicate).hasToString(expectedString);
        }
    }
}