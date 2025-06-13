package cz.prm.utils;

import static cz.prm.utils.ComponentTestUtils.randomLong;
import static cz.prm.utils.ComponentTestUtils.uuid;
import static cz.prm.utils.TestUtils.randomInt;
import static java.lang.String.format;
import static org.assertj.core.util.Lists.newArrayList;

import cz.prm.controllers.dto.common.PaginationDto;
import cz.prm.controllers.dto.referral.ReferralDto;
import cz.prm.controllers.dto.referral.query.ReferralQueryDto;
import cz.prm.domain.referral.Recurrence;
import cz.prm.domain.referral.Referral;
import java.util.List;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.Freq;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRule.Part;

public class ReminderComponentTestUtils {

    public static List<Referral> reminders() {
        return newArrayList(reminder(), reminder(), reminder());
    }

    public static Referral reminder() {
        return reminder(randomLong());
    }

    public static Referral reminder(long contactId) {
        var reminder = new Referral();
        reminder.setContactId(contactId);
        reminder.setTitle(format("Title%s", uuid()));
        reminder.setDescription(format("Description%s", uuid()));
        reminder.setRecurrence(recurrence());
        return reminder;
    }

    public static ReferralDto reminderDto(long contactId) {
        var dto = new ReferralDto();
        dto.setContactId(contactId);
        dto.setTitle(uuid());
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

    public static ReferralQueryDto remindersQueryDto() {
        var query = new ReferralQueryDto();
        query.setPagination(new PaginationDto());
        return query;
    }
}
