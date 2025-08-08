package cz.prm.controllers.dto.contact.query;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactsFilterDto {

    private String globalFilter;

    private String firstName;

    private String lastName;

    private String status;

    private String source;

    private String labels;

    private String industries;
}
