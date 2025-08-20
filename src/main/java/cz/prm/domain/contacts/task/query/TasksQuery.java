package cz.prm.domain.contacts.task.query;

import cz.prm.domain.common.query.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TasksQuery extends Query {

    public static final String DEFAULT_TASKS_SORT = "desc(creationDate)";

    private TasksFilter filter;

    public TasksQuery() {
        this.sort = DEFAULT_TASKS_SORT;
        this.filter = new TasksFilter();
    }
}
