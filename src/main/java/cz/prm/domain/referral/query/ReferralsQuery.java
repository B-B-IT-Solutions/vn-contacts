package cz.prm.domain.referral.query;

import cz.prm.domain.common.query.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReferralsQuery extends Query {

    public static final String DEFAULT_REMINDERS_SORT = "desc(creationDate)";

    private ReferralsFilter filter;

    public ReferralsQuery() {
        this.sort = DEFAULT_REMINDERS_SORT;
        this.filter = new ReferralsFilter();
    }
}
