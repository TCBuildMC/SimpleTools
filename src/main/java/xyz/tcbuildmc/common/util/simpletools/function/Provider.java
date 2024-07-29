package xyz.tcbuildmc.common.util.simpletools.function;

@FunctionalInterface
public interface Provider<T> {
    T get();
}
