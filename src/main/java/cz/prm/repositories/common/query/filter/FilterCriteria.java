package cz.prm.repositories.common.query.filter;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.stream.Streams.of;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

import java.time.Instant;
import java.util.Date;
import java.util.regex.Pattern;
import lombok.Getter;

@Getter
public class FilterCriteria {

    private static final String VALUE_SEPARATOR = ",";
    private static final String FILTER_CRITERIA_REGEX = "^(\\w+)\\((.+)\\)$";
    private static final Pattern FILTER_CRITERIA_PATTERN = Pattern.compile(FILTER_CRITERIA_REGEX);

    private String operation;
    private String[] values;

    public FilterCriteria(String filter) {
        parse(filter);
    }

    public Instant[] getInstantValues() {
        var instants = of(values).map(v -> new Date(v).toInstant()).collect(toList());
        return instants.toArray(Instant[]::new);
    }

    private void parse(String filter) {
        if (isNotBlank(filter)) {
            var cMatcher = FILTER_CRITERIA_PATTERN.matcher(filter);
            if (cMatcher.matches()) {
                this.operation = cMatcher.group(1);
                this.values = cMatcher.group(2).split(VALUE_SEPARATOR);
                return;
            }
        }
        this.operation = null;
        this.values = nonNull(filter) ? new String[]{filter} : new String[0];
    }
}
