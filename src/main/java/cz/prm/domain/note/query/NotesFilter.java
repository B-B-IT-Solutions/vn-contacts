package cz.prm.domain.note.query;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotesFilter {

    private String globalFilter;

    private String title;

    private String categories;

    public boolean isGlobalFilter() {
        return isNotBlank(globalFilter);
    }

    public boolean isTitle() {
        return isNotBlank(title);
    }

    public boolean isCategories() {
        return isNotBlank(categories);
    }
}
