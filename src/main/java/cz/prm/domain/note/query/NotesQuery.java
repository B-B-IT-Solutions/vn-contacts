package cz.prm.domain.note.query;

import cz.prm.domain.common.query.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
public class NotesQuery extends Query {

    public static final String DEFAULT_NOTES_SORT = "desc(creationDate)";

    public NotesQuery() {
        this.sort = DEFAULT_NOTES_SORT;
    }
}
