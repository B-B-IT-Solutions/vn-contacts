package cz.prm.domain.contact.query;

import cz.prm.domain.common.Pagination;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactsQuery {

   private ContactsFilter filter;

   private Pagination pagination;

   public ContactsQuery() {
      this.filter = new ContactsFilter();
      this.pagination = new Pagination();
   }
}
