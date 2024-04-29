package cz.prm.controllers.dto.contact.query;

import cz.prm.controllers.dto.common.PaginationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactsQueryDto {

   private ContactsFilterDto filter;

   private PaginationDto pagination;

}
