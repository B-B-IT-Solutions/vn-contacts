package cz.prm.utils;

import static java.lang.Math.abs;
import static java.lang.String.format;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class ComponentTestUtils {

    private static Random random = new Random();

    public static Long randomLong() {
        return abs(random.nextLong());
    }

    public static List<String> uuids() {
        return uuids(3);
    }

    public static List<String> uuids(int count) {
        return range(0, count).mapToObj((i) -> uuid()).collect(toList());
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

    public static String startsWithFilter(String... filters) {
        var filter = Stream.of(filters).collect(joining(","));
        return containsFilter(filter);
    }

    public static String startsWithFilter(String filter) {
        return format("startsWith(%s)", filter);
    }

    public static String endsWithFilter(String... filters) {
        var filter = Stream.of(filters).collect(joining(","));
        return notContainsFilter(filter);
    }

    public static String endsWithFilter(String filter) {
        return format("endsWith(%s)", filter);
    }

    public static String equalsFilter(String filter) {
        return format("equals(%s)", filter);
    }

    public static String notEqualsFilter(String filter) {
        return format("notEquals(%s)", filter);
    }

    public static String arrayIncludesFilter(List<String> filters) {
        return format("arrIncludes(%s)", filters.stream().collect(joining(",")));
    }

    public static String arrayIncludesAllFilter(List<String> filters) {
        return format("arrIncludesAll(%s)", filters.stream().collect(joining(",")));
    }

    public static String emptyFilter() {
        return format("empty( )");
    }

    public static String notEmptyFilter() {
        return format("notEmpty( )");
    }
}
