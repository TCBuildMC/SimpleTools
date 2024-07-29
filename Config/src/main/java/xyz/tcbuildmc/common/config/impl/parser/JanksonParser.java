package xyz.tcbuildmc.common.config.impl.parser;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.api.SyntaxError;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import xyz.tcbuildmc.common.config.api.parser.Parser;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public class JanksonParser implements Parser {
    private final Jankson jankson;

    @Contract(pure = true)
    public JanksonParser(Jankson jankson) {
        this.jankson = jankson;
    }

    public JanksonParser() {
        this(new Jankson.Builder().build());
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public <T> Function<String, T> toObject(Class<T> clazz) {
        return content -> {
            try {
                T instance = this.jankson.fromJson(content, clazz);

                if (instance == null) {
                    return clazz.getDeclaredConstructor().newInstance();
                }

                return instance;
            } catch (SyntaxError |
                     InvocationTargetException |
                     InstantiationException |
                     IllegalAccessException |
                     NoSuchMethodException e) {

                throw new RuntimeException("Failed to parse.", e);
            }
        };
    }

    @Contract(pure = true)
    @NotNull
    @Override
    public <T> Function<T, String> toConfig(Class<T> clazz) {
        return instance -> this.jankson.toJson(instance).toJson(true, true);
    }
}
