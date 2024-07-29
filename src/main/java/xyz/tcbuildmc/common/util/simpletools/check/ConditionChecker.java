package xyz.tcbuildmc.common.util.simpletools.check;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ConditionChecker {
    @Contract(value = "_, true -> fail; _, false -> param1", pure = true)
    public static <T> T throwIf(T value, boolean condition) {
        if (condition) {
            throw new IllegalArgumentException();
        }

        return value;
    }

    public static <T> T throwIf(T value, Supplier<Boolean> condition) {
        throwIf(condition, condition == null);
        throwIf(condition.get(), condition.get() == null);

        return throwIf(value, condition.get());
    }

    public static <T> T throwIf(T value, Predicate<T> condition) {
        throwIf(condition, condition == null);

        return throwIf(value, condition.test(value));
    }

    public static <T> T throwIf(T value, Function<T, Boolean> condition) {
        throwIf(condition, condition == null);

        return throwIf(value, condition.apply(value));
    }

    @Contract(pure = true)
    @NotNull
    public static <T> T throwIfNull(T value) {
        return throwIf(value, value == null);
    }
}
