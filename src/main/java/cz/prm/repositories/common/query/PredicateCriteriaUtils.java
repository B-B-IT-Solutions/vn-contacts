package cz.prm.repositories.common.query;

import static cz.prm.repositories.common.query.filter.FilterOperation.CONTAINS;
import static cz.prm.repositories.common.query.filter.FilterOperation.ENDS_WITH;
import static cz.prm.repositories.common.query.filter.FilterOperation.NOT_CONTAINS;
import static cz.prm.repositories.common.query.filter.FilterOperation.STARTS_WITH;
import static java.util.stream.Stream.of;
import static lombok.AccessLevel.PRIVATE;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringPath;
import cz.prm.repositories.common.query.filter.FilterCriteria;
import cz.prm.repositories.common.query.filter.FilterCriterias;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class PredicateCriteriaUtils {

    public static Predicate applyCriteria(BooleanBuilder predicate, StringPath field, String filter) {
        var fcs = new FilterCriterias(filter);
        fcs.getCriterias().forEach(fc -> predicate.and(apply(field, fc)));
        return predicate;
    }

    private static Predicate apply(StringPath field, FilterCriteria fc) {
        var predicate = new BooleanBuilder();
        if (CONTAINS.isOperation(fc)) {
            of(fc.getValues()).forEach(value -> predicate.or(field.containsIgnoreCase(value)));
        } else if (NOT_CONTAINS.isOperation(fc)) {
            of(fc.getValues()).forEach(value -> predicate.andNot(field.containsIgnoreCase(value)));
        } else if (STARTS_WITH.isOperation(fc)) {
            of(fc.getValues()).forEach(value -> predicate.or(field.startsWithIgnoreCase(value)));
        } else if (ENDS_WITH.isOperation(fc)) {
            of(fc.getValues()).forEach(value -> predicate.or(field.endsWithIgnoreCase(value)));
        } else {
            of(fc.getValues()).forEach(value -> predicate.or(field.containsIgnoreCase(value)));
        }
        return predicate;
    }
}
