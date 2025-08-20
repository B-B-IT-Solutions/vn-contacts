package cz.prm.utils.assertions;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.contacts.recurrence.Recurrence;
import java.util.List;
import java.util.Objects;

public class RecurrenceAssertions {

    public static void assertRecurrencesDto(List<Recurrence> recurrences, List<String> reminderRules) {
        var recurrenceRuleValues = recurrences.stream().map(r -> r.getValue()).collect(toList());
        assertThat(recurrenceRuleValues).isNotEmpty().isEqualTo(reminderRules);
    }

    public static void assertRecurrences(List<Recurrence> recurrences1, List<Recurrence> recurrences2) {
        assertThat(recurrences1).isNotEmpty().hasSameSizeAs(recurrences2);
        recurrences1.forEach(c1 -> {
            var c2 = recurrences2.stream().filter(u -> Objects.equals(c1.getRecurrenceId(), u.getRecurrenceId())).findFirst().get();
            assertRecurrence(c1, c2);
        });
    }

    public static void assertRecurrence(Recurrence recurrence1, Recurrence recurrence2) {
        assertThat(recurrence1.getRecurrenceId()).isEqualTo(recurrence2.getRecurrenceId());
        assertThat(recurrence1.getValue()).isEqualTo(recurrence2.getValue());
        assertThat(recurrence1.getStartDate()).isEqualTo(recurrence2.getStartDate());
    }
}
