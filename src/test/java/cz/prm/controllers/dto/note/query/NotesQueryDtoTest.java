package cz.prm.controllers.dto.note.query;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.contacts.note.query.NotesQueryDto;
import org.junit.jupiter.api.Test;

class NotesQueryDtoTest {

    @Test
    void newInstance() {
        var query = new NotesQueryDto();
        assertThat(query.getPagination()).isNull();
    }
}