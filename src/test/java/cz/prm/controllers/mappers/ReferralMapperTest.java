package cz.prm.controllers.mappers;

import static cz.prm.utils.CommonUtils.DEFAULT_PAGE_SIZE;
import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.ReferralUtils.reminder;
import static cz.prm.utils.ReferralUtils.reminderDto;
import static cz.prm.utils.ReferralUtils.reminders;
import static cz.prm.utils.ReferralUtils.remindersQueryDto;
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
        var page = page(reminders());
        var dtos = mapper.toPageDto(page);
        assertPage(page, dtos);
    }

    @Test
    void toReferralDto() {
        var reminder = reminder();
        var dto = mapper.toReferralDto(reminder);
        assertReferral(reminder, dto);
    }

    @Test
    void toReferral() {
        var dto = reminderDto();
        var reminder = mapper.toReferral(dto);
        assertReferral(reminder, dto);
    }

    @Test
    void toReferralsQuery() {
        var dto = remindersQueryDto();
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
        var dto = remindersQueryDto();
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