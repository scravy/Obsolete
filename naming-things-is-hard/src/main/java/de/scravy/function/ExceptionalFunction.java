package de.scravy.function;

import de.scravy.Exceptional;

import java.util.function.Function;

public interface ExceptionalFunction<A, B> extends Function<A, Exceptional<B>> {

    static <A, B> Function<A, Exceptional<B>> trying(final ExceptionalFunction<A, B> f) {
        return f;
    }

    @Override
    default Exceptional<B> apply(final A arg) {
        try {
            return Exceptional.value(applyExceptional(arg));
        } catch (final Exception exc) {
            return Exceptional.exception(exc);
        }
    }

    B applyExceptional(final A arg) throws Exception;
}
