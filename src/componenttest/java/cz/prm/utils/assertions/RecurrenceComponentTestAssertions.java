package cz.prm.utils.assertions;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.recurrence.Recurrence;
import java.util.List;

public class RecurrenceComponentTestAssertions {

    public static void assertRecurrencesDto(List<Recurrence> recurrences, List<String> reminderRules) {
        var recurrenceRuleValues = recurrences.stream().map(r -> r.getValue()).collect(toList());
        assertThat(recurrenceRuleValues).isNotEmpty().isEqualTo(reminderRules);
    }
}
