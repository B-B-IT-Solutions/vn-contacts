package cz.prm.domain.contact.query;

import cz.prm.domain.common.query.Query;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactsQuery extends Query {

    private ContactsFilter filter;

    public ContactsQuery() {
        this.filter = new ContactsFilter();
    }
}
