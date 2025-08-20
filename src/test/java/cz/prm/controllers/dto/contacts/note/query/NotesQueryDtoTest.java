package cz.prm.controllers.dto.contacts.note.query;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class NotesQueryDtoTest {

    @Test
    void newInstance() {
        var query = new NotesQueryDto();
        assertThat(query.getPagination()).isNull();
    }
}