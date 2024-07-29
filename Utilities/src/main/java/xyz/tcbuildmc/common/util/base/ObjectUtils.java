package xyz.tcbuildmc.common.util.base;

import org.jetbrains.annotations.Contract;

import java.util.Objects;
import java.util.function.Supplier;

public class ObjectUtils {
    @Contract(value = "!null, _ -> param1", pure = true)
    public static <T> T requiresNonNullOrElse(T object, T other) {
        if (object == null) {
            return Objects.requireNonNull(other);
        }

        return object;
    }

    @Contract("!null, _ -> param1")
    public static <T> T requiresNonNullOrGet(T object, Supplier<T> other) {
        if (object == null) {
            return Objects.requireNonNull(Objects.requireNonNull(other).get());
        }

        return object;
    }
}
