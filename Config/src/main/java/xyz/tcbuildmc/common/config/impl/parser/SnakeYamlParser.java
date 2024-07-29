package xyz.tcbuildmc.common.config.impl.parser;

import org.yaml.snakeyaml.Yaml;
import xyz.tcbuildmc.common.config.api.parser.Parser;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public class SnakeYamlParser implements Parser {
    private final Yaml yaml;

    public SnakeYamlParser(Yaml yaml) {
        this.yaml = yaml;
    }

    public SnakeYamlParser() {
        this(new Yaml());
    }

    @Override
    public <T> Function<String, T> toObject(Class<T> clazz) {
        return content -> {
            try {
                T instance = this.yaml.load(content);

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
        return this.yaml::dump;
    }
}
