package de.scravy;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class Either<L, R> implements Serializable {

    private Either() {
        throw new UnsupportedOperationException();
    }

    public L left() {
        return null;
    }

    public R right() {
        return null;
    }

    public boolean isLeft() {
        return false;
    }

    public boolean isRight() {
        return false;
    }

    public abstract <M> Either<M, R> mapLeft(final Function<L, M> f);

    public abstract <S> Either<L, S> map(final Function<R, S> f);

    public abstract <S> Either<L, S> flatMap(final Function<R, Either<L, S>> f);

    public abstract Either<L, R> forEach(final Consumer<R> f);

    public abstract Right<L, R> recover(final Function<L, R> f);

    public abstract Either<L, R> recoverWith(final Function<L, Either<L, R>> f);

    public abstract Either<L, R> filter(final Predicate<R> f, final L v);

    public abstract Either<L, R> filterWith(final Predicate<R> f, final Supplier<L> v);

    public abstract void consume(final Consumer<L> f, final Consumer<R> g);

    public abstract <T> T foldWith(final Function<L, T> f, final Function<R, T> g);

    public abstract Optional<R> toOptional();

    public static <L, R> Left<L, R> left(final L value) {
        return new Left<>(value);
    }

    public static <L, R> Right<L, R> right(final R value) {
        return new Right<>(value);
    }

    public static <V> Exceptional<V> toExceptional(final Either<Exception, V> either) {
        return either.foldWith(Exceptional::exception, Exceptional::value);
    }

    public static class Left<L, R> extends Either<L, R> {

        final L value;

        private Left(final L value) {
            this.value = value;
        }

        @Override
        public L left() {
            return this.value;
        }

        @Override
        public boolean isLeft() {
            return true;
        }

        @Override
        public <M> Left<M, R> mapLeft(Function<L, M> f) {
            return new Left<>(f.apply(value));
        }

        @Override
        @SuppressWarnings("unchecked")
        public <S> Left<L, S> map(final Function<R, S> f) {
            return (Left<L, S>) this;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <S> Left<L, S> flatMap(final Function<R, Either<L, S>> f) {
            return (Left<L, S>) this;
        }

        @Override
        public Left<L, R> forEach(final Consumer<R> f) {
            return this;
        }

        @Override
        public Right<L, R> recover(final Function<L, R> f) {
            return new Right<>(f.apply(value));
        }

        @Override
        public Either<L, R> recoverWith(final Function<L, Either<L, R>> f) {
            return f.apply(value);
        }

        @Override
        public Left<L, R> filter(final Predicate<R> f, final L v) {
            return this;
        }

        @Override
        public Left<L, R> filterWith(final Predicate<R> f, final Supplier<L> v) {
            return this;
        }

        @Override
        public void consume(final Consumer<L> f, final Consumer<R> g) {
            f.accept(value);
        }

        @Override
        public <T> T foldWith(final Function<L, T> f, final Function<R, T> g) {
            return f.apply(value);
        }

        @Override
        public Optional<R> toOptional() {
            return Optional.empty();
        }
    }

    public static class Right<L, R> extends Either<L, R> {

        final R value;

        private Right(final R value) {
            this.value = value;
        }

        @Override
        public R right() {
            return this.value;
        }

        @Override
        public boolean isRight() {
            return true;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <M> Right<M, R> mapLeft(Function<L, M> f) {
            return (Right<M, R>) this;
        }

        @Override
        public <S> Right<L, S> map(final Function<R, S> f) {
            return new Right<>(f.apply(value));
        }

        @Override
        public <S> Either<L, S> flatMap(final Function<R, Either<L, S>> f) {
            return f.apply(value);
        }

        @Override
        public Right<L, R> forEach(final Consumer<R> f) {
            f.accept(value);
            return this;
        }

        @Override
        public Right<L, R> recover(final Function<L, R> f) {
            return this;
        }

        @Override
        public Either<L, R> recoverWith(final Function<L, Either<L, R>> f) {
            return this;
        }

        @Override
        public Either<L, R> filter(final Predicate<R> f, final L v) {
            if (f.test(value)) {
                return this;
            }
            return new Left<>(v);
        }

        @Override
        public Either<L, R> filterWith(final Predicate<R> f, final Supplier<L> v) {
            if (f.test(value)) {
                return this;
            }
            return new Left<>(v.get());
        }

        @Override
        public void consume(final Consumer<L> f, final Consumer<R> g) {
            g.accept(value);
        }

        @Override
        public <T> T foldWith(final Function<L, T> f, final Function<R, T> g) {
            return g.apply(value);
        }

        @Override
        public Optional<R> toOptional() {
            return Optional.ofNullable(value);
        }
    }

}
