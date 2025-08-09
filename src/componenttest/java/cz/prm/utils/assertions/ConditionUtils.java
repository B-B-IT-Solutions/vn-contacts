package cz.prm.utils.assertions;

import static java.util.Objects.isNull;

import org.assertj.core.api.Condition;

public class ConditionUtils {

    public static <T> Condition nullOrEquals(T expected) {
        return new Condition<T>(value -> isNull(value) || value.equals(expected), "invalid condition null or equals");
    }
}
