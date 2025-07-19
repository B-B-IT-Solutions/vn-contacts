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

    public Predicate tasks(TasksFilter filter) {
        var predicate = dataAccessPredicate();
        return predicate.and(filterPredicates(filter));
    }

    public Predicate byTaskId(Long taskId) {
        var predicate = dataAccessPredicate();
        return predicate.and(task.taskId.eq(taskId));
    }

    private BooleanExpression dataAccessPredicate() {
        var user = getUser();
        return task.owner.username.eq(user.getUsername());
    }

    private BooleanBuilder filterPredicates(TasksFilter filter) {
        var predicate = new BooleanBuilder();
        if (filter.isGlobalFilter()) {
            predicate.or(task.name.containsIgnoreCase(filter.getGlobalFilter()));
            predicate.or(task.description.containsIgnoreCase(filter.getGlobalFilter()));
        }
        if (filter.isContactId()) {
            predicate.and(task.contactId.eq(filter.getContactId()));
        }
        if (filter.isReferralId()) {
            predicate.and(task.referralId.eq(filter.getReferralId()));
        }
        if (filter.isName()) {
            applyCriteria(predicate, task.name, filter.getName());
        }
        if (filter.isStatus()) {
            applyCriteria(predicate, task.status, filter.getStatus());
        }
        if (filter.isEndDate()) {
            applyCriteria(predicate, task.endDate, filter.getEndDate(), Instant.class);
        }
        return predicate;
    }
}