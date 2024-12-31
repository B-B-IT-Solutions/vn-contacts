package cz.prm.controllers.dto.contact.query;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactsFilterDto {

    private String firstName;

    private String middleName;

    private String lastName;

    private String nickName;

    private String labels;

    private String industries;
}
