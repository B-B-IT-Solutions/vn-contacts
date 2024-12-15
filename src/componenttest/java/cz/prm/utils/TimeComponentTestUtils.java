package cz.prm.utils;

import static java.time.LocalTime.MIN;
import static java.time.ZoneId.systemDefault;

import java.time.Instant;
import java.time.LocalDateTime;

public class TimeComponentTestUtils {

    public static Instant todayStartOfDay() {
        return LocalDateTime.now().with(MIN).atZone(systemDefault()).toInstant();
    }
}
