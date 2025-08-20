package cz.prm.domain.contacts.task.query;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.contacts.task.query.TasksQuery;
import org.junit.jupiter.api.Test;

class TasksQueryTest {

    private static final String DEFAULT_TASKS_SORT = "desc(creationDate)";

    @Test
    void newInstance() {
        var query = new TasksQuery();
        assertThat(query.getSort()).isEqualTo(DEFAULT_TASKS_SORT);
        assertThat(query.getPagination()).isNotNull();
        assertThat(query.getFilter()).isNotNull();
    }
}