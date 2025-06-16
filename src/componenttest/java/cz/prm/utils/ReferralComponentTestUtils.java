package cz.prm.utils;

import static cz.prm.domain.common.Priority.HIGH;
import static cz.prm.domain.referral.ReferralStatus.TO_DO;
import static cz.prm.utils.ComponentTestUtils.randomLong;
import static cz.prm.utils.ComponentTestUtils.uuid;
import static java.lang.String.format;
import static java.time.Instant.now;
import static org.assertj.core.util.Lists.newArrayList;

import cz.prm.controllers.dto.common.PaginationDto;
import cz.prm.controllers.dto.referral.ReferralDto;
import cz.prm.controllers.dto.referral.query.ReferralQueryDto;
import cz.prm.controllers.dto.referral.query.ReferralsFilterDto;
import cz.prm.domain.referral.Referral;
import java.util.List;

public class ReferralComponentTestUtils {

    public static List<Referral> referrals() {
        return newArrayList(referral(), referral(), referral());
    }

    public static Referral referral() {
        return referral(randomLong());
    }

    public static Referral referral(long contactId) {
        var referral = new Referral();
        referral.setContactId(contactId);
        referral.setName(format("Title-%s-End", uuid()));
        referral.setNote(format("Description%s", uuid()));
        referral.setSource(uuid());
        referral.setPriority(HIGH);
        referral.setStatus(TO_DO);
        referral.setClosedDate(now());
        referral.setStartDate(now());
        referral.setExpiredDate(now());
        return referral;
    }

    public static ReferralDto referralDto(long contactId) {
        var dto = new ReferralDto();
        dto.setContactId(contactId);
        dto.setName(uuid());
        dto.setNote(uuid());
        dto.setSource(uuid());
        dto.setPriority(HIGH);
        dto.setStatus(TO_DO);
        dto.setClosedDate(now());
        dto.setStartDate(now());
        dto.setExpiredDate(now());
        return dto;
    }

    public static ReferralQueryDto referralsQueryDto() {
        var query = new ReferralQueryDto();
        query.setPagination(new PaginationDto());
        query.setFilter(new ReferralsFilterDto());
        return query;
    }
}
