package net.scravy.technetium.util.value;

/**
 * Either is inspired by the Either type known from Haskell and other functional
 * languages and/or strongly typed languages.
 * <p>
 * An Either object wraps a value of either its left type or its right type.
 * This is very useful if you are about to create functions which can return two
 * very different things but you want to keep it typesafe.
 * <p>
 * In order to create an Either object of {@link Left} or {@link Right}, use
 * {@link ValueUtil#left(Object)} and {@link ValueUtil#right(Object)}.
 * 
 * @author Julian Fleischer
 * 
 * @param <L>
 *            The left type.
 * @param <R>
 *            The right type.
 */
public interface Either<L, R> {

	/**
	 * Checks whether this Either-object contains an object of the left type.
	 * 
	 * @return true if the wrapped object is of type L, false otherwise.
	 */
	boolean isLeft();

	/**
	 * Checks whether this Either-object contains an object of the right type.
	 * 
	 * @return true if the wrapped object is of type R, false otherwise.
	 */
	boolean isRight();

	/**
	 * Return the wrapped value of type L (the left type).
	 * 
	 * @return The value of type L (iff this is a {@link Left}).
	 * @throws RuntimeException
	 *             Iff the object actually contains a value of the right type.
	 *             Use {@link #isLeft()} to check before accessing this method.
	 */
	L getLeft();

	/**
	 * Return the wrapped value of type R (the right type).
	 * 
	 * @return The value of type R (iff this is a {@link Right}).
	 * @throws RuntimeException
	 *             Iff the object actually contains a value of the left type.
	 *             Use {@link #isRight()} to check before accessing this method.
	 */
	R getRight();

}
