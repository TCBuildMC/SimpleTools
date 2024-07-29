package xyz.tcbuildmc.common.config.impl.manager;

import lombok.Getter;
import xyz.tcbuildmc.common.config.api.ConfigApi;
import xyz.tcbuildmc.common.config.api.parser.Parser;

import java.io.File;

@Getter
public class FileConfigManager<T> extends AbstractConfigManager<T> {
    private final File file;

    public FileConfigManager(Class<T> clazz, Parser parser, File file) {
        super(clazz, parser);
        this.file = file;
    }

    @Override
    public void load() {
        super.content = ConfigApi.getInstance().read(getClazz(), this.file, getParser());
    }

    @Override
    public void save() {
        ConfigApi.getInstance().write(getClazz(), super.content, this.file, getParser());
        this.load();
    }
}
