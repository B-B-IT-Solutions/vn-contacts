package cz.prm.repositories.common.query.filter;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.stream.Streams.of;
import static org.apache.commons.validator.GenericValidator.isDate;

import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.List;
import lombok.Getter;

@Getter
public class DateTimeFilterCriteria<T extends Temporal> extends AbstractFilterCriteria {

    protected static final String DATE_FORMAT = "dd MMM yyyy";
    private Class<T> dateClass;

    public DateTimeFilterCriteria(String filter, Class<T> dateClass) {
        super(filter);
        this.dateClass = dateClass;
    }

    public boolean hasBetweenDateValues() {
        return getDateValues().size() == 2;
    }

    public List<T> getDateValues() {
        return of(values).filter(v -> isDate(v, DATE_FORMAT, false)).map(this::fromValue).collect(toList());
    }

    private T fromValue(String value) {
        if (dateClass == Instant.class) {
            return (T) new Date(value).toInstant();
        }
        throw new IllegalArgumentException("Unrecognized date type!");
    }
}
