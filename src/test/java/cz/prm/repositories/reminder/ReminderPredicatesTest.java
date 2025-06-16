package cz.prm.repositories.reminder;

import static cz.prm.utils.TestUtils.randomLong;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReminderPredicatesTest {

    private ReminderPredicates predicates;

    @BeforeEach
    void setUp() {
        predicates = new ReminderPredicates();
    }

    @Test
    void reminders() {
        var query = predicates.reminders();
        var expectedString = "com.querydsl.core.BooleanBuilder@0";
        assertThat(query).hasToString(expectedString);
    }

    @Test
    void byReminderId() {
        var reminderId = randomLong();
        var query = predicates.byReminderId(reminderId);
        var expectedString = format("reminder.reminderId = %s", reminderId);
        assertThat(query).hasToString(expectedString);
    }
}