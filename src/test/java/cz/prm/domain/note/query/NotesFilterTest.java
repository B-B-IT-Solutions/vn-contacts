package cz.prm.domain.note.query;

import static com.google.common.collect.Lists.newArrayList;
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

    @Test
    void isCategories() {
        var filter = new NotesFilter();
        assertThat(filter.isCategories()).isFalse();
        filter.setCategories(null);
        assertThat(filter.isCategories()).isFalse();
        filter.setCategories(newArrayList());
        assertThat(filter.isCategories()).isFalse();
        filter.setCategories(newArrayList(" "));
        assertThat(filter.isCategories()).isTrue();
        filter.setCategories(newArrayList(uuid()));
        assertThat(filter.isCategories()).isTrue();
        filter.setCategories(newArrayList(uuid(), uuid(), uuid()));
        assertThat(filter.isCategories()).isTrue();
    }
}