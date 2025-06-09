package cz.prm.repositories.common.query.filter;

import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

import java.util.regex.Pattern;
import lombok.Getter;

@Getter
public abstract class AbstractFilterCriteria {

    protected static final String VALUE_SEPARATOR = ",";
    protected static final String FILTER_CRITERIA_REGEX = "^(\\w+)\\((.+)\\)$";
    protected static final Pattern FILTER_CRITERIA_PATTERN = Pattern.compile(FILTER_CRITERIA_REGEX);
    protected static final Pattern VALUE_PATTERN = Pattern.compile(VALUE_SEPARATOR);

    protected String operation;
    protected String[] values;

    public AbstractFilterCriteria(String filter) {
        parse(filter);
    }

    protected void parse(String filter) {
        if (isNotBlank(filter)) {
            var cMatcher = FILTER_CRITERIA_PATTERN.matcher(filter);
            if (cMatcher.matches()) {
                this.operation = cMatcher.group(1);
                this.values = cMatcher.group(2).split(VALUE_SEPARATOR);
                return;
            }

            if (filter.contains(VALUE_SEPARATOR)) {
                this.operation = null;
                this.values = filter.split(VALUE_SEPARATOR);
                return;
            }
        }
        this.operation = null;
        this.values = nonNull(filter) ? new String[]{filter} : new String[0];
    }
}
