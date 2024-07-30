package xyz.tcbuildmc.common.util.function;

@FunctionalInterface
public interface Action<T> {
    Object execute(T t);
}
