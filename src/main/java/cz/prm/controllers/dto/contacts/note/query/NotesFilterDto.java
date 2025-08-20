package cz.prm.controllers.dto.contacts.note.query;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotesFilterDto {

    private String globalFilter;

    private String text;

    private String categories;
}
