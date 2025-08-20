package cz.prm.domain.contacts.task.query;

import static cz.prm.utils.TestUtils.randomLong;
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
    void isContactId() {
        var filter = new TasksFilter();
        assertThat(filter.isContactId()).isFalse();
        filter.setContactId(null);
        assertThat(filter.isContactId()).isFalse();
        filter.setContactId(randomLong());
        assertThat(filter.isContactId()).isTrue();
    }

    @Test
    void isReferralId() {
        var filter = new TasksFilter();
        assertThat(filter.isReferralId()).isFalse();
        filter.setReferralId(null);
        assertThat(filter.isReferralId()).isFalse();
        filter.setReferralId(randomLong());
        assertThat(filter.isReferralId()).isTrue();
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
    void isEndDate() {
        var filter = new TasksFilter();
        assertThat(filter.isEndDate()).isFalse();
        filter.setEndDate(null);
        assertThat(filter.isEndDate()).isFalse();
        filter.setEndDate("");
        assertThat(filter.isEndDate()).isFalse();
        filter.setEndDate(" ");
        assertThat(filter.isEndDate()).isFalse();
        filter.setEndDate(uuid());
        assertThat(filter.isEndDate()).isTrue();
    }
}