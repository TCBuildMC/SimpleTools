package xyz.tcbuildmc.common.config.test;

import blue.endless.jankson.Comment;
import xyz.tcbuildmc.common.config.api.annotation.TomlComment;
import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
public class TestConfig {
    @Comment("Language of the app.")
    @TomlComment("Language of the app.")
    private String lang;
    private int time;

    @Comment("Is commands allowed?")
    private boolean enable;
    private List<String> depends;
    private Map<String, ?> properties;
}
