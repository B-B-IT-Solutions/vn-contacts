package cz.prm.domain.contact.query;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ContactsQueryTest {

    private static final String DEFAULT_CONTACTS_SORT = "desc(firstName)";

    @Test
    void newInstance() {
        var query = new ContactsQuery();
        assertThat(query.getSort()).isEqualTo(DEFAULT_CONTACTS_SORT);
        assertThat(query.getFilter()).isNotNull();
        assertThat(query.getPagination()).isNotNull();
    }
}