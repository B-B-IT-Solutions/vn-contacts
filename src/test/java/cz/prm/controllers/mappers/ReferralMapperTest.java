package cz.prm.controllers.mappers;

import static cz.prm.utils.CommonUtils.DEFAULT_PAGE_SIZE;
import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.ReferralUtils.referral;
import static cz.prm.utils.ReferralUtils.referralDto;
import static cz.prm.utils.ReferralUtils.referrals;
import static cz.prm.utils.ReferralUtils.referralsQueryDto;
import static cz.prm.utils.assertions.ReferralAssertions.assertPage;
import static cz.prm.utils.assertions.ReferralAssertions.assertReferral;
import static cz.prm.utils.assertions.ReferralAssertions.assertReferralsQuery;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.referral.query.ReferralQueryDto;
import cz.prm.domain.referral.query.ReferralsQuery;
import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.Test;

class ReferralMapperTest {

    public static final String DEFAULT_REMINDERS_SORT = "desc(creationDate)";

    private ReferralMapper mapper = MapperUtils.getReferralMapper();

    @Test
    void toPageDto() {
        var page = page(referrals());
        var dtos = mapper.toPageDto(page);
        assertPage(page, dtos);
    }

    @Test
    void toReferralDto() {
        var reminder = referral();
        var dto = mapper.toReferralDto(reminder);
        assertReferral(reminder, dto);
    }

    @Test
    void toReferral() {
        var dto = referralDto();
        var reminder = mapper.toReferral(dto);
        assertReferral(reminder, dto);
    }

    @Test
    void toReferralsQuery() {
        var dto = referralsQueryDto();
        var query = mapper.toReferralsQuery(dto);
        assertReferralsQuery(query, dto);
    }

    @Test
    void toNullSafeReferralsQueryNullQuery() {
        var query = mapper.toNullSafeReferralsQuery(null);
        assertNullSafeReferralQuery(query);
    }

    @Test
    void toNullSafeReferralsQueryNotNullQuery() {
        var dto = referralsQueryDto();
        var query = mapper.toNullSafeReferralsQuery(dto);
        assertReferralsQuery(query, dto);
    }

    @Test
    void toNullSafeReferralsQueryNullPagination() {
        var dto = new ReferralQueryDto();
        dto.setPagination(null);
        var query = mapper.toNullSafeReferralsQuery(dto);
        assertNullSafeReferralQuery(query);
    }

    @Test
    void afterReferralsQuery() {
        var target = new ReferralsQuery();
        target.setPagination(null);
        mapper.afterReferralsQuery(null, target);
        assertNullSafeReferralQuery(target);
    }

    private void assertNullSafeReferralQuery(ReferralsQuery query) {
        assertThat(query.getPagination()).isNotNull();
        assertThat(query.getSort()).isEqualTo(DEFAULT_REMINDERS_SORT);
        var pagination = query.getPagination();
        assertThat(pagination.getPageNumber()).isZero();
        assertThat(pagination.getPageSize()).isEqualTo(DEFAULT_PAGE_SIZE);
    }
}