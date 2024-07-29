package xyz.tcbuildmc.common.util.simpletools.function;

public interface Property<T extends Comparable<T>> {
    T get();

    void set(T value);

    T equal(T other);
}
