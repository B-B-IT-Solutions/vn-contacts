package cz.prm.utils;

import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {

    private static final TimeZone REAL_TIME_ZONE = TimeZone.getDefault();

    public static void useMockTimeZone() {
        var zoneId = of("Europe/Prague");
        TimeZone.setDefault(TimeZone.getTimeZone(zoneId));
    }

    public static void useSystemDefaultTimeZone() {
        TimeZone.setDefault(REAL_TIME_ZONE);
    }

    public static LocalDateTime toLocalDateTime(String value) {
        return LocalDateTime.ofInstant(toInstant(value), systemDefault());
    }

    public static LocalDate toLocalDate(String value) {
        return LocalDate.ofInstant(toInstant(value), systemDefault());
    }

    public static Instant toInstant(String value) {
        return new Date(value).toInstant();
    }
}
