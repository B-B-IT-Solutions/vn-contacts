package cz.prm.repositories.common.query.filter;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.stream.Streams.of;

import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.Getter;

@Getter
public class EnumFilterCriteria extends AbstractFilterCriteria {

    private static final String DATE_FORMAT = "dd MMM yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private Class<?> filterType;

    public EnumFilterCriteria(String filter, Class<?> filterType) {
        super(filter);
        this.filterType = filterType;
    }

    public List<Comparable> getEnumValues() {
        return of(values).map(this::toOrdinal).collect(toList());
    }

    private Integer toOrdinal(String filter) {
        try {
            var constant = filterType.getDeclaredField(filter).get(null);
            var clazz = constant.getClass();
            var ordinalMethod = clazz.getMethod("ordinal");
            return (Integer) ordinalMethod.invoke(constant);
        } catch (NoSuchMethodException | NoSuchFieldException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalArgumentException("Unrecognized enum type!", e);
        }
    }
}
