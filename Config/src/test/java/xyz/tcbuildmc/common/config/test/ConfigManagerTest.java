package xyz.tcbuildmc.common.config.test;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import xyz.tcbuildmc.common.config.api.ConfigApi;
import xyz.tcbuildmc.common.config.api.parser.DefaultParsers;
import xyz.tcbuildmc.common.config.impl.manager.FileConfigManager;

import java.io.File;
import java.io.IOException;

public class ConfigManagerTest {
    @Test
    public void loadConfig() {
        FileConfigManager<TestConfig> manager = ConfigApi.getInstance().ofFile(TestConfig.class, DefaultParsers.jankson(), new File("run", "config.json5"));

        manager.load();

        if (manager.content == null) {
            throw new NullPointerException();
        }
        System.out.println(manager.content);

        manager.save();
    }

    @Test
    public void saveEditedConfig() throws IOException {
        FileUtils.copyFile(new File("run", "config.json5"), new File("run", "config-copied.json5"));
        FileConfigManager<TestConfig> manager = ConfigApi.getInstance().ofFile(TestConfig.class, DefaultParsers.jankson(), new File("run", "config-copied.json5"));

        manager.load();

        if (manager.content == null) {
            throw new NullPointerException();
        }

        manager.content.setTime(manager.content.getTime() - 1);
        manager.save();
    }
}
