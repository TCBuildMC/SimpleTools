package xyz.tcbuildmc.common.i18n;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import xyz.tcbuildmc.common.config.api.ConfigApi;
import xyz.tcbuildmc.common.config.api.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Supplier;

/**
 * See <a href="https://github.com/gnembon/fabric-carpet/blob/master/src/main/java/carpet/utils/Translations.java">Carpet mod's Translations class</a>.
 * <p>
 * This is just split it out to a single library.
 */
public class Translations {
    @Getter
    private static final List<String> supportedLanguages = new ArrayList<>(Arrays.asList("en_us"));

    @Getter
    @Setter
    private static Map<String, String> translations = Collections.emptyMap();

    @NotNull
    public static String getLocalLanguage() {
        return String.format("%s_%s", Locale.getDefault().getLanguage(), Locale.getDefault().getCountry()).toLowerCase(Locale.ROOT);
    }

    public static Map<String, String> getTranslationsFromClasspath(String path, String lang, String suffix, Parser parser) {
        if (!supportedLanguages.contains(lang)) {
            lang = supportedLanguages.get(0);
        }

        try (InputStream is = Translations.class.getClassLoader().getResourceAsStream(
                String.format("%s/%s.%s", path, lang, suffix))) {

            if (is == null) {
                return Collections.emptyMap();
            }

            try (InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                return (Map<String, String>) ConfigApi.getInstance().read(Map.class, isr, parser);
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

            ConfigApi.getInstance().write(Map.class, Collections.emptyMap(), path, parser);
            return Collections.emptyMap();
        }

        return (Map<String, String>) ConfigApi.getInstance().read(Map.class, path, parser);
    }

    public static Map<String, String> getTranslationsFromFile(@NotNull Path path, Parser parser) {
        return getTranslationsFromFile(path.toFile(), parser);
    }

    public static String getTranslation(String key) {
        return translations.getOrDefault(key, key);
    }

    public static String getTranslation(String key, Object... args) {
        return String.format(translations.getOrDefault(key, key), args);
    }

    public static String getTranslationOrNull(String key) {
        return translations.get(key);
    }

    public static String getTranslationsOrDefault(String key, String or) {
        return translations.getOrDefault(key, or);
    }

    public static String getTranslationsOrDefault(String key, String or, Object... args) {
        return String.format(translations.getOrDefault(key, or), args);
    }

    public static String getTranslationsOrDefault(String key, @NotNull Supplier<String> or) {
        return translations.getOrDefault(key, or.get());
    }

    public static String getTranslationsOrDefault(String key, @NotNull Supplier<String> or, Object... args) {
        return String.format(translations.getOrDefault(key, or.get()), args);
    }

    @Contract(pure = true)
    public static boolean hasTranslations() {
        return translations.isEmpty();
    }

    @Contract(pure = true)
    public static boolean hasTranslation(String key) {
        return translations.containsKey(key);
    }

    public static void addLanguageSupport(String... lang) {
        Collections.addAll(supportedLanguages, lang);
    }
}
