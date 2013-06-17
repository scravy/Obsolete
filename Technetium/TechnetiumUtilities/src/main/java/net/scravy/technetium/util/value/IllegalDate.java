package net.scravy.technetium.util.value;

import java.util.Date;

/**
 * A type safe representation of illegal Date values (in order to distinguish
 * from null as “not given”).
 * 
 * Any method invoked on instances of this object (except {@link #toString()}),
 * will throw an {@link UnsupportedOperationException}.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class IllegalDate extends Date {

	private static final long serialVersionUID = -3091706184853521421L;

	private String definition;

	/**
	 * @param definition
	 *            The textual representation which could not be converted to a
	 *            legal date. This may syntactically be correct, like
	 *            “1999-02-29”.
	 */
	public IllegalDate(String definition) {
		this.definition = definition;
	}

	@Override
	public boolean after(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean before(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object clone() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int compareTo(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean equals(Object obj) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getTime() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTime(long arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return definition;
	}

}