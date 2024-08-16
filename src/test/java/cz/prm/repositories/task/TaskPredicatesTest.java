package cz.prm.repositories.task;

import static cz.prm.utils.CommonUtils.user;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.security.SecurityContextUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class TaskPredicatesTest {

    private TaskPredicates predicates;

    @BeforeEach
    void setUp() {
        predicates = new TaskPredicates();
    }

    @Test
    void tasks() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var query = predicates.tasks();
            var expectedString = format("task.owner.username = %s", user.getUsername());
            assertThat(query).hasToString(expectedString);
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

    @Test
    void byContactId() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var query = predicates.byContactId(11L);
            var expectedString = format("task.owner.username = %s && task.contactId = 11", user.getUsername());
            assertThat(query).hasToString(expectedString);
        }
    }
}