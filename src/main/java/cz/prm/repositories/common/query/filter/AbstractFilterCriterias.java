package cz.prm.repositories.common.query.filter;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public abstract class AbstractFilterCriterias<T> {

    protected static final String FILTER_CRITERIA_SEPARATOR = "\\+";
    protected List<T> criterias = new ArrayList<>();

    public AbstractFilterCriterias(String filter) {
        parse(filter);
    }

    protected void parse(String filter) {
        if (isNotBlank(filter)) {
            var fcs = filter.split(FILTER_CRITERIA_SEPARATOR);
            Stream.of(fcs).forEach(this::addFilterCriteria);
        }
    }

    protected abstract void addFilterCriteria(String filter);
}
