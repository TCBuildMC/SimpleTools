package xyz.tcbuildmc.common.util.simpletools.i18n;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import xyz.tcbuildmc.common.config.v0.api.SimpleConfigApi;
import xyz.tcbuildmc.common.config.v0.api.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * See <a href="https://github.com/gnembon/fabric-carpet/blob/master/src/main/java/carpet/utils/Translations.java">Carpet mod's Translations class</a>.
 * <p>
 * This is just split it out to a single library.
 */
@Getter
@Setter
public class Translations {
    private static Map<String, String> translations = Collections.emptyMap();

    @NotNull
    public static String getLanguage() {
        return String.format("%s_%s", Locale.getDefault().getLanguage(), Locale.getDefault().getCountry()).toLowerCase(Locale.ROOT);
    }

    public static Map<String, String> getTranslationsFromClasspath(String path, Parser parser) {
        try (InputStream is = Translations.class.getClassLoader().getResourceAsStream(path)) {
            if (is == null) {
                return Collections.emptyMap();
            }

            try (InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                return (Map<String, String>) SimpleConfigApi.getInstance().read(Map.class, isr, parser);
            }
        } catch (IOException e) {
            System.err.println("Failed to get translations.");
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    public static Map<String, String> getTranslationsFromFile(@NotNull File path, Parser parser) {
        if (!path.exists()) {
            if (!path.getParentFile().exists()) {
                path.getParentFile().mkdirs();
            }

            SimpleConfigApi.getInstance().write(Map.class, Collections.emptyMap(), path, parser);
            return Collections.emptyMap();
        }

        return (Map<String, String>) SimpleConfigApi.getInstance().read(Map.class, path, parser);
    }

    public static Map<String, String> getTranslationsFromFile(@NotNull Path path, Parser parser) {
        return getTranslationsFromFile(path.toFile(), parser);
    }

    public static String getTranslations(String key) {
        return translations.getOrDefault(key, key);
    }

    public static String getTranslationOrNull(String key) {
        return translations.get(key);
    }

    public static String getTranslationsOrDefault(String key, String or) {
        return translations.getOrDefault(key, or);
    }

    @Contract(pure = true)
    public static boolean hasTranslations() {
        return translations.isEmpty();
    }

    @Contract(pure = true)
    public static boolean hasTranslation(String key) {
        return translations.containsKey(key);
    }
}
