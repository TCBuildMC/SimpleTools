package xyz.tcbuildmc.common.config.test;

import com.google.gson.Gson;
import lombok.*;
import org.junit.jupiter.api.Test;
import xyz.tcbuildmc.common.config.api.ConfigApi;
import xyz.tcbuildmc.common.config.api.model.ConfigObject;
import xyz.tcbuildmc.common.config.api.parser.DefaultParsers;

import java.util.*;

public class MapTest {
    @Test
    public void testMerge() {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("114", 514);
        System.out.println(map);

        map.merge("114", 515, (oldValue, newValue) -> newValue);
        System.out.println(map);

        map.merge("0", "qwq", (oldValue, newValue) -> newValue);
        System.out.println(map);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void pathWalkerTest() {
        ConfigObject object = new ConfigObject();

        Map<String, Object> properties = new LinkedHashMap<>();
        ConfigObject ban = new ConfigObject();

        ban.set("tel", 114514);
        ban.set("qq", 1919810);
        properties.put("awa", Arrays.asList("MC", "Java", "Windows", "Server", ban));
        object.set("properties", properties);

        System.out.println((int) object.getByPath("properties.awa.5.tel"));
        System.out.println((Map<String, ?>) object.getByPath("properties.awa.5"));
        System.out.println((String) object.getByPath("properties.awa.2"));

        Object o1 = object.getByPath("properties.qwq");
        System.out.println(o1);

        Object o2 = object.getByPath("properties.awa.5.tel.v");
        System.out.println(o2);

        System.out.println(ConfigApi.getInstance().toJavaObject(Map.class, object, DefaultParsers.gson()));
    }

    @Test
    public void configInConfigTest() {
        System.out.println(ConfigApi.getInstance().write(Config1.class, new Config1(), DefaultParsers.gson(new Gson())));

        String json = "{\"commandAwa\":{\"enable\":true,\"exceptPlayers\":[]}}";
        Config1 c = ConfigApi.getInstance().read(Config1.class, json, DefaultParsers.gson());
        System.out.println(c.getCommandAwa());
        System.out.println(c);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString(callSuper = false)
    @EqualsAndHashCode(callSuper = false)
    public static class Config1 {
        private Config2 commandAwa = new Config2();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString(callSuper = false)
    @EqualsAndHashCode(callSuper = false)
    public static class Config2 {
        private boolean enable = true;
        private List<String> exceptPlayers = new ArrayList<>();
    }
}
