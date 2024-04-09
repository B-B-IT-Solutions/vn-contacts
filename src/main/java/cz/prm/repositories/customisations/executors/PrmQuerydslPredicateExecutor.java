package cz.prm.repositories.customisations.executors;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PrmQuerydslPredicateExecutor<T> extends QuerydslPredicateExecutor<T> {

   JPQLQuery<?> createQuery(Predicate predicate);
}
