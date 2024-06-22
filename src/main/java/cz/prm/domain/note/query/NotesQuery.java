package cz.prm.domain.note.query;

import cz.prm.domain.common.query.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
public class NotesQuery extends Query {

    public NotesQuery() {
        this.sort = "asc(displayOrder)";
    }
}
