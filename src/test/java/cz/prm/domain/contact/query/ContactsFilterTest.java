package cz.prm.domain.contact.query;

import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ContactsFilterTest {

    @Test
    void isGlobalFilter() {
        var filter = new ContactsFilter();
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
    void isFirstName() {
        var filter = new ContactsFilter();
        assertThat(filter.isFirstName()).isFalse();
        filter.setFirstName(null);
        assertThat(filter.isFirstName()).isFalse();
        filter.setFirstName("");
        assertThat(filter.isFirstName()).isFalse();
        filter.setFirstName(" ");
        assertThat(filter.isFirstName()).isFalse();
        filter.setFirstName(uuid());
        assertThat(filter.isFirstName()).isTrue();
    }

    @Test
    void isLastName() {
        var filter = new ContactsFilter();
        assertThat(filter.isLastName()).isFalse();
        filter.setLastName(null);
        assertThat(filter.isLastName()).isFalse();
        filter.setLastName("");
        assertThat(filter.isLastName()).isFalse();
        filter.setLastName(" ");
        assertThat(filter.isLastName()).isFalse();
        filter.setLastName(uuid());
        assertThat(filter.isLastName()).isTrue();
    }

    @Test
    void isStatus() {
        var filter = new ContactsFilter();
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
    void isSource() {
        var filter = new ContactsFilter();
        assertThat(filter.isSource()).isFalse();
        filter.setSource(null);
        assertThat(filter.isSource()).isFalse();
        filter.setSource("");
        assertThat(filter.isSource()).isFalse();
        filter.setSource(" ");
        assertThat(filter.isSource()).isFalse();
        filter.setSource(uuid());
        assertThat(filter.isSource()).isTrue();
    }

    @Test
    void isLabels() {
        var filter = new ContactsFilter();
        assertThat(filter.isLabels()).isFalse();
        filter.setLabels(null);
        assertThat(filter.isLabels()).isFalse();
        filter.setLabels("");
        assertThat(filter.isLabels()).isFalse();
        filter.setLabels(" ");
        assertThat(filter.isLabels()).isFalse();
        filter.setLabels(uuid());
        assertThat(filter.isLabels()).isTrue();
    }

    @Test
    void isIndustries() {
        var filter = new ContactsFilter();
        assertThat(filter.isIndustries()).isFalse();
        filter.setIndustries(null);
        assertThat(filter.isIndustries()).isFalse();
        filter.setIndustries("");
        assertThat(filter.isIndustries()).isFalse();
        filter.setIndustries(" ");
        assertThat(filter.isIndustries()).isFalse();
        filter.setIndustries(uuid());
        assertThat(filter.isIndustries()).isTrue();
    }
}