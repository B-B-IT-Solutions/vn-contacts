package cz.prm.domain.task.query;

import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

import cz.prm.domain.task.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TasksFilter {

    private String globalFilter;

    private String name;

    private Status status;

    private String dueDate;

    public boolean isGlobalFilter() {
        return isNotBlank(globalFilter);
    }

    public boolean isName() {
        return isNotBlank(name);
    }

    public boolean isStatus() {
        return nonNull(status);
    }

    public boolean isDueDate() {
        return isNotBlank(dueDate);
    }
}
