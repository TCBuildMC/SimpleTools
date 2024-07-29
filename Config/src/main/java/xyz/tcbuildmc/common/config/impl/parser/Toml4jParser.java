package xyz.tcbuildmc.common.config.impl.parser;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import lombok.AccessLevel;
import lombok.Getter;
import xyz.tcbuildmc.common.config.api.parser.Parser;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public class Toml4jParser implements Parser {
    private final Toml toml;
    @Getter(AccessLevel.PACKAGE)
    private final TomlWriter tomlWriter;

    public Toml4jParser(Toml toml, TomlWriter tomlWriter) {
        this.toml = toml;
        this.tomlWriter = tomlWriter;
    }

    public Toml4jParser() {
        this(new Toml(), new TomlWriter.Builder().build());
    }

    @Override
    public <T> Function<String, T> toObject(Class<T> clazz) {
        return content -> {
            try {
                T instance = this.toml.read(content).to(clazz);

                if (instance == null) {
                    return clazz.getDeclaredConstructor().newInstance();
                }

                return instance;
            } catch (InvocationTargetException |
                     InstantiationException |
                     IllegalAccessException |
                     NoSuchMethodException e) {

                throw new RuntimeException("Failed to parse.", e);
            }
        };
    }

    @Override
    public <T> Function<T, String> toConfig(Class<T> clazz) {
        return this.tomlWriter::write;
    }
}
