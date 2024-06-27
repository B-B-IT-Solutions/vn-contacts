package cz.prm.utils;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.abs;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestUtils {

    private static Random random = new Random();

    public static Long randomLong() {
        return abs(random.nextLong());
    }

    public static Integer randomInt() {
        return abs(random.nextInt());
    }

    public static List<String> uuids() {
        return uuids(3);
    }

    public static List<String> uuids(int count) {
        return range(0, count).mapToObj((i) -> uuid()).collect(toList());
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }
}
