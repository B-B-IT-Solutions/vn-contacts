package cz.prm.domain.task.query;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TasksFilter {

    private String globalFilter;

    private String name;

    private String status;

    private String endDate;

    public boolean isGlobalFilter() {
        return isNotBlank(globalFilter);
    }

    public boolean isName() {
        return isNotBlank(name);
    }

    public boolean isStatus() {
        return isNotBlank(status);
    }

    public boolean isEndDate() {
        return isNotBlank(endDate);
    }
}
