package xyz.tcbuildmc.common.util.container;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.tcbuildmc.common.util.function.Action;
import xyz.tcbuildmc.common.util.function.BiAction;
import xyz.tcbuildmc.common.util.function.Provider;

import java.util.Objects;
import java.util.Optional;
import java.util.function.*;

@SuppressWarnings("unused")
public abstract class AABB<A, B> {
    public abstract Optional<A> a();

    public abstract Optional<B> b();

    public abstract AABB<A, B> accept(Consumer<? super A> a, Consumer<? super B> b);

    public abstract AABB<A, B> acceptAll(BiConsumer<? super A, ? super B> consumer);

    public abstract <R> R apply(Function<? super A, ? extends R> a, Function<? super B, ? extends R> b);

    public abstract <R> R applyAll(BiFunction<? super A, ? super B, ? extends R> function);

    public abstract <C, D> AABB<C, D> map(Function<? super A, ? extends C> a, Function<? super B, ? extends D> b);

    public abstract <C, D> AABB<C, D> flatmap(Function<? super A, ? extends AABB<C, D>> a, Function<? super B, ? extends AABB<C, D>> b);

    public abstract boolean test(Predicate<? super A> a, Predicate<? super B> b);

    public abstract boolean testAll(BiPredicate<? super A, ? super B> predicate);

    public abstract Object execute(Action<? super A> a, Action<? super B> b);

    public abstract Object executeAll(BiAction<A, B> action);

    public abstract AABB<A, B> get(Provider<A> a, Provider<B> b);

    @Override
    public abstract String toString();

    @Contract(value = "null -> false", pure = true)
    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    private static final class AA<E, F> extends AABB<E, F> {
        private final E a;

        private AA(E a) {
            this.a = Objects.requireNonNull(a, "A");
        }

        @Contract(value = " -> new", pure = true)
        @Override
        public @NotNull Optional<E> a() {
            return Optional.of(a);
        }

        @Contract(pure = true)
        @Override
        public Optional<F> b() {
            return Optional.empty();
        }

        @Contract("_, _ -> this")
        @Override
        public AABB<E, F> accept(@NotNull Consumer<? super E> a, Consumer<? super F> b) {
            a.accept(this.a);
            return this;
        }

        @Contract(value = "_ -> this", pure = true)
        @Override
        public AABB<E, F> acceptAll(BiConsumer<? super E, ? super F> consumer) {
            return this;
        }

        @Override
        public <R> R apply(@NotNull Function<? super E, ? extends R> a, Function<? super F, ? extends R> b) {
            return a.apply(this.a);
        }

        @Contract(pure = true)
        @Override
        public <R> @Nullable R applyAll(BiFunction<? super E, ? super F, ? extends R> function) {
            return null;
        }

        @Contract("_, _ -> new")
        @Override
        public <C, D> @NotNull AABB<C, D> map(@NotNull Function<? super E, ? extends C> a, Function<? super F, ? extends D> b) {
            return new AA<>(a.apply(this.a));
        }

        @Override
        public <C, D> AABB<C, D> flatmap(@NotNull Function<? super E, ? extends AABB<C, D>> a, Function<? super F, ? extends AABB<C, D>> b) {
            return a.apply(this.a);
        }

        @Override
        public boolean test(@NotNull Predicate<? super E> a, Predicate<? super F> b) {
            return a.test(this.a);
        }

        @Contract(pure = true)
        @Override
        public boolean testAll(BiPredicate<? super E, ? super F> predicate) {
            return false;
        }

        @Override
        public Object execute(@NotNull Action<? super E> a, Action<? super F> b) {
            return a.execute(this.a);
        }

        @Contract(pure = true)
        @Override
        public @Nullable Object executeAll(BiAction<E, F> action) {
            return null;
        }

        @Contract("_, _ -> new")
        @Override
        public @NotNull AABB<E, F> get(@NotNull Provider<E> a, Provider<F> b) {
            return new AA<>(a.get());
        }

        @Contract(pure = true)
        @Override
        public @NotNull String toString() {
            return "AA{" +
                    "a=" + a +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AA<?, ?> aa = (AA<?, ?>) o;
            return Objects.equals(this.a, aa.a);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(a);
        }
    }

    private static final class BB<E, F> extends AABB<E, F> {
        private final F b;

        private BB(F b) {
            this.b = Objects.requireNonNull(b, "B");
        }

        @Contract(pure = true)
        @Override
        public Optional<E> a() {
            return Optional.empty();
        }

        @Contract(value = " -> new", pure = true)
        @Override
        public @NotNull Optional<F> b() {
            return Optional.of(this.b);
        }

        @Contract("_, _ -> this")
        @Override
        public AABB<E, F> accept(Consumer<? super E> a, @NotNull Consumer<? super F> b) {
            b.accept(this.b);
            return this;
        }

        @Contract(value = "_ -> this", pure = true)
        @Override
        public AABB<E, F> acceptAll(BiConsumer<? super E, ? super F> consumer) {
            return this;
        }

        @Override
        public <R> R apply(Function<? super E, ? extends R> a, @NotNull Function<? super F, ? extends R> b) {
            return b.apply(this.b);
        }

        @Contract(pure = true)
        @Override
        public <R> @Nullable R applyAll(BiFunction<? super E, ? super F, ? extends R> function) {
            return null;
        }

        @Contract("_, _ -> new")
        @Override
        public <C, D> @NotNull AABB<C, D> map(Function<? super E, ? extends C> a, @NotNull Function<? super F, ? extends D> b) {
            return new BB<>(b.apply(this.b));
        }

        @Override
        public <C, D> AABB<C, D> flatmap(Function<? super E, ? extends AABB<C, D>> a, @NotNull Function<? super F, ? extends AABB<C, D>> b) {
            return b.apply(this.b);
        }

        @Override
        public boolean test(Predicate<? super E> a, @NotNull Predicate<? super F> b) {
            return b.test(this.b);
        }

        @Contract(pure = true)
        @Override
        public boolean testAll(BiPredicate<? super E, ? super F> predicate) {
            return false;
        }

        @Override
        public Object execute(Action<? super E> a, @NotNull Action<? super F> b) {
            return b.execute(this.b);
        }

        @Contract(pure = true)
        @Override
        public @Nullable Object executeAll(BiAction<E, F> action) {
            return null;
        }

        @Contract("_, _ -> new")
        @Override
        public @NotNull AABB<E, F> get(Provider<E> a, @NotNull Provider<F> b) {
            return new BB<>(b.get());
        }

        @Contract(pure = true)
        @Override
        public @NotNull String toString() {
            return "BB{" +
                    "b=" + b +
                    '}';
        }

        @Contract(value = "null -> false", pure = true)
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BB<?, ?> bb = (BB<?, ?>) o;
            return Objects.equals(this.b, bb.b);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.b);
        }
    }

    private static final class AB<E, F> extends AABB<E, F> {
        private final E a;
        private final F b;

        private AB(E a, F b) {
            this.a = Objects.requireNonNull(a, "A");
            this.b = Objects.requireNonNull(b, "B");
        }

        @Contract(value = " -> new", pure = true)
        @Override
        public @NotNull Optional<E> a() {
            return Optional.of(this.a);
        }

        @Contract(value = " -> new", pure = true)
        @Override
        public @NotNull Optional<F> b() {
            return Optional.of(this.b);
        }

        @Contract("_, _ -> this")
        @Override
        public AABB<E, F> accept(@NotNull Consumer<? super E> a, @NotNull Consumer<? super F> b) {
            a.accept(this.a);
            b.accept(this.b);
            return this;
        }

        @Contract("_ -> this")
        @Override
        public AABB<E, F> acceptAll(@NotNull BiConsumer<? super E, ? super F> consumer) {
            consumer.accept(this.a, this.b);
            return this;
        }

        @Contract(pure = true)
        @Override
        public <R> @Nullable R apply(Function<? super E, ? extends R> a, Function<? super F, ? extends R> b) {
            return null;
        }

        @Override
        public <R> R applyAll(@NotNull BiFunction<? super E, ? super F, ? extends R> function) {
            return function.apply(this.a, this.b);
        }

        @Contract(pure = true)
        @Override
        public <C, D> @Nullable AABB<C, D> map(Function<? super E, ? extends C> a, Function<? super F, ? extends D> b) {
            return null;
        }

        @Contract(pure = true)
        @Override
        public <C, D> @Nullable AABB<C, D> flatmap(Function<? super E, ? extends AABB<C, D>> a, Function<? super F, ? extends AABB<C, D>> b) {
            return null;
        }

        @Override
        public boolean test(@NotNull Predicate<? super E> a, Predicate<? super F> b) {
            return a.test(this.a) && b.test(this.b);
        }

        @Override
        public boolean testAll(@NotNull BiPredicate<? super E, ? super F> predicate) {
            return predicate.test(this.a, this.b);
        }

        @Contract(pure = true)
        @Override
        public @Nullable Object execute(Action<? super E> a, Action<? super F> b) {
            return null;
        }

        @Override
        public Object executeAll(@NotNull BiAction<E, F> action) {
            return action.execute(this.a, this.b);
        }

        @Contract("_, _ -> new")
        @Override
        public @NotNull AABB<E, F> get(@NotNull Provider<E> a, @NotNull Provider<F> b) {
            return new AB<>(a.get(), b.get());
        }

        @Contract(pure = true)
        @Override
        public @NotNull String toString() {
            return "AB{" +
                    "a=" + a +
                    ", b=" + b +
                    '}';
        }

        @Contract(value = "null -> false", pure = true)
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AB<?, ?> ab = (AB<?, ?>) o;
            return Objects.equals(this.a, ab.a) && Objects.equals(this.b, ab.b);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.a, this.b);
        }
    }

    public final boolean isOnlyA() {
        return this.a().isPresent() && !this.isBoth();
    }

    public final boolean isOnlyB() {
        return this.b().isPresent() && !this.isBoth();
    }

    public final boolean isBoth() {
        return this.a().isPresent() && this.b().isPresent();
    }

    public final AABB<B, A> swap() {
        return this.apply(AABB::newB, AABB::newA);
    }

    public final <C> AABB<C, B> mapA(Function<? super A, ? extends C> function) {
        return this.map(function, Function.identity());
    }

    public final <D> AABB<A, D> mapB(Function<? super B, ? extends D> function) {
        return this.map(Function.identity(), function);
    }
    
    @Contract("_ -> new")
    public static <A, B> @NotNull AABB<A, B> newA(A a) {
        return new AA<>(a);
    }

    @Contract("_ -> new")
    public static <A, B> @NotNull AABB<A, B> newB(B b) {
        return new BB<>(b);
    }
}
