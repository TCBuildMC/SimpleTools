package xyz.tcbuildmc.common.util.function;

public interface BiAction<T, U> {
    Object execute(T t, U u);
}
