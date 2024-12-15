package cz.prm.repositories.task;

import static cz.prm.domain.task.querydsl.QTask.task;
import static cz.prm.repositories.common.query.PredicateCriteriaUtils.applyCriteria;
import static cz.prm.security.SecurityContextUtils.getUser;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import cz.prm.domain.task.query.TasksFilter;
import java.time.Instant;
import org.springframework.stereotype.Component;

@Component
public class TaskPredicates {

    public Predicate byTaskId(Long taskId) {
        var predicate = dataAccessPredicate();
        return predicate.and(task.taskId.eq(taskId));
    }

    public Predicate byContactId(Long contactId, TasksFilter filter) {
        var predicate = tasks(filter);
        return predicate.and(task.contactId.eq(contactId));
    }

    private BooleanExpression tasks(TasksFilter filter) {
        var predicate = dataAccessPredicate();
        return predicate.and(filterPredicates(filter));
    }

    private BooleanExpression dataAccessPredicate() {
        var user = getUser();
        return task.owner.username.eq(user.getUsername());
    }

    private BooleanBuilder filterPredicates(TasksFilter filter) {
        var predicate = new BooleanBuilder();
        if (filter.isGlobalFilter()) {
            predicate.or(task.title.containsIgnoreCase(filter.getGlobalFilter()));
            predicate.or(task.description.containsIgnoreCase(filter.getGlobalFilter()));
        }
        if (filter.isTitle()) {
            applyCriteria(predicate, task.title, filter.getTitle());
        }
        if (filter.isCompleted()) {
            predicate.and(task.completed.eq(filter.getCompleted()));
        }
        if (filter.isDueDate()) {
            applyCriteria(predicate, task.dueDate, filter.getDueDate(), Instant.class);
        }
        return predicate;
    }
}