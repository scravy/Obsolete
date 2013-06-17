package net.scravy.technetium.util.value;

/**
 * A type safe representation of illegal Number values (in order to distinguish
 * from null as “not given”).
 * 
 * Any method invoked on instances of this object (except {@link #toString()}),
 * will throw an {@link UnsupportedOperationException}.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class IllegalNumber extends Number {

	private static final long serialVersionUID = 2765896191001913231L;

	private final String definition;

	/**
	 * @param definition
	 *            The textual representation which could not be converted to a
	 *            number. This may well be a well-formed number, but it probably
	 *            exceeded the value range.
	 */
	public IllegalNumber(String definition) {
		this.definition = definition;
	}

	@Override
	public double doubleValue() {
		throw new UnsupportedOperationException();
	}

	@Override
	public float floatValue() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int intValue() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long longValue() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return this.definition;
	}
}