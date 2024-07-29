package xyz.tcbuildmc.common.config.api;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import xyz.tcbuildmc.common.config.api.manager.ConfigManager;
import xyz.tcbuildmc.common.config.api.model.ConfigObject;
import xyz.tcbuildmc.common.config.api.parser.DefaultParsers;
import xyz.tcbuildmc.common.config.api.parser.Parser;
import xyz.tcbuildmc.common.config.impl.ConfigApiImpl;
import xyz.tcbuildmc.common.config.impl.manager.FileConfigManager;
import xyz.tcbuildmc.common.config.impl.manager.URLConfigManager;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * The core of {@code simple-config-v0}, just involving two basic methods: {@code read(...)} and {@code write(...)}.
 * <p>
 * To work with the library, simply call {@link ConfigApi#getInstance()} and use the methods.
 *
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface ConfigApi {
    /**
     * Get an instance of {@link ConfigApi}.
     *
     * @return an instance of {@link ConfigApi}.
     */
    static ConfigApi getInstance() {
        return (ConfigApiImpl.INSTANCE != null) ? ConfigApiImpl.INSTANCE : new ConfigApiImpl();
    }

    /**
     * Parse the given config content and cast to the given class type.
     * <p>
     * There must be a constructor with no args in the class.
     *
     * @param clazz the class type of your config class
     * @param content the config content
     * @param parser the config parser. Also see {@link Parser} and {@link DefaultParsers}
     * @param <T> the type of your config class
     * @return an instance of your config class
     */
    <T> T read(Class<T> clazz, String content, Parser parser);

    /**
     * @see ConfigApi#read(Class, String, Parser)
     */
    default <T> T read(Class<T> clazz, @NotNull File file, Parser parser) {
        try {
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                file.createNewFile();
            }

            String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

            return read(clazz, content, parser);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read config file.", e);
        }
    }

    /**
     * @see ConfigApi#read(Class, String, Parser)
     */
    default <T> T read(Class<T> clazz, @NotNull Path path, Parser parser) {
        return read(clazz, path.toFile(), parser);
    }

    /**
     * @see ConfigApi#read(Class, String, Parser)
     */
    default <T> T read(Class<T> clazz, Reader reader, Parser parser) {
        try {
            String content = IOUtils.toString(reader);

            return read(clazz, content, parser);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read.", e);
        }
    }

    /**
     * @see ConfigApi#read(Class, String, Parser)
     */
    default <T> T read(Class<T> clazz, InputStream is, Parser parser) {
        try {
            String content = IOUtils.toString(is, StandardCharsets.UTF_8);

            return read(clazz, content, parser);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read.", e);
        }
    }

    /**
     * @see ConfigApi#read(Class, String, Parser)
     */
    default <T> T read(Class<T> clazz, URL url, Parser parser) {
        try {
            String content = IOUtils.toString(url, StandardCharsets.UTF_8);

            return read(clazz, content, parser);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read.", e);
        }
    }

    /**
     * @see ConfigApi#read(Class, String, Parser)
     */
    default <T> T read(Class<T> clazz, @NotNull URI uri, Parser parser) {
        try {
            return read(clazz, uri.toURL(), parser);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to parse URI.", e);
        }
    }

    /**
     * @see ConfigApi#read(Class, String, Parser)
     */
    default <T> T read(Class<T> clazz, byte[] bytes, Parser parser) {
        String content = IOUtils.toString(bytes, "utf-8");

        return read(clazz, content, parser);
    }

    /**
     * Parse the given instance of your config class to a {@link String}.
     *
     * @param clazz the class type of your config class
     * @param content the config content
     * @param parser the config parser. Also see {@link Parser} and {@link DefaultParsers}
     * @param <T> the type of your config class
     * @return a {@link String} of the instance of your config class
     */
    <T> String write(Class<T> clazz, T content, Parser parser);

    /**
     * @see ConfigApi#write(Class, Object, Parser)
     */
    default <T> byte[] writeToBytes(Class<T> clazz, T content, Parser parser) {
        return write(clazz, content, parser).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * @see ConfigApi#write(Class, Object, Parser)
     */
    default <T> void write(Class<T> clazz, T content, File file, Parser parser) {
        String config = write(clazz, content, parser);

        try {
            FileUtils.writeStringToFile(file, config, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to file.", e);
        }
    }

    /**
     * @see ConfigApi#write(Class, Object, Parser)
     */
    default <T> void write(Class<T> clazz, T content, @NotNull Path path, Parser parser) {
        write(clazz, content, path.toFile(), parser);
    }

    /**
     * @see ConfigApi#write(Class, Object, Parser)
     */
    default <T> void write(Class<T> clazz, T content, @NotNull Writer writer, Parser parser) {
        String config = write(clazz, content, parser);

        try {
            writer.write("");
            writer.write(config);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write.", e);
        }
    }

    /**
     * @see ConfigApi#write(Class, Object, Parser)
     */
    default <T> void write(Class<T> clazz, T content, @NotNull OutputStream os, Parser parser) {
        byte[] config = writeToBytes(clazz, content, parser);

        try {
            os.write(config);
            os.flush();
            os.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write.", e);
        }
    }


    /**
     * @see ConfigApi#read(Class, String, Parser)
     * @since 1.2.0
     */
    default ConfigObject read(String content, Parser parser) {
        return read(ConfigObject.class, content, parser);
    }

    /**
     * @see ConfigApi#read(Class, File, Parser)
     * @since 1.2.0
     */
    default ConfigObject read(File file, Parser parser) {
        return read(ConfigObject.class, file, parser);
    }

    /**
     * @see ConfigApi#read(Class, Path, Parser)
     * @since 1.2.0
     */
    default ConfigObject read(Path path, Parser parser) {
        return read(ConfigObject.class, path, parser);
    }

    /**
     * @see ConfigApi#read(Class, Reader, Parser)
     * @since 1.2.0
     */
    default ConfigObject read(Reader reader, Parser parser) {
        return read(ConfigObject.class, reader, parser);
    }

    /**
     * @see ConfigApi#read(Class, InputStream, Parser)
     * @since 1.2.0
     */
    default ConfigObject read(InputStream is, Parser parser) {
        return read(ConfigObject.class, is, parser);
    }

    /**
     * @see ConfigApi#read(Class, URL, Parser)
     * @since 1.2.0
     */
    default ConfigObject read(URL url, Parser parser) {
        return read(ConfigObject.class, url, parser);
    }

    /**
     * @see ConfigApi#read(Class, URI, Parser)
     * @since 1.2.0
     */
    default ConfigObject read(URI uri, Parser parser) {
        return read(ConfigObject.class, uri, parser);
    }

    /**
     * @see ConfigApi#read(Class, byte[], Parser)
     * @since 1.2.0
     */
    default ConfigObject read(byte[] bytes, Parser parser) {
        return read(ConfigObject.class, bytes, parser);
    }

    /**
     * @see ConfigApi#write(Class, Object, Parser)
     * @since 1.2.0
     */
    default String write(ConfigObject object, Parser parser) {
        return write(ConfigObject.class, object, parser);
    }

    /**
     * @see ConfigApi#write(Class, Object, File, Parser)
     * @since 1.2.0
     */
    default void write(ConfigObject object, File file, Parser parser) {
        write(ConfigObject.class, object, file, parser);
    }

    /**
     * @see ConfigApi#write(Class, Object, Path, Parser)
     * @since 1.2.0
     */
    default void write(ConfigObject object, Path path, Parser parser) {
        write(ConfigObject.class, object, path, parser);
    }

    /**
     * @see ConfigApi#write(Class, Object, Writer, Parser)
     * @since 1.2.0
     */
    default void write(ConfigObject object, Writer writer, Parser parser) {
        write(ConfigObject.class, object, writer, parser);
    }

    /**
     * @see ConfigApi#write(Class, Object, OutputStream, Parser)
     * @since 1.2.0
     */
    default void write(ConfigObject object, OutputStream os, Parser parser) {
        write(ConfigObject.class, object, os, parser);
    }

    /**
     * @see ConfigApi#write(ConfigObject, Parser)
     * @see ConfigApi#read(Class, String, Parser)
     * @since 1.2.1-hotfix.1
     */
    default <T> T toJavaObject(Class<T> clazz, ConfigObject object, Parser parser) {
        String config = write(object, parser);
        return (T) read(clazz, config, parser);
    }

    /**
     * @see ConfigApi#write(Class, Object, Parser)
     * @see ConfigApi#read(String, Parser)
     * @since 1.2.1-hotfix.1
     */
    default <T> ConfigObject toConfigObject(Class<T> clazz, T content, Parser parser) {
        String config = write(clazz, content, parser);
        return read(config, parser);
    }

    /**
     * Get an implementation of {@link ConfigManager}.
     * <p>
     * Use {@link File} to load/ save.
     *
     * @param clazz the class type of your config class
     * @param parser the config parser. Also see {@link Parser} and {@link DefaultParsers}
     * @param file the config {@link File}
     * @param <T> the type of your config class
     * @return an implementation of {@link ConfigManager}
     * @since 1.3.0
     */
    @Contract("_, _, _ -> new")
    @NotNull
    default <T> FileConfigManager<T> ofFile(Class<T> clazz, Parser parser, File file) {
        return new FileConfigManager<>(clazz, parser, file);
    }

    /**
     * A simple overload method of {@link ConfigApi#ofFile(Class, Parser, File)} (By {@link Path#toFile()}).
     *
     * @param clazz the class type of your config class
     * @param parser the config parser. Also see {@link Parser} and {@link DefaultParsers}
     * @param path the {@link Path} of the config {@link File}
     * @param <T> the type of your config class
     * @return an implementation of {@link ConfigManager}
     * @since 1.3.0
     */
    @Contract("_, _, _ -> new")
    @NotNull
    default <T> FileConfigManager<T> ofPath(Class<T> clazz, Parser parser, @NotNull Path path) {
        return ofFile(clazz, parser, path.toFile());
    }

    /**
     * Get an implementation of {@link ConfigManager}.
     * <p>
     * Use {@link URL} to load. (It doesn't support save!)
     *
     * @param clazz the class type of your config class
     * @param parser the config parser. Also see {@link Parser} and {@link DefaultParsers}
     * @param url a {@link URL} of the config file.
     * @param <T> the type of your config class
     * @return an implementation of {@link ConfigManager}
     * @since 1.3.0
     */
    @Contract("_, _, _ -> new")
    @ApiStatus.Experimental
    @NotNull
    default <T> URLConfigManager<T> ofURL(Class<T> clazz, Parser parser, URL url) {
        return new URLConfigManager<>(clazz, parser, url);
    }

    /**
     * A simple overload method of {@link ConfigApi#ofURL(Class, Parser, URL)}.
     *
     * @param clazz the class type of your config class
     * @param parser the config parser. Also see {@link Parser} and {@link DefaultParsers}
     * @param uri a {@link URI} of the config file.
     * @param <T> the type of your config class
     * @return an implementation of {@link ConfigManager}
     * @since 1.3.0
     */
    @Contract("_, _, _ -> new")
    @ApiStatus.Experimental
    @NotNull
    default <T> URLConfigManager<T> ofURI(Class<T> clazz, Parser parser, @NotNull URI uri) {
        try {
            return ofURL(clazz, parser, uri.toURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to parse URI.", e);
        }
    }
}
