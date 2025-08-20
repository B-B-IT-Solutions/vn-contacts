package cz.prm.domain.contacts.note.query;

import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class NotesFilterTest {

    @Test
    void isGlobalFilter() {
        var filter = new NotesFilter();
        assertThat(filter.isGlobalFilter()).isFalse();
        filter.setGlobalFilter(null);
        assertThat(filter.isGlobalFilter()).isFalse();
        filter.setGlobalFilter("");
        assertThat(filter.isGlobalFilter()).isFalse();
        filter.setGlobalFilter(" ");
        assertThat(filter.isGlobalFilter()).isFalse();
        filter.setGlobalFilter(uuid());
        assertThat(filter.isGlobalFilter()).isTrue();
    }

    @Test
    void isText() {
        var filter = new NotesFilter();
        assertThat(filter.isText()).isFalse();
        filter.setText(null);
        assertThat(filter.isText()).isFalse();
        filter.setText("");
        assertThat(filter.isText()).isFalse();
        filter.setText(" ");
        assertThat(filter.isText()).isFalse();
        filter.setText(uuid());
        assertThat(filter.isText()).isTrue();
    }

    @Test
    void isCategories() {
        var filter = new NotesFilter();
        assertThat(filter.isCategories()).isFalse();
        filter.setCategories(null);
        assertThat(filter.isCategories()).isFalse();
        filter.setCategories("");
        assertThat(filter.isCategories()).isFalse();
        filter.setCategories(" ");
        assertThat(filter.isCategories()).isFalse();
        filter.setCategories(uuid());
        assertThat(filter.isCategories()).isTrue();
    }
}