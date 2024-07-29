package xyz.tcbuildmc.common.util.versioning;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class VersionUtils {
    @Contract(pure = true)
    public static boolean isExperimental(@NotNull String version) {
        return version.endsWith("exp");
    }

    @Contract(pure = true)
    public static boolean isAlpha(@NotNull String version) {
        return version.endsWith("alpha");
    }

    @Contract(pure = true)
    public static boolean isBeta(@NotNull String version) {
        return version.endsWith("beta");
    }

    @Contract(pure = true)
    public static boolean isPreview(@NotNull String version) {
        return version.endsWith("pre");
    }

    @Contract(pure = true)
    public static boolean isDev(@NotNull String version) {
        return version.endsWith("dev");
    }

    @Contract(pure = true)
    public static boolean isStable(@NotNull String version) {
        return version.endsWith("stable");
    }

    @Contract(pure = true)
    public static boolean isRelease(@NotNull String version) {
        return isStable(version) || (!isExperimental(version) && !isAlpha(version) && !isBeta(version) && !isPreview(version) && isDev(version));
    }
}
