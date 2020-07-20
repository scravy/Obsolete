package de.scravy.function;

import java.util.Optional;
import java.util.function.Function;

public interface OptionalFunction<A, B> extends Function<A, Optional<B>> {

    static <A, B> Function<A, Optional<B>> trying(final OptionalFunction<A, B> f) {
        return f;
    }

    @Override
    default Optional<B> apply(final A arg) {
        try {
            return Optional.ofNullable(applyThrowing(arg));
        } catch (final Exception exc) {
            return Optional.empty();
        }
    }

    B applyThrowing(final A arg) throws Exception;
}
