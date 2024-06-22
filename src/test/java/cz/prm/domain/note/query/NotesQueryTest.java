package cz.prm.domain.note.query;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class NotesQueryTest {

    private static final String DEFAULT_NOTES_SORT = "asc(displayOrder)";

    @Test
    void newInstance() {
        var query = new NotesQuery();
        assertThat(query.getSort()).isEqualTo(DEFAULT_NOTES_SORT);
        assertThat(query.getPagination()).isNotNull();
    }
}