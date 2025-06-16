package cz.prm.utils;

import static java.time.LocalTime.MIN;
import static java.time.ZoneId.systemDefault;
import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.Instant;
import java.time.LocalDateTime;
import org.assertj.core.data.TemporalOffset;
import org.assertj.core.data.TemporalUnitWithinOffset;

public class TimeComponentTestUtils {

    public static TemporalOffset ONE_SECOND_OFFSET = new TemporalUnitWithinOffset(1, SECONDS);

    public static Instant todayStartOfDay() {
        return LocalDateTime.now().with(MIN).atZone(systemDefault()).toInstant();
    }
}
