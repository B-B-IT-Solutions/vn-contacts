package cz.prm.domain.contacts.note.query;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotesFilter {

    private String globalFilter;

    private String text;

    private String categories;

    public boolean isGlobalFilter() {
        return isNotBlank(globalFilter);
    }

    public boolean isText() {
        return isNotBlank(text);
    }

    public boolean isCategories() {
        return isNotBlank(categories);
    }
}
