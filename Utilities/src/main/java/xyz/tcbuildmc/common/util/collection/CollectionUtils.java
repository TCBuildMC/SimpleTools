package xyz.tcbuildmc.common.util.collection;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CollectionUtils {
    @Contract("_ -> new")
    @NotNull
    @SafeVarargs
    public static <E> List<E> ofArrayList(E... e) {
        return new ArrayList<>(Arrays.asList(e));
    }

    @Contract("_ -> new")
    @NotNull
    @SafeVarargs
    public static <E> List<E> ofLinkedList(E... e) {
        return new LinkedList<>(Arrays.asList(e));
    }

    @Contract("_ -> new")
    @NotNull
    @SafeVarargs
    public static <E> Set<E> ofHashSet(E... e) {
        return new HashSet<>(Arrays.asList(e));
    }

    @Contract("_ -> new")
    @NotNull
    @SafeVarargs
    public static <E> Set<E> ofLinkedHashSet(E... e) {
        return new LinkedHashSet<>(Arrays.asList(e));
    }

    @Contract("_ -> new")
    @NotNull
    @SafeVarargs
    public static <E> Set<E> ofTreeSet(E... e) {
        return new TreeSet<>(Arrays.asList(e));
    }

    @Contract("_ -> new")
    @NotNull
    @SafeVarargs
    public static <E> List<E> ofVector(E... e) {
        return new Vector<>(Arrays.asList(e));
    }

    @Contract("_ -> new")
    @NotNull
    @SafeVarargs
    public static <E> Stack<E> ofStack(E @NotNull ... e) {
        Stack<E> stack = new Stack<>();
        for (E o : e) {
            stack.push(o);
        }

        return stack;
    }

    @Contract("_ -> new")
    @NotNull
    @SafeVarargs
    public static <E> Deque<E> ofArrayDeque(E... e) {
        return new ArrayDeque<>(Arrays.asList(e));
    }

    @NotNull
    @SafeVarargs
    public static <K, V> Map<K, V> ofHashMap(MapEntry<K, V> @NotNull ... entries) {
        Map<K, V> map = new HashMap<>();
        for (MapEntry<K, V> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }

        return map;
    }

    @NotNull
    @SafeVarargs
    public static <K, V> Map<K, V> ofLinkedHashMap(MapEntry<K, V> @NotNull ... entries) {
        Map<K, V> map = new LinkedHashMap<>();
        for (MapEntry<K, V> entry : entries) {
            map.put(entry.k, entry.v);
        }

        return map;
    }

    @NotNull
    @SafeVarargs
    public static <K, V> Hashtable<K, V> ofHashTable(MapEntry<K, V> @NotNull ... entries) {
        Hashtable<K, V> hashtable = new Hashtable<>();
        for (MapEntry<K, V> entry : entries) {
            hashtable.put(entry.k, entry.v);
        }

        return hashtable;
    }

    public static final class MapEntry<K, V> implements Map.Entry<K, V> {
        private final K k;
        private final V v;

        @Contract(pure = true)
        public MapEntry(K k, V v) {
            this.k = k;
            this.v = v;
        }

        @Contract(pure = true)
        @Override
        public K getKey() {
            return this.k;
        }

        @Contract(pure = true)
        @Override
        public V getValue() {
            return this.v;
        }

        @Contract(value = "_ -> fail", pure = true)
        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }
    }
}
