package cz.prm.controllers.mapper.contacts;

import static cz.prm.utils.CommonUtils.DEFAULT_PAGE_SIZE;
import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.assertions.contacts.ReferralAssertions.assertPage;
import static cz.prm.utils.assertions.contacts.ReferralAssertions.assertReferral;
import static cz.prm.utils.assertions.contacts.ReferralAssertions.assertReferralsQuery;
import static cz.prm.utils.data.contacts.ReferralUtils.referral;
import static cz.prm.utils.data.contacts.ReferralUtils.referralDto;
import static cz.prm.utils.data.contacts.ReferralUtils.referrals;
import static cz.prm.utils.data.contacts.ReferralUtils.referralsQueryDto;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.contacts.referral.query.ReferralQueryDto;
import cz.prm.domain.contacts.referral.query.ReferralsQuery;
import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.Test;

class ReferralMapperTest {

    public static final String DEFAULT_REFERRALS_SORT = "desc(creationDate)";

    private ReferralMapper mapper = MapperUtils.getReferralMapper();

    @Test
    void toPageDto() {
        var page = page(referrals());
        var dtos = mapper.toPageDto(page);
        assertPage(page, dtos);
    }

    @Test
    void toReferralDto() {
        var referral = referral();
        var dto = mapper.toReferralDto(referral);
        assertReferral(referral, dto);
    }

    @Test
    void toReferral() {
        var dto = referralDto();
        var referral = mapper.toReferral(dto);
        assertReferral(referral, dto);
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
        assertThat(query.getFilter()).isNotNull();
        assertThat(query.getSort()).isEqualTo(DEFAULT_REFERRALS_SORT);
        var pagination = query.getPagination();
        assertThat(pagination.getPageNumber()).isZero();
        assertThat(pagination.getPageSize()).isEqualTo(DEFAULT_PAGE_SIZE);
    }
}