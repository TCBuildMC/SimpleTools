package xyz.tcbuildmc.common.util.simpletools.collection;

import java.util.Map;

import static xyz.tcbuildmc.common.util.simpletools.check.ConditionChecker.throwIf;

public class MapUtils {
    public static <M extends Map<?, ?>> M throwIfEmpty(M value) {
        return throwIf(value, value.isEmpty());
    }

    public static <K, M extends Map<K, ?>> M throwIfNotContainsKey(M value, K key) {
        return throwIf(value, !value.containsKey(key));
    }

    public static <V, M extends Map<?, V>> M throwIfNotContainsValue(M value, V key) {
        return throwIf(value, !value.containsValue(key));
    }

    public static <M extends Map<?, ?>> M throwIfHasNullKey(M value) {
        return throwIf(value, (M m) -> {
            for (Map.Entry<?, ?> entry : m.entrySet()) {
                if (entry.getKey() == null) {
                    return true;
                } else {
                    break;
                }
            }

            return false;
        });
    }

    public static <M extends Map<?, ?>> M throwIfHasNullValue(M value) {
        return throwIf(value, (M m) -> {
            for (Map.Entry<?, ?> entry : m.entrySet()) {
                if (entry.getValue() == null) {
                    return true;
                } else {
                    break;
                }
            }

            return false;
        });
    }

    public static <M extends Map<?, ?>> M throwIfHasNull(M value) {
        return throwIf(value, (M m) -> {
            for (Map.Entry<?, ?> entry : m.entrySet()) {
                if (entry.getKey() == null || entry.getValue() == null) {
                    return true;
                } else {
                    break;
                }
            }

            return false;
        });
    }
}
