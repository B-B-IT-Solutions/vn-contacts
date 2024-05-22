package cz.prm.controllers.dto.contact.query;

import cz.prm.controllers.dto.common.QueryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactsQueryDto extends QueryDto {

    private ContactsFilterDto filter;
}
