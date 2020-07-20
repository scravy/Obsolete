package de.scravy.function;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class Partial {

    private Partial() {
        throw new UnsupportedOperationException();
    }

    public static <A, B, R> Function<B, R> apply(final BiFunction<A, B, R> f, final A arg1) {
        return arg2 -> f.apply(arg1, arg2);
    }

    public static <A, B, C, R> BiFunction<B, C, R> apply(final TriFunction<A, B, C, R> f, final A arg1) {
        return (arg2, arg3) -> f.apply(arg1, arg2, arg3);
    }

    public static <A, B, C, R> Function<C, R> apply(final TriFunction<A, B, C, R> f, final A arg1, final B arg2) {
        return arg3 -> f.apply(arg1, arg2, arg3);
    }

    public static <A, B, C, D, R> Function<D, R> apply(final TetraFunction<A, B, C, D, R> f, final A arg1, final B arg2, final C arg3) {
        return arg4 -> f.apply(arg1, arg2, arg3, arg4);
    }

    public static <A, B, C, D, R> BiFunction<C, D, R> apply(final TetraFunction<A, B, C, D, R> f, final A arg1, final B arg2) {
        return (arg3, arg4) -> f.apply(arg1, arg2, arg3, arg4);
    }

    public static <A, B, C, D, R> TriFunction<B, C, D, R> apply(final TetraFunction<A, B, C, D, R> f, final A arg1) {
        return (arg2, arg3, arg4) -> f.apply(arg1, arg2, arg3, arg4);
    }

    public static <A> Function<A, A> applyOn(final Function<A, ?> f) {
        return arg -> {
            f.apply(arg);
            return arg;
        };
    }
}
