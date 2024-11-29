package cz.prm.domain.note.query;

import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class NotesFilterTest {

    @Test
    void isSearchText() {
        var filter = new NotesFilter();
        assertThat(filter.isSearchText()).isFalse();
        filter.setSearchText(null);
        assertThat(filter.isSearchText()).isFalse();
        filter.setSearchText("");
        assertThat(filter.isSearchText()).isFalse();
        filter.setSearchText(" ");
        assertThat(filter.isSearchText()).isFalse();
        filter.setSearchText(uuid());
        assertThat(filter.isSearchText()).isTrue();
    }
}