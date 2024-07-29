package xyz.tcbuildmc.common.config.impl;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.tcbuildmc.common.config.api.ConfigApi;
import xyz.tcbuildmc.common.config.api.parser.Parser;

@ApiStatus.Internal
public final class ConfigApiImpl implements ConfigApi {
    @Nullable
    public static ConfigApi INSTANCE;

    public ConfigApiImpl() {
        ConfigApiImpl.INSTANCE = this;
    }

    @Override
    public <T> T read(Class<T> clazz, String content, @NotNull Parser parser) {
        return parser.toObject(clazz).apply(content);
    }

    @Override
    public <T> String write(Class<T> clazz, T content, @NotNull Parser parser) {
        return parser.toConfig(clazz).apply(content);
    }
}
