package cz.prm.domain.task.query;

import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TasksFilter {

    private String globalFilter;

    private String title;

    private Boolean completed;

    private String dueDate;

    public boolean isGlobalFilter() {
        return isNotBlank(globalFilter);
    }

    public boolean isTitle() {
        return isNotBlank(title);
    }

    public boolean isCompleted() {
        return nonNull(completed);
    }

    public boolean isDueDate() {
        return isNotBlank(dueDate);
    }
}
