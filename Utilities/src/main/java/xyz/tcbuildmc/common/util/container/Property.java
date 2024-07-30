package xyz.tcbuildmc.common.util.container;

public interface Property<T extends Comparable<T>> {
    T get();

    void set(T value);

    T equal(T other);
}
