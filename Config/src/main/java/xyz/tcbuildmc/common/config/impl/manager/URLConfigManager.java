package xyz.tcbuildmc.common.config.impl.manager;

import lombok.Getter;
import org.jetbrains.annotations.ApiStatus;
import xyz.tcbuildmc.common.config.api.ConfigApi;
import xyz.tcbuildmc.common.config.api.parser.Parser;

import java.net.URL;

@ApiStatus.Experimental
@Getter
public class URLConfigManager<T> extends AbstractConfigManager<T> {
    private final URL url;

    public URLConfigManager(Class<T> clazz, Parser parser, URL url) {
        super(clazz, parser);
        this.url = url;
    }

    @Override
    public void load() {
        super.content = ConfigApi.getInstance().read(getClazz(), this.url, getParser());
    }

    @Override
    public void save() {
        throw new UnsupportedOperationException();
    }
}
