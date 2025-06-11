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
    void isName() {
        var filter = new TasksFilter();
        assertThat(filter.isName()).isFalse();
        filter.setName(null);
        assertThat(filter.isName()).isFalse();
        filter.setName("");
        assertThat(filter.isName()).isFalse();
        filter.setName(" ");
        assertThat(filter.isName()).isFalse();
        filter.setName(uuid());
        assertThat(filter.isName()).isTrue();
    }

    @Test
    void isStatus() {
        var filter = new TasksFilter();
        assertThat(filter.isStatus()).isFalse();
        filter.setStatus(null);
        assertThat(filter.isStatus()).isFalse();
        filter.setStatus("");
        assertThat(filter.isStatus()).isFalse();
        filter.setStatus(" ");
        assertThat(filter.isStatus()).isFalse();
        filter.setStatus(uuid());
        assertThat(filter.isStatus()).isTrue();
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