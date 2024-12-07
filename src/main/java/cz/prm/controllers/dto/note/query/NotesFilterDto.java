package cz.prm.controllers.dto.note.query;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotesFilterDto {

    private String globalFilter;

    private List<String> categories;
}
