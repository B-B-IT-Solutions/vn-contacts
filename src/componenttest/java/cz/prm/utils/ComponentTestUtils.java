package cz.prm.utils;

import static java.lang.Math.abs;
import static java.lang.String.format;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.joining;

import java.util.Random;
import java.util.stream.Stream;

public class ComponentTestUtils {

    private static Random random = new Random();

    public static Long randomLong() {
        return abs(random.nextLong());
    }

    public static String uuid() {
        return randomUUID().toString();
    }

    public static String containsNotContainsFilter(String filter1, String fitler2) {
        return format("%s+%s", containsFilter(filter1), notContainsFilter(fitler2));
    }

    public static String containsFilter(String... filters) {
        var filter = Stream.of(filters).collect(joining(","));
        return containsFilter(filter);
    }

    public static String containsFilter(String filter) {
        return format("contains(%s)", filter);
    }

    public static String notContainsFilter(String... filters) {
        var filter = Stream.of(filters).collect(joining(","));
        return notContainsFilter(filter);
    }

    public static String notContainsFilter(String filter) {
        return format("notContains(%s)", filter);
    }
}
