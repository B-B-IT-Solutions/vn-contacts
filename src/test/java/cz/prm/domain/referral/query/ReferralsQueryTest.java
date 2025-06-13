package cz.prm.domain.referral.query;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReferralsQueryTest {

    private static final String DEFAULT_REMINDERS_SORT = "desc(creationDate)";

    @Test
    void newInstance() {
        var query = new ReferralsQuery();
        assertThat(query.getSort()).isEqualTo(DEFAULT_REMINDERS_SORT);
        assertThat(query.getPagination()).isNotNull();
    }
}