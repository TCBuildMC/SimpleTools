package xyz.tcbuildmc.common.config.api.manager;

/**
 * An easy api to load/ save config.
 * <p>
 * It's like Bukkit config api.
 *
 * @since 1.3.0
 */
public interface ConfigManager {
    /**
     * To load the config.
     */
    void load();

    /**
     * To reload the config.
     * <p>
     * By default, this method just call {@link ConfigManager#load()}.
     */
    void reload();

    /**
     * To save the config.
     */
    void save();
}
