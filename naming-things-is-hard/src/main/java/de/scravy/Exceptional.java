package de.scravy;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class Exceptional<V> implements Serializable {

    private Exceptional() {

    }

    public Exception getException() {
        throw new RuntimeException();
    }

    public V getValue() {
        throw new RuntimeException();
    }

    public boolean isException() {
        return false;
    }

    public boolean isValue() {
        return false;
    }

    public abstract Exceptional<V> mapLeft(final Function<Exception, Exception> f);

    public abstract <W> Exceptional<W> map(final Function<V, W> f);

    public abstract <W> Exceptional<W> flatMap(final Function<V, Exceptional<W>> f);

    public abstract Exceptional<V> forEach(final Consumer<V> f);

    public abstract Exceptional<V> recover(final Function<Exception, V> f);

    public abstract <W> Exceptional<W> recoverWith(final Function<Exception, Exceptional<W>> f);

    public abstract Exceptional<V> filter(final Predicate<V> f, final Exception v);

    public Exceptional<V> filter(final Predicate<V> f, final String msg) {
        return filter(f, new Exception(msg));
    }

    public abstract Exceptional<V> filterWith(final Predicate<V> f, final Supplier<Exception> v);

    public abstract void consume(final Consumer<Exception> f, final Consumer<V> g);

    public abstract <T> T foldWith(final Function<Exception, T> f, final Function<V, T> g);

    public abstract Either<Exception, V> toEither();

    public abstract Optional<V> toOptional();

    public abstract V get() throws Exception;

    public static <V> Exceptional<V> from(final Optional<V> optional, final Exception exc) {
        return optional.map(Exceptional::value).orElse(exception(exc));
    }

    public static <V> Exceptional<V> exception(final Exception exc) {
        return new Failure<>(exc);
    }

    public static <V> Exceptional<V> value(final V value) {
        return new Success<>(value);
    }

    public static class Failure<V> extends Exceptional<V> {

        private final Exception exception;

        private Failure(final Exception exception) {
            this.exception = exception;
        }

        @Override
        public Exception getException() {
            return exception;
        }

        @Override
        public boolean isException() {
            return true;
        }

        @Override
        public Exceptional<V> mapLeft(final Function<Exception, Exception> f) {
            return new Failure<>(f.apply(exception));
        }

        @Override
        @SuppressWarnings("unchecked")
        public <W> Exceptional<W> map(final Function<V, W> f) {
            return (Exceptional<W>) this;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <W> Exceptional<W> flatMap(final Function<V, Exceptional<W>> f) {
            return (Exceptional<W>) this;
        }

        @Override
        public Exceptional<V> forEach(final Consumer<V> f) {
            return this;
        }

        @Override
        public Exceptional<V> recover(final Function<Exception, V> f) {
            return value(f.apply(exception));
        }

        @Override
        public <W> Exceptional<W> recoverWith(final Function<Exception, Exceptional<W>> f) {
            return f.apply(exception);
        }

        @Override
        public Exceptional<V> filter(final Predicate<V> f, final Exception v) {
            return this;
        }

        @Override
        public Exceptional<V> filterWith(final Predicate<V> f, final Supplier<Exception> v) {
            return this;
        }

        @Override
        public void consume(final Consumer<Exception> f, final Consumer<V> g) {
            f.accept(exception);
        }

        @Override
        public <T> T foldWith(final Function<Exception, T> f, Function<V, T> g) {
            return f.apply(exception);
        }

        @Override
        public Either<Exception, V> toEither() {
            return Either.left(exception);
        }

        @Override
        public Optional<V> toOptional() {
            return Optional.empty();
        }

        @Override
        public V get() throws Exception {
            throw exception;
        }

        @Override
        public String toString() {
            return String.format("%s(%s)", exception.getClass().getSimpleName(), exception.getMessage());
        }
    }

    public static class Success<V> extends Exceptional<V> {

        private final V value;

        private Success(final V value) {
            this.value = value;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public boolean isValue() {
            return true;
        }

        @Override
        public Exceptional<V> mapLeft(final Function<Exception, Exception> f) {
            return this;
        }

        @Override
        public <W> Exceptional<W> map(final Function<V, W> f) {
            return new Success<>(f.apply(value));
        }

        @Override
        public <W> Exceptional<W> flatMap(final Function<V, Exceptional<W>> f) {
            return f.apply(value);
        }

        @Override
        public Exceptional<V> forEach(final Consumer<V> f) {
            f.accept(value);
            return this;
        }

        @Override
        public Success<V> recover(final Function<Exception, V> f) {
            return this;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <W> Exceptional<W> recoverWith(final Function<Exception, Exceptional<W>> f) {
            return (Success<W>) this;
        }

        @Override
        public Exceptional<V> filter(final Predicate<V> f, Exception v) {
            if (f.test(value)) {
                return this;
            }
            return new Failure<>(v);
        }

        @Override
        public Exceptional<V> filterWith(final Predicate<V> f, Supplier<Exception> v) {
            if (f.test(value)) {
                return this;
            }
            return new Failure<>(v.get());
        }

        @Override
        public void consume(Consumer<Exception> f, Consumer<V> g) {
            g.accept(value);
        }

        @Override
        public <T> T foldWith(final Function<Exception, T> f, Function<V, T> g) {
            return g.apply(value);
        }

        @Override
        public Either<Exception, V> toEither() {
            return Either.right(value);
        }

        @Override
        public Optional<V> toOptional() {
            return Optional.ofNullable(value);
        }

        @Override
        public V get() {
            return value;
        }

        public String toString() {
            return String.format("Value(%s)", value);
        }
    }
}
