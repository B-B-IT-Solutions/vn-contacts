package cz.prm.domain.reminder.query;

import cz.prm.domain.common.query.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RemindersQuery extends Query {

    public static final String DEFAULT_REMINDERS_SORT = "desc(creationDate)";

    public RemindersQuery() {
        this.sort = DEFAULT_REMINDERS_SORT;
    }
}
