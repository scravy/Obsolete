package net.abusingjava;

@Experimental
@Author("Julian Fleischer")
@Version("2011-12-28")
public class AbusingAsserts {

	public static void assertArrayEquals(final String $message, final char[] $0, final char[] $1) {
		if ($0 == null) {
			if ($1 == null) {
				return;
			}
			throw new AssertionError($message);
		}
		if ($1 == null) {
			throw new AssertionError($message);
		}
		if ($0.length != $1.length) {
			throw new AssertionError($message);
		}
		for (int $i = 0; $i < $0.length; $i++) {
			if ($0[$i] != $1[$i]) {
				throw new AssertionError($message);
			}
		}
	}
	
	public static void assertArrayEquals(final String $message, final byte[] $0, final byte[] $1) {
		if ($0 == null) {
			if ($1 == null) {
				return;
			}
			throw new AssertionError($message);
		}
		if ($1 == null) {
			throw new AssertionError($message);
		}
		if ($0.length != $1.length) {
			throw new AssertionError($message);
		}
		for (int $i = 0; $i < $0.length; $i++) {
			if ($0[$i] != $1[$i]) {
				throw new AssertionError($message);
			}
		}
	}
	
	public static void assertArrayEquals(final String $message, final float[] $0, final float[] $1) {
		if ($0 == null) {
			if ($1 == null) {
				return;
			}
			throw new AssertionError($message);
		}
		if ($1 == null) {
			throw new AssertionError($message);
		}
		if ($0.length != $1.length) {
			throw new AssertionError($message);
		}
		for (int $i = 0; $i < $0.length; $i++) {
			if ($0[$i] != $1[$i]) {
				throw new AssertionError($message);
			}
		}
	}
	
	public static void assertArrayEquals(final String $message, final double[] $0, final double[] $1) {
		if ($0 == null) {
			if ($1 == null) {
				return;
			}
			throw new AssertionError($message);
		}
		if ($1 == null) {
			throw new AssertionError($message);
		}
		if ($0.length != $1.length) {
			throw new AssertionError($message);
		}
		for (int $i = 0; $i < $0.length; $i++) {
			if ($0[$i] != $1[$i]) {
				throw new AssertionError($message);
			}
		}
	}
	
	public static void assertArrayEquals(final String $message, final short[] $0, final short[] $1) {
		if ($0 == null) {
			if ($1 == null) {
				return;
			}
			throw new AssertionError($message);
		}
		if ($1 == null) {
			throw new AssertionError($message);
		}
		if ($0.length != $1.length) {
			throw new AssertionError($message);
		}
		for (int $i = 0; $i < $0.length; $i++) {
			if ($0[$i] != $1[$i]) {
				throw new AssertionError($message);
			}
		}
	}
	
	public static void assertArrayEquals(final String $message, final long[] $0, final long[] $1) {
		if ($0 == null) {
			if ($1 == null) {
				return;
			}
			throw new AssertionError($message);
		}
		if ($1 == null) {
			throw new AssertionError($message);
		}
		if ($0.length != $1.length) {
			throw new AssertionError($message);
		}
		for (int $i = 0; $i < $0.length; $i++) {
			if ($0[$i] != $1[$i]) {
				throw new AssertionError($message);
			}
		}
	}
	
	public static void assertArrayEquals(final String $message, final int[] $0, final int[] $1) {
		if ($0 == null) {
			if ($1 == null) {
				return;
			}
			throw new AssertionError($message);
		}
		if ($1 == null) {
			throw new AssertionError($message);
		}
		if ($0.length != $1.length) {
			throw new AssertionError($message);
		}
		for (int $i = 0; $i < $0.length; $i++) {
			if ($0[$i] != $1[$i]) {
				throw new AssertionError($message);
			}
		}
	}

	public static void assertArrayEquals(final char[] $0, final char[] $1) {
		assertArrayEquals(null, $0, $1);
	}
	
	public static void assertArrayEquals(final byte[] $0, final byte[] $1) {
		assertArrayEquals(null, $0, $1);
	}
	
	public static void assertArrayEquals(final float[] $0, final float[] $1) {
		assertArrayEquals(null, $0, $1);
	}
	
	public static void assertArrayEquals(final double[] $0, final double[] $1) {
		assertArrayEquals(null, $0, $1);
	}
	
	public static void assertArrayEquals(final long[] $0, final long[] $1) {
		assertArrayEquals(null, $0, $1);
	}
	

	public static void assertArrayEquals(final int[] $0, final int[] $1) {
		assertArrayEquals(null, $0, $1);
	}
	
}
