package cz.prm.domain.contact.query;

import cz.prm.controllers.dto.common.PaginationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactsQuery {

   private ContactsFilter filter;

   private PaginationDto pagination;
}
