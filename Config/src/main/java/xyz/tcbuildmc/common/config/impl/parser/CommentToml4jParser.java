package xyz.tcbuildmc.common.config.impl.parser;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import xyz.tcbuildmc.common.config.api.annotation.TomlComment;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

// 参考 PrismConfig
public class CommentToml4jParser extends Toml4jParser {
    public CommentToml4jParser(Toml toml, TomlWriter tomlWriter) {
        super(toml, tomlWriter);
    }

    public CommentToml4jParser() {
    }

    @Override
    public <T> Function<String, T> toObject(Class<T> clazz) {
        return super.toObject(clazz);
    }

    @Override
    public <T> Function<T, String> toConfig(Class<T> clazz) {
        return (config) -> {
            try {
                StringBuilder sb = new StringBuilder();
                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);

                    if (field.isAnnotationPresent(TomlComment.class)) {
                        sb.append("# ")
                                .append(field.getDeclaredAnnotation(TomlComment.class).value())
                                .append("\n");
                    }

                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put(field.getName(), field.get(config));

                    sb.append(getTomlWriter().write(map));
                }

                return sb.toString();
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to parse.", e);
            }
        };
    }
}
