package cz.prm.domain.note.query;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class NotesQueryTest {

    @Test
    void newInstance() {
        var query = new NotesQuery();
        assertThat(query.getPagination()).isNotNull();
    }
}