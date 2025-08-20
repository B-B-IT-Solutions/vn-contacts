package cz.prm.domain.referral.query;

import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.contacts.referral.query.ReferralsFilter;
import org.junit.jupiter.api.Test;

class ReferralsFilterTest {

    @Test
    void isGlobalFilter() {
        var filter = new ReferralsFilter();
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
        var filter = new ReferralsFilter();
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
        var filter = new ReferralsFilter();
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
}