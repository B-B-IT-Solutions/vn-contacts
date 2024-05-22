package cz.prm.domain.contact.query;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ContactsQueryTest {

    @Test
    void newInstance() {
        var query = new ContactsQuery();
        assertThat(query.getFilter()).isNotNull();
        assertThat(query.getPagination()).isNotNull();
    }
}