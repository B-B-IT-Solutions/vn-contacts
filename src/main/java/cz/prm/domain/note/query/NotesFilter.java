package cz.prm.domain.note.query;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotesFilter {

    private String globalFilter;

    private List<String> categories;

    public boolean isGlobalFilter() {
        return isNotBlank(globalFilter);
    }

    public boolean isCategories() {
        return isNotEmpty(categories);
    }
}
