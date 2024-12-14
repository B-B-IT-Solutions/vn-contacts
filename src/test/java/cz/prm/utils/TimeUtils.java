package cz.prm.utils;

import static java.time.ZoneId.of;

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
}
