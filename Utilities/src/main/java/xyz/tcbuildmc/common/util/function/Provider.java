package xyz.tcbuildmc.common.util.function;

@FunctionalInterface
public interface Provider<T> {
    T get();
}
