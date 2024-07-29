package xyz.tcbuildmc.common.config.api.parser;

import java.util.function.Function;

/**
 * The basic config parser of {@code simple-config-v0}.
 * <p>
 * If you want to make a custom {@link Parser}, simply implements this.
 *
 * @since 1.0.0
 */
public interface Parser {
    /**
     * Tool for serializing the config content.
     *
     * @param clazz a class type
     * @param <T> the type of the class
     * @return a {@link Function} instance to use to parse the config content.
     */
    <T> Function<String, T> toObject(Class<T> clazz);

    /**
     * Tool for deserializing the config class instance.
     *
     * @param clazz a class type
     * @param <T> the type of the class
     * @return a {@link Function} instance to use to parse the config class instance.
     */
    <T> Function<T, String> toConfig(Class<T> clazz);
}
