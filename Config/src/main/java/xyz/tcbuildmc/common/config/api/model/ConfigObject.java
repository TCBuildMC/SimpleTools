package xyz.tcbuildmc.common.config.api.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Supplier;

/**
 * An implementation of {@link Map}, for reading values by keys from config.
 *
 * @since 1.2.0
 */
@SuppressWarnings("unused")
public class ConfigObject extends LinkedHashMap<String, Object> implements Map<String, Object> {
    /**
     * A constructor of {@link ConfigObject} with no args.
     */
    public ConfigObject() {
    }

    /**
     * A constructor of {@link ConfigObject}.
     *
     * @param m the map {@link ConfigObject} will inherit.
     */
    public ConfigObject(Map<? extends String, ?> m) {
        super(m);
    }

    /**
     * An implementation of {@link Map#get(Object)} (Config key is {@link String} only).
     *
     * @param key the key of config
     * @param <T> a type
     * @return the value of the config key
     */
    @Nullable
    public <T> T get(String key) {
        if (key == null) {
            throw new NullPointerException();
        }

        return (T) super.get(key);
    }

    /**
     * A method to find config value of specified config path.
     *
     * @param path the config path
     * @param <T> a type
     * @return the value of the config key
     * @since 1.2.1
     */
    @Nullable
    public <T> T getByPath(@NotNull String path) {
        String[] keys = path.split("\\.");

        Object self = new ConfigObject(this);
        for (String key : keys) {
            if (self instanceof Map) {
                Map<String, ?> instance = (Map<String, ?>) self;

                if (!instance.containsKey(key)) {
                    return null;
                }

                self = instance.get(key);
            } else if (self instanceof List) {
                List<?> instance = (List<?>) self;
                int index = Integer.parseInt(key) - 1;

                if (index < 0 || index >= instance.size()) {
                    return null;
                }

                self = instance.get(index);
            } else {
                break;
            }
        }

        return (T) self;
    }

    /**
     * An implementation of {@link Map#getOrDefault(Object, Object)} (Config key is {@link String} only).
     *
     * @param key the key of config
     * @param defaultValue if the value of the config key is null, this method will return it.
     * @param <T> a type
     * @return the value of the config key. If the value is null, it will be {@code defaultValue}
     */
    @NotNull
    public <T> T getOrDefault(String key, T defaultValue) {
        T value = this.get(key);

        if (value == null) {
            if (defaultValue == null) {
                throw new NullPointerException();
            }

            return defaultValue;
        }

        return value;
    }

    /**
     * @see ConfigObject#getOrDefault(String, Object)
     * @see ConfigObject#getByPath(String)
     * @since 1.2.1
     */
    @NotNull
    public <T> T getByPathOrDefault(String path, T defaultValue) {
        T value = this.getByPath(path);

        if (value == null) {
            if (defaultValue == null) {
                throw new NullPointerException();
            }

            return defaultValue;
        }

        return value;
    }

    /**
     * @see ConfigObject#getOrDefault(Object, Object)
     */
    @Contract("_, null -> fail")
    @NotNull
    public <T> T getOrDefault(String key, Supplier<T> supplier) {
        if (supplier == null) {
            throw new NullPointerException();
        }

        return this.getOrDefault(key, supplier.get());
    }

    /**
     * @see ConfigObject#getByPathOrDefault(String, Object)
     * @since 1.2.1
     */
    @Contract("_, null -> fail")
    @NotNull
    public <T> T getByPathOrDefault(String path, Supplier<T> supplier) {
        if (supplier == null) {
            throw new NullPointerException();
        }

        return this.getByPathOrDefault(path, supplier.get());
    }

    /**
     * @see ConfigObject#get(String) 
     */
    @NotNull
    public <T> T getOrThrow(String key) {
        T value = this.get(key);

        if (value == null) {
            throw new NullPointerException();
        }

        return value;
    }

    /**
     * @see ConfigObject#getOrThrow(String)
     * @see ConfigObject#getByPath(String)
     * @since 1.2.1
     */
    @NotNull
    public <T> T getByPathOrThrow(String path) {
        T value = this.getByPath(path);

        if (value == null) {
            throw new NullPointerException();
        }

        return value;
    }

    /**
     * @see ConfigObject#getOrThrow(String) 
     */
    @NotNull
    public <T, X extends Throwable> T getOrThrow(String key, X e) throws X {
        T value = this.get(key);

        if (value == null) {
            throw e;
        }

        return value;
    }

    /**
     * @see ConfigObject#getByPathOrThrow(String)
     * @since 1.2.1
     */
    @NotNull
    public <T, X extends Throwable> T getByPathOrThrow(String path, X e) throws X {
        T value = this.getByPath(path);

        if (value == null) {
            throw e;
        }

        return value;
    }

    /**
     * @see ConfigObject#getOrThrow(String, Throwable)
     */
    @NotNull
    public <T, X extends Throwable> T getOrThrow(String key, Supplier<X> supplier) throws X {
        T value = this.get(key);

        if (value == null) {
            throw supplier.get();
        }

        return value;
    }

    /**
     * @see ConfigObject#getByPathOrThrow(String, Throwable)
     * @since 1.2.1
     */
    @NotNull
    public <T, X extends Throwable> T getByPathOrThrow(String path, Supplier<X> supplier) throws X {
        T value = this.getByPath(path);

        if (value == null) {
            throw supplier.get();
        }

        return value;
    }

    /**
     * Returns whether the value of the key exists.
     *
     * @param key the config key
     * @return Whether the value of the key exists
     */
    public boolean contains(String key) {
        return super.containsKey(key);
    }

    /**
     * An alternative of {@link Map#put(Object, Object)} and {@link Map#remove(Object)}.
     * <p>
     * If the value is {@code null}, the config key and value will be deleted.
     * <p>
     * If the value in config exists, it will be replaced with value in the method.
     * <p>
     * Otherwise, it will add the key and the value to the config.
     *
     * @param key the config key
     * @param value a value of the key
     */
    public void set(String key, @Nullable Object value) {
        if (key == null) {
            throw new NullPointerException();
        }

        if (value == null) {
            super.remove(key);
            return;
        }

        super.merge(key, value, ConfigObject::replaceOld);
    }

    /**
     * Use {@link ConfigObject#set(String, Object)} instead.
     *
     * @deprecated
     */
    @Deprecated
    @Override
    public Object put(String key, Object value) {
        return super.put(key, value);
    }

    /**
     * Use {@link ConfigObject#set(String, Object)} instead.
     *
     * @deprecated
     */
    @Deprecated
    @Override
    public Object putIfAbsent(String key, Object value) {
        return super.putIfAbsent(key, value);
    }

    /**
     * Use {@link ConfigObject#set(String, Object)} instead.
     *
     * @deprecated
     */
    @Deprecated
    public void add(String key, String value) {
        this.put(key, value);
    }

    /**
     * Use {@link ConfigObject#set(String, Object)} instead.
     *
     * @deprecated
     */
    @Deprecated
    public void remove(String key) {
        super.remove(key);
    }

    /**
     * A helper method for {@link ConfigObject#set(String, Object)}.
     * <p>
     * At the same time, this is also an implementation of {@link java.util.function.BiFunction#apply(Object, Object)}.
     * <p>
     * Just be used to replace the {@code oldValue}.
     * 
     * @param oldValue the old value
     * @param newValue the new value
     * @return the new value
     */
    @Contract(value = "_, _ -> param2", pure = true)
    public static Object replaceOld(Object oldValue, Object newValue) {
        return newValue;
    }
}
