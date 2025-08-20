package cz.prm.domain.contacts.referral.query;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.contacts.referral.query.ReferralsQuery;
import org.junit.jupiter.api.Test;

class ReferralsQueryTest {

    private static final String DEFAULT_REFERRALS_SORT = "desc(creationDate)";

    @Test
    void newInstance() {
        var query = new ReferralsQuery();
        assertThat(query.getSort()).isEqualTo(DEFAULT_REFERRALS_SORT);
        assertThat(query.getPagination()).isNotNull();
        assertThat(query.getFilter()).isNotNull();
    }
}