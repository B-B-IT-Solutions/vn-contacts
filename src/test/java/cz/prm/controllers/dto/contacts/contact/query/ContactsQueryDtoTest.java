package cz.prm.controllers.dto.contacts.contact.query;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ContactsQueryDtoTest {

    @Test
    void newInstance() {
        var query = new ContactsQueryDto();
        assertThat(query.getFilter()).isNull();
        assertThat(query.getPagination()).isNull();
    }
}