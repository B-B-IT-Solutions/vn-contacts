package cz.prm.utils.data.contacts;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.domain.common.Priority.HIGH;
import static cz.prm.domain.contacts.referral.ReferralStatus.TO_DO;
import static cz.prm.utils.CommonUtils.pagination;
import static cz.prm.utils.CommonUtils.paginationDto;
import static cz.prm.utils.CommonUtils.user;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.TestUtils.uuid;
import static java.time.Instant.now;

import cz.prm.controllers.dto.contacts.referral.ReferralDto;
import cz.prm.controllers.dto.contacts.referral.query.ReferralQueryDto;
import cz.prm.domain.contacts.referral.Referral;
import cz.prm.domain.contacts.referral.query.ReferralsQuery;
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
        referral.setNote(uuid());
        referral.setSource(uuid());
        referral.setPriority(HIGH);
        referral.setStatus(TO_DO);
        referral.setClosedDate(now());
        referral.setStartDate(now());
        referral.setExpiredDate(now());
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
        referral.setNote(uuid());
        referral.setSource(uuid());
        referral.setPriority(HIGH);
        referral.setStatus(TO_DO);
        referral.setClosedDate(now());
        referral.setStartDate(now());
        referral.setExpiredDate(now());
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
