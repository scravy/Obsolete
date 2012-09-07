package net.abusingjava;

/**
 * A boxed value, all wrapped up into a fine object. If you need a mutable final
 * value (if you ever want this...).
 * 
 * @param <T>
 *            The type of the underlying value object.
 */
@Author("Julian Fleischer")
@Version("2012-01-04")
public class Box<T> {
	private T $value = null;

	/**
	 * Sets the value of this box.
	 */
	public void set(final T $value) {
		this.$value = $value;
	}

	/**
	 * Elvis.
	 */
	public void setIfNull(final T $value) {
		if (this.$value == null) {
			this.$value = $value;
		}
	}

	/**
	 * Gets the value of this box.
	 */
	public T get() {
		return $value;
	}

	/**
	 * Returns the value of this box (like {@link #get()} or null, if the value
	 * is null). Returns null if and only if $default is null.
	 */
	public T get(final T $default) {
		if ($value == null) {
			return $default;
		}
		return $value;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean equals(final Object $object) {
		if ($value == null) {
			$object.equals(null);
		}
		return $value.equals($object);
	}

	@Override
	public int hashCode() {
		if ($value == null) {
			return Null.OBJECT.hashCode();
		}
		return $value.hashCode();
	}
}