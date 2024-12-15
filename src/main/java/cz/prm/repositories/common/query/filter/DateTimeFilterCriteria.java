package cz.prm.repositories.common.query.filter;

import static java.time.ZoneId.systemDefault;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.stream.Streams.of;
import static org.apache.commons.validator.GenericValidator.isDate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import lombok.Getter;

@Getter
public class DateTimeFilterCriteria extends AbstractFilterCriteria {

    private static final String DATE_FORMAT = "dd MMM yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private Class<?> filterType;

    public DateTimeFilterCriteria(String filter, Class<?> filterType) {
        super(filter);
        this.filterType = filterType;
    }

    public boolean hasBetweenDateValues() {
        return getDateValues().size() == 2;
    }

    public List<Comparable> getDateValues() {
        return of(values).filter(v -> isDate(v, DATE_FORMAT, false)).map(this::fromValue).collect(toList());
    }

    private Comparable fromValue(String value) {
        var date = new Date(value);
        if (filterType == Instant.class) {
            return date.toInstant();
        } else if (filterType == LocalDate.class) {
            return LocalDate.ofInstant(date.toInstant(), systemDefault());
        } else if (filterType == LocalDateTime.class) {
            return LocalDateTime.ofInstant(date.toInstant(), systemDefault());
        }
        throw new IllegalArgumentException("Unrecognized date type!");
    }
}
