package cz.prm.controllers.dto.contacts.contact.query;

import cz.prm.controllers.dto.common.QueryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ContactsQueryDto extends QueryDto {

    private ContactsFilterDto filter;
}
