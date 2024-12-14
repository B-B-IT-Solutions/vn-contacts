package cz.prm.domain.task.query;

import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TasksFilterTest {

    @Test
    void isGlobalFilter() {
        var filter = new TasksFilter();
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
        var filter = new TasksFilter();
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
    void isCompleted() {
        var filter = new TasksFilter();
        assertThat(filter.isCompleted()).isFalse();
        filter.setCompleted(null);
        assertThat(filter.isCompleted()).isFalse();
        filter.setCompleted(false);
        assertThat(filter.isCompleted()).isTrue();
        filter.setCompleted(true);
        assertThat(filter.isCompleted()).isTrue();
    }

    @Test
    void isDueDate() {
        var filter = new TasksFilter();
        assertThat(filter.isDueDate()).isFalse();
        filter.setDueDate(null);
        assertThat(filter.isDueDate()).isFalse();
        filter.setDueDate("");
        assertThat(filter.isDueDate()).isFalse();
        filter.setDueDate(" ");
        assertThat(filter.isDueDate()).isFalse();
        filter.setDueDate(uuid());
        assertThat(filter.isDueDate()).isTrue();
    }
}