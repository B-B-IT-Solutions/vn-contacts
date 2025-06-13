package cz.prm.domain.referral.query;

import cz.prm.domain.common.query.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReferralsQuery extends Query {

    public static final String DEFAULT_REFERRALS_SORT = "desc(creationDate)";

    private ReferralsFilter filter;

    public ReferralsQuery() {
        this.sort = DEFAULT_REFERRALS_SORT;
        this.filter = new ReferralsFilter();
    }
}
