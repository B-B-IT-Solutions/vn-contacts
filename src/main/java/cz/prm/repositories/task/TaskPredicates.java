package cz.prm.repositories.task;

import static cz.prm.domain.task.querydsl.QTask.task;
import static cz.prm.security.SecurityContextUtils.getUser;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Component;

@Component
public class TaskPredicates {

    public Predicate tasks() {
        return dataAccessPredicate();
    }

    public Predicate byTaskId(Long taskId) {
        var predicate = dataAccessPredicate();
        return predicate.and(task.taskId.eq(taskId));
    }

    public Predicate byContactId(Long contactId) {
        var predicate = dataAccessPredicate();
        return predicate.and(task.contactId.eq(contactId));
    }

    private BooleanExpression dataAccessPredicate() {
        var user = getUser();
        return task.owner.username.eq(user.getUsername());
    }
}