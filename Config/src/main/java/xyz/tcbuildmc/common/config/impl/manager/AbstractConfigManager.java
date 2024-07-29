package xyz.tcbuildmc.common.config.impl.manager;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import xyz.tcbuildmc.common.config.api.manager.ConfigManager;
import xyz.tcbuildmc.common.config.api.parser.Parser;

public abstract class AbstractConfigManager<T> implements ConfigManager {
    @Getter
    private final Class<T> clazz;
    @Getter
    private final Parser parser;

    @Nullable
    public T content;

    @Contract(pure = true)
    public AbstractConfigManager(Class<T> clazz, Parser parser) {
        this.clazz = clazz;
        this.parser = parser;
    }

    @Override
    public abstract void load();

    @Override
    public void reload() {
        this.load();
    }

    @Override
    public abstract void save();
}
