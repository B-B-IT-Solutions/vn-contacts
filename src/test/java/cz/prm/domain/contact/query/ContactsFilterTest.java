package cz.prm.domain.contact.query;

import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ContactsFilterTest {

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
    void isMiddleName() {
        var filter = new ContactsFilter();
        assertThat(filter.isMiddleName()).isFalse();
        filter.setMiddleName(null);
        assertThat(filter.isMiddleName()).isFalse();
        filter.setMiddleName("");
        assertThat(filter.isMiddleName()).isFalse();
        filter.setMiddleName(" ");
        assertThat(filter.isMiddleName()).isFalse();
        filter.setMiddleName(uuid());
        assertThat(filter.isMiddleName()).isTrue();
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
    void isNickName() {
        var filter = new ContactsFilter();
        assertThat(filter.isNickName()).isFalse();
        filter.setNickName(null);
        assertThat(filter.isNickName()).isFalse();
        filter.setNickName("");
        assertThat(filter.isNickName()).isFalse();
        filter.setNickName(" ");
        assertThat(filter.isNickName()).isFalse();
        filter.setNickName(uuid());
        assertThat(filter.isNickName()).isTrue();
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