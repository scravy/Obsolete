package net.abusingjava;

import java.io.Serializable;
import java.util.Iterator;


@Author("Julian Fleischer")
@Version("2011-08-17")
@Since("2011-08-17")
public class Message implements Serializable, Iterable<Serializable> {

	private static final long serialVersionUID = 7797401903899366380L;
	
	final Serializable[] $values;
	
	public Message(final Serializable... $values) {
		this.$values = $values;
	}
	
	@Override
	public String toString() {
		if ($values.length > 0) {
			if ($values[0] instanceof String) {
				Object[] $args = new Object[$values.length - 1];
				for (int $i = 1; $i < $values.length; $i++) {
					$args[$i-1] = $values[$i];
				}
				return String.format((String) $values[0], $args);
			} else if ($values[0] instanceof Exception) {
				return $values[0].getClass().getSimpleName()
						+ ": " + ((Throwable)$values[0]).getMessage();
			} else if ($values[0] instanceof Enum) {
				return $values[0].toString();
			}
			return $values[0].toString();
		}
		return super.toString();
	}

	@Override
	public Iterator<Serializable> iterator() {
		return AbusingArrays.array($values).iterator();
	}
	
	public Serializable get(final int $which) {
		return $values[$which];
	}
	
	public int length() {
		return $values.length;
	}
}
