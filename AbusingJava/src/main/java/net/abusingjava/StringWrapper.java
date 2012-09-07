package net.abusingjava;

@Author("Julian Fleischer")
@Version("2012-01-06")
@Since("2012-01-06")
public class StringWrapper<T extends StringWrapper<?>> implements Comparable<T> {
	protected final String $string;
	
	public StringWrapper(final String $string) {
		this.$string = $string;
	}

	@Override
	public int hashCode() {
		return $string.hashCode();
	}

	@Override
	public boolean equals(final Object $obj) {
		if (this == $obj)
			return true;
		if ($obj == null)
			return false;
		if (getClass() != $obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		StringWrapper<T> $other = (StringWrapper<T>) $obj;
		if ($string == null) {
			if ($other.$string != null)
				return false;
		} else if (!$string.equals($other.$string))
			return false;
		return true;
	}

	@Override
	public int compareTo(final T $obj) {
		return $obj.$string.compareTo($string);
	}
	
	@Override
	public String toString() {
		return $string;
	}
}