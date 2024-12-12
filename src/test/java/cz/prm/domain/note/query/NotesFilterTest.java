package cz.prm.domain.note.query;

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
    void isTitle() {
        var filter = new NotesFilter();
        assertThat(filter.isTitle()).isFalse();
        filter.setTitle(null);
        assertThat(filter.isTitle()).isFalse();
        filter.setTitle("");
        assertThat(filter.isTitle()).isFalse();
        filter.setTitle(" ");
        assertThat(filter.isTitle()).isFalse();
        filter.setTitle(uuid());
        assertThat(filter.isTitle()).isTrue();
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