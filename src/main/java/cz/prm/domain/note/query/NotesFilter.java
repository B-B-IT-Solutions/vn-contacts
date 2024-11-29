package cz.prm.domain.note.query;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotesFilter {

    private String searchText;

    public boolean isSearchText() {
        return isNotBlank(searchText);
    }
}
