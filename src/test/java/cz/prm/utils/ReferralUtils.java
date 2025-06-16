package cz.prm.utils;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.CommonUtils.pagination;
import static cz.prm.utils.CommonUtils.paginationDto;
import static cz.prm.utils.CommonUtils.user;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.TestUtils.uuid;
import static java.time.Instant.now;

import cz.prm.controllers.dto.referral.ReferralDto;
import cz.prm.controllers.dto.referral.query.ReferralQueryDto;
import cz.prm.domain.referral.Referral;
import cz.prm.domain.referral.query.ReferralsQuery;
import java.util.List;

public class ReferralUtils {

    public static List<Referral> referrals() {
        return newArrayList(referral(), referral(), referral());
    }

    public static Referral referral() {
        var referral = new Referral();
        referral.setReferralId(randomLong());
        referral.setContactId(randomLong());
        referral.setName(uuid());
        referral.setDescription(uuid());
        referral.setLastEditDate(now());
        referral.setCreationDate(now());
        referral.setOwner(user());
        return referral;
    }

    public static ReferralDto referralDto() {
        var referral = new ReferralDto();
        referral.setReferralId(randomLong());
        referral.setContactId(randomLong());
        referral.setName(uuid());
        referral.setDescription(uuid());
        referral.setRecurrence(uuid());
        referral.setLastEditDate(now());
        referral.setCreationDate(now());
        return referral;
    }

    public static ReferralsQuery referralsQuery() {
        var query = new ReferralsQuery();
        query.setPagination(pagination());
        query.setSort(uuid());
        return query;
    }

    public static ReferralQueryDto referralsQueryDto() {
        var query = new ReferralQueryDto();
        query.setPagination(paginationDto());
        query.setSort(uuid());
        return query;
    }
}
