package cz.prm.utils;

import static cz.prm.utils.ComponentTestUtils.randomLong;
import static cz.prm.utils.ComponentTestUtils.uuid;
import static cz.prm.utils.TestUtils.randomInt;
import static java.lang.String.format;
import static org.assertj.core.util.Lists.newArrayList;

import cz.prm.controllers.dto.common.PaginationDto;
import cz.prm.controllers.dto.referral.ReferralDto;
import cz.prm.controllers.dto.referral.query.ReferralQueryDto;
import cz.prm.controllers.dto.referral.query.ReferralsFilterDto;
import cz.prm.domain.referral.Recurrence;
import cz.prm.domain.referral.Referral;
import java.util.List;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.Freq;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRule.Part;

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
        referral.setDescription(format("Description%s", uuid()));
        referral.setRecurrence(recurrence());
        return referral;
    }

    public static ReferralDto referralDto(long contactId) {
        var dto = new ReferralDto();
        dto.setContactId(contactId);
        dto.setName(uuid());
        dto.setDescription(uuid());
        dto.setRecurrence(uuid());
        return dto;
    }

    public static Recurrence recurrence() {
        var rrule = recurrenceRule();
        var startDate = DateTime.now();
        var value = format("DTSTART:%s\nRRULE:%s", startDate, rrule);
        return new Recurrence(value);
    }

    public static RecurrenceRule recurrenceRule() {
        try {
            var rrule = new RecurrenceRule(Freq.DAILY);
            rrule.setByPart(Part.BYMONTH, randomInt());
            rrule.setByPart(Part.BYMONTHDAY, randomInt());
            rrule.setCount(randomInt());
            return rrule;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ReferralQueryDto referralsQueryDto() {
        var query = new ReferralQueryDto();
        query.setPagination(new PaginationDto());
        query.setFilter(new ReferralsFilterDto());
        return query;
    }
}
