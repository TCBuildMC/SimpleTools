package xyz.tcbuildmc.common.config.test;

import org.junit.jupiter.api.Test;
import xyz.tcbuildmc.common.config.api.ConfigApi;
import xyz.tcbuildmc.common.config.api.parser.DefaultParsers;
import xyz.tcbuildmc.common.config.api.model.ConfigObject;

import java.io.File;
import java.util.Map;

public class ReadTest {
    @Test
    public void gsonRead() {
        File file = new File("run", "config.json");

        TestConfig instance = ConfigApi.getInstance().read(TestConfig.class, file, DefaultParsers.gson());
        System.out.println(instance);
    }

    @Test
    public void janksonRead() {
        File file = new File("run", "config.json5");

        TestConfig instance = ConfigApi.getInstance().read(TestConfig.class, file, DefaultParsers.jankson());
        System.out.println(instance);
    }

    @Test
    public void toml4jRead() {
        File file = new File("run", "config.toml");

        TestConfig instance = ConfigApi.getInstance().read(TestConfig.class, file, DefaultParsers.toml4j(false));
        System.out.println(instance);

        ConfigObject object = ConfigApi.getInstance().read(file, DefaultParsers.toml4j(false));
        System.out.println((Map<String, ?>) object.get("properties"));

        object.set("time", 17);
        System.out.println((int) object.get("time"));

        object.set("time", null);
        System.out.println(object.contains("time"));
    }

    @Test
    public void snakeYamlRead() {
        File file = new File("run", "config.yml");

        TestConfig instance = ConfigApi.getInstance().read(TestConfig.class, file, DefaultParsers.snakeYaml());
        System.out.println(instance);
    }

    @Test
    public void fastjsonRead() {
        File file = new File("run", "config.json");

        TestConfig instance = ConfigApi.getInstance().read(TestConfig.class, file, DefaultParsers.fastjson());
        System.out.println(instance);
    }
}
