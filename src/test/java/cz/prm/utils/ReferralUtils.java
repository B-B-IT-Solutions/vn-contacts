package cz.prm.utils;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.CommonUtils.pagination;
import static cz.prm.utils.CommonUtils.paginationDto;
import static cz.prm.utils.CommonUtils.user;
import static cz.prm.utils.TestUtils.randomInt;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.TestUtils.uuid;
import static java.time.Instant.now;

import cz.prm.controllers.dto.referral.ReferralDto;
import cz.prm.controllers.dto.referral.query.ReferralQueryDto;
import cz.prm.domain.referral.Recurrence;
import cz.prm.domain.referral.Referral;
import cz.prm.domain.referral.query.ReferralsQuery;
import java.util.List;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.Freq;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRule.Part;

public class ReferralUtils {

    public static List<Referral> reminders() {
        return newArrayList(reminder(), reminder(), reminder());
    }

    public static Referral reminder() {
        var reminder = new Referral();
        reminder.setReferralId(randomLong());
        reminder.setContactId(randomLong());
        reminder.setTitle(uuid());
        reminder.setDescription(uuid());
        reminder.setRecurrence(recurrence());
        reminder.setLastEditDate(now());
        reminder.setCreationDate(now());
        reminder.setOwner(user());
        return reminder;
    }

    public static ReferralDto reminderDto() {
        var reminder = new ReferralDto();
        reminder.setReferralId(randomLong());
        reminder.setContactId(randomLong());
        reminder.setTitle(uuid());
        reminder.setDescription(uuid());
        reminder.setRecurrence(uuid());
        reminder.setLastEditDate(now());
        reminder.setCreationDate(now());
        return reminder;
    }

    public static Recurrence recurrence() {
        var rrule = recurrenceRule();
        var startDate = DateTime.now();
        var value = String.format("DTSTART:%s\nRRULE:%s", startDate, rrule);
        return recurrence(value);
    }

    public static Recurrence recurrence(String value) {
        var r = new Recurrence();
        r.setRecurrenceId(randomLong());
        r.setValue(value);
        return r;
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

    public static ReferralsQuery remindersQuery() {
        var query = new ReferralsQuery();
        query.setPagination(pagination());
        query.setSort(uuid());
        return query;
    }

    public static ReferralQueryDto remindersQueryDto() {
        var query = new ReferralQueryDto();
        query.setPagination(paginationDto());
        query.setSort(uuid());
        return query;
    }
}
