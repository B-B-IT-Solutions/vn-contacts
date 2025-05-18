package cz.prm.utils;

import static java.lang.Math.abs;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TestUtils {

    private static Random random = new Random();

    public static Long randomLong() {
        return abs(random.nextLong());
    }

    public static Integer randomInt() {
        return abs(random.nextInt());
    }

    public static Short randomShort() {
        return (short) abs(random.nextInt(32767));
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
