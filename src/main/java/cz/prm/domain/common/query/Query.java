package cz.prm.domain.common.query;

import static org.apache.logging.log4j.util.Strings.isNotBlank;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;
import static org.springframework.data.domain.Sort.Direction.fromOptionalString;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.data.domain.Sort.unsorted;

import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
public class Query {

    private static final String PROPERTIES_SEPARATOR = ",";
    private static final String SORT_REGEX = "^(\\w+)\\((.+)\\)$";
    private static final Pattern SORT_PATTERN = Pattern.compile(SORT_REGEX);

    protected String sort;

    protected Pagination pagination;

    public Query() {
        this.pagination = new Pagination();
    }

    public Sort resolveSort() {
        if (isNotBlank(sort)) {
            var cMatcher = SORT_PATTERN.matcher(sort);
            if (cMatcher.matches()) {
                var optional = fromOptionalString(cMatcher.group(1));
                if (optional.isPresent()) {
                    var direction = optional.get();
                    var properties = cMatcher.group(2).split(PROPERTIES_SEPARATOR);
                    return by(direction, properties);
                }
            }
        }
        return isNotEmpty(sort) ? by(sort) : unsorted();
    }
}
