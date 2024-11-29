package cz.prm.domain.note.query;

import cz.prm.domain.common.query.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NotesQuery extends Query {

    public static final String DEFAULT_NOTES_SORT = "desc(creationDate)";

    private NotesFilter filter;

    public NotesQuery() {
        this.sort = DEFAULT_NOTES_SORT;
        this.filter = new NotesFilter();
    }
}
