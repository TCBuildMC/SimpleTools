package xyz.tcbuildmc.common.util.simpletools.collection;

import org.jetbrains.annotations.Contract;

import java.util.Collection;
import java.util.List;

import static xyz.tcbuildmc.common.util.simpletools.check.ConditionChecker.throwIf;

public class CollectionUtils {
    public static <C extends Collection<?>> C throwIfEmpty(C value, int index) {
        CollectionUtils.throwIfOutOfIndex(value, index);

        return throwIf(value, (C c) -> {
            int i = 0;
            for (Object o : c) {
                if (i == index) {
                    if (o == null) {
                        return true;
                    } else {
                        break;
                    }
                }

                i++;
            }

            return false;
        });
    }

    public static <C extends Collection<?>> C throwIfEmpty(C value) {
        return throwIf(value, value.isEmpty());
    }

    public static <C extends Collection<?>, K> C throwIfNotContains(C value, K key) {
        return throwIf(value, !value.contains(key));
    }

    public static <C extends Collection<?>> C throwIfOutOfIndex(C value, int index) {
        return throwIf(value, index < 0 || index >= value.size());
    }

    @Contract("_ -> param1")
    public static <C extends Collection<?>> C throwIfHasNull(C value) {
        return throwIf(value, (C c) -> {
            for (Object o : c) {
                if (o == null) {
                    return true;
                }
            }

            return false;
        });
    }
}
