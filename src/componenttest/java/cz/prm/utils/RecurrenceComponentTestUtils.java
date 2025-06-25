package cz.prm.utils;

import static cz.prm.utils.TestUtils.randomInt;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.util.Lists.newArrayList;
import static org.dmfs.rfc5545.DateTime.now;

import cz.prm.domain.recurrence.Recurrence;
import java.util.List;
import org.dmfs.rfc5545.recur.Freq;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRule.Part;

public class RecurrenceComponentTestUtils {

    public static List<String> reminderRules() {
        return recurrences().stream().map(r -> r.getValue()).collect(toList());
    }

    public static List<Recurrence> recurrences() {
        return newArrayList(recurrence(), recurrence(), recurrence());
    }

    public static Recurrence recurrence() {
        var rrule = recurrenceRule();
        var startDate = now();
        var value = format("DTSTART:%s\nRRULE:%s", startDate, rrule);
        return recurrence(value);
    }

    public static Recurrence recurrence(String value) {
        var r = new Recurrence();
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
}
