package xyz.tcbuildmc.common.util.simpletools.base;

import org.jetbrains.annotations.Contract;

import java.io.File;
import java.util.function.Supplier;

public class SystemUtils {
    public static String getPropertyOrThrow(String key) {
        if (System.getProperty(key) == null) {
            throw new NullPointerException();
        }

        return System.getProperty(key);
    }

    public static <X extends Throwable> String getPropertyOrThrow(String key, X e) throws X {
        if (System.getProperty(key) == null) {
            throw e;
        }

        return System.getProperty(key);
    }

    public static <X extends Throwable> String getPropertyOrThrow(String key, Supplier<X> e) throws X {
        if (System.getProperty(key) == null) {
            throw e.get();
        }

        return System.getProperty(key);
    }

    @Contract(pure = true)
    public static String getPathSeparator() {
        return File.separator;
    }

    @Contract(pure = true)
    public static String getFileSeparator() {
        return File.pathSeparator;
    }
}
