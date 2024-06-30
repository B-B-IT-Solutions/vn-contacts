package cz.prm.domain.contact.query;

import cz.prm.domain.common.query.Query;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class ContactsQuery extends Query {

    public static final String DEFAULT_CONTACTS_SORT = "desc(firstName)";
    private ContactsFilter filter;

    public ContactsQuery() {
        this.sort = DEFAULT_CONTACTS_SORT;
        this.filter = new ContactsFilter();
    }
}
