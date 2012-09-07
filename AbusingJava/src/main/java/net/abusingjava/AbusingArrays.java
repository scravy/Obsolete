/* Part of the AbusingJava-Library.
 * 
 * Source:  http://github.com/scravy/AbusingJava
 * Home:    http://www.abusingjava.net/
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.abusingjava;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Useful functions for dealing with arrays.
 */
@Author("Julian Fleischer")
@Version("2011-11-17")
@Since("2011-07")
final public class AbusingArrays {

	private AbusingArrays() {
	}

	/**
	 * Obtain an Iterator which walks the given array in reverse.
	 * <p>
	 * How to use (given <code>Type[] $array</code>):<br>
	 * <code>for (Type $elem : reverse($array)) { ... }</code>
	 */
	public static <T> Iterable<T> reverse(final T[] $array) {
		return new ArrayReverse<T>($array);
	}

	/**
	 * Obtain an Iterator which walks through the given array.
	 * <p>
	 * How to use (given <code>Type[] $array</code>):<br>
	 * <code>for (Type $elem : array($array)) { ... }</code>
	 */
	public static <T> Iterable<T> array(final T[] $array) {
		return new Array<T>($array);
	}

	/**
	 * Reverse the given array. This allocates a new array of type T[].
	 * <p>
	 * The time- and space-complexity is (n), since the elements are copied in a
	 * loop. Please check if you really need to use this function. Most of the
	 * times, converting the array to a list or simply iterating it backwards
	 * will suffice and work more efficiently.
	 * <p>
	 * How to use:<br>
	 * <code>Type[] $reverse = AbusingArrays.reverseArray($array);</code>.
	 */
	public static <T> T[] reverseArray(final T[] $array) {
		@SuppressWarnings("unchecked")
		T[] $arrayReverse = (T[]) java.lang.reflect.Array.newInstance($array.getClass().getComponentType(),
				$array.length);
		for (int $i = 0; $i < $array.length; $i++) {
			$arrayReverse[$array.length - $i - 1] = $array[$i];
		}
		return $arrayReverse;
	}

	/**
	 * Obtain an array from anything which is {@link java.lang.Iterable}.
	 * <p>
	 * Sample usage:<br />
	 * <code>List<String> $list = new LinkedList<String>();<br>
	 * $list.add("hello array");<br>
	 * String[] $strings = AbusingArrays.toArray($list);</code>
	 */
	public static <T> T[] toArray(final Iterable<T> $iterable) {
		List<T> $list = new LinkedList<T>();
		for (T $item : $iterable) {
			$list.add($item);
		}
		if ($list.size() == 0) {
			@SuppressWarnings("unchecked")
			T[] $array = (T[]) new Object[]{};
			return $array;
		}
		@SuppressWarnings("unchecked")
		T[] $array = (T[]) java.lang.reflect.Array.newInstance($list.get(0).getClass(), $list.size());
		return $list.toArray($array);
	}

	/**
	 * Create array based on the $sample (with the same component type as
	 * $sample) with given $length.
	 */
	public static <T> T[] createArray(final T[] $sample, final int $length) {
		@SuppressWarnings("unchecked")
		T[] $array = (T[]) java.lang.reflect.Array.newInstance(
				$sample.getClass().getComponentType(),
				$length);
		return $array;
	}

	/**
	 * Create an array with the given $componentType and $length.
	 */
	public static <T> T[] createArray(final Class<T> $componentType, final int $length) {
		@SuppressWarnings("unchecked")
		T[] $array = (T[]) java.lang.reflect.Array.newInstance($componentType, $length);
		return $array;
	}

	/**
	 * Concatenate many arrays into one. The new array will have the sum of the
	 * lengths of the arrays as length.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] concat(final T[]... $arrays) {
		T[] $array;
		int $length = 0;

		for (T[] $arr : $arrays) {
			if ($arr == null)
				continue;
			$length += $arr.length;
		}
		$array = createArray((Class<T>) $arrays.getClass().getComponentType().getComponentType(), $length);
		$length = 0;
		for (T[] $arr : $arrays) {
			if ($arr == null)
				continue;
			System.arraycopy($arr, 0, $array, $length, $arr.length);
			$length += $arr.length;
		}
		return $array;
	}

	/**
	 * Creates a new array with one more element $e. The new array will have a
	 * length of $arr.length + 1.
	 */
	public static <T> T[] add(final T[] $arr, final T $e) {
		T[] $array = createArray($arr, $arr.length + 1);
		System.arraycopy($arr, 0, $array, 0, $arr.length);
		$array[$arr.length] = $e;
		return $array;
	}

	public static <T> T[] shift(final T[] $arr, final T $e) {
		T[] $array = createArray($arr, $arr.length + 1);
		System.arraycopy($arr, 0, $array, 1, $arr.length);
		$array[0] = $e;
		return $array;
	}

	public static <T> T[] add(final T $e, final T[] $arr) {
		T[] $array = createArray($arr, $arr.length + 1);
		System.arraycopy($arr, 0, $array, 1, $arr.length);
		$array[0] = $e;
		return $array;
	}

	/**
	 * Checks if two arrays contain the same objects, i.e. if
	 * $arr1[i].equals($arr2[i]) for all i < $arr1.length. If $arr1.length !=
	 * $arr2.length, false is returned.
	 */
	public static boolean equals(final Object[] $arr1, final Object[] $arr2) {
		if ($arr1.length != $arr2.length)
			return false;
		for (int $i = 0; $i < $arr1.length; $i++) {
			if ($arr1[$i] == null) {
				if ($arr2[$i] == null) {
					continue;
				}
				return false;
			}
			if (!$arr1[$i].equals($arr2[$i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if two arrays contain the same values (double version), i.e. if
	 * $arr1[i].equals($arr2[i]) for all i < $arr1.length. If $arr1.length !=
	 * $arr2.length, false is returned.
	 */
	public static boolean equals(final double[] $arr1, final double[] $arr2) {
		if ($arr1.length != $arr2.length)
			return false;
		for (int $i = 0; $i < $arr1.length; $i++)
			if (!($arr1[$i] == $arr2[$i]))
				return false;
		return true;
	}

	/**
	 * Checks if two arrays contain the same values (float version), i.e. if
	 * $arr1[i].equals($arr2[i]) for all i < $arr1.length. If $arr1.length !=
	 * $arr2.length, false is returned.
	 */
	public static boolean equals(final float[] $arr1, final float[] $arr2) {
		if ($arr1.length != $arr2.length)
			return false;
		for (int $i = 0; $i < $arr1.length; $i++)
			if (!($arr1[$i] == $arr2[$i]))
				return false;
		return true;
	}

	/**
	 * Checks if two arrays contain the same values (byte version), i.e. if
	 * $arr1[i].equals($arr2[i]) for all i < $arr1.length. If $arr1.length !=
	 * $arr2.length, false is returned.
	 */
	public static boolean equals(final byte[] $arr1, final byte[] $arr2) {
		if ($arr1.length != $arr2.length)
			return false;
		for (int $i = 0; $i < $arr1.length; $i++)
			if (!($arr1[$i] == $arr2[$i]))
				return false;
		return true;
	}

	/**
	 * Checks if two arrays contain the same values (char version), i.e. if
	 * $arr1[i].equals($arr2[i]) for all i < $arr1.length. If $arr1.length !=
	 * $arr2.length, false is returned.
	 */
	public static boolean equals(final char[] $arr1, final char[] $arr2) {
		if ($arr1.length != $arr2.length)
			return false;
		for (int $i = 0; $i < $arr1.length; $i++)
			if (!($arr1[$i] == $arr2[$i]))
				return false;
		return true;
	}

	/**
	 * Checks if two arrays contain the same values (short version), i.e. if
	 * $arr1[i].equals($arr2[i]) for all i < $arr1.length. If $arr1.length !=
	 * $arr2.length, false is returned.
	 */
	public static boolean equals(final short[] $arr1, final short[] $arr2) {
		if ($arr1.length != $arr2.length)
			return false;
		for (int $i = 0; $i < $arr1.length; $i++)
			if (!($arr1[$i] == $arr2[$i]))
				return false;
		return true;
	}

	/**
	 * Checks if two arrays contain the same values (boolean version), i.e. if
	 * $arr1[i].equals($arr2[i]) for all i < $arr1.length. If $arr1.length !=
	 * $arr2.length, false is returned.
	 */
	public static boolean equals(final boolean[] $arr1, final boolean[] $arr2) {
		if ($arr1.length != $arr2.length)
			return false;
		for (int $i = 0; $i < $arr1.length; $i++)
			if (!($arr1[$i] == $arr2[$i]))
				return false;
		return true;
	}

	/**
	 * Checks if two arrays contain the same values (long version), i.e. if
	 * $arr1[i].equals($arr2[i]) for all i < $arr1.length. If $arr1.length !=
	 * $arr2.length, false is returned.
	 */
	public static boolean equals(final long[] $arr1, final long[] $arr2) {
		if ($arr1.length != $arr2.length)
			return false;
		for (int $i = 0; $i < $arr1.length; $i++)
			if (!($arr1[$i] == $arr2[$i]))
				return false;
		return true;
	}

	/**
	 * Determines if the specified object (in terms of equals()) is contained in
	 * a given array
	 */
	public static <T, U extends T> boolean contains(final T[] $arr, final U $elem) {
		for (Object $e : $arr) {
			if ($e.equals($elem))
				return true;
		}
		return false;
	}

	/**
	 * Determines whether the specified $array contains the given $value or not.
	 */
	public static boolean contains(final int[] $array, final int $value) {
		for (int $i : $array) {
			if ($i == $value) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines whether the specified $array contains the given $value or not.
	 */
	public static boolean contains(final long[] $array, final long $value) {
		for (long $i : $array) {
			if ($i == $value) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines whether the specified $array contains the given $value or not.
	 */
	public static boolean contains(final char[] $array, final char $value) {
		for (char $i : $array) {
			if ($i == $value) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines whether the specified $array contains the given $value or not.
	 */
	public static boolean contains(final byte[] $array, final byte $value) {
		for (byte $i : $array) {
			if ($i == $value) {
				return true;
			}
		}
		return false;
	}

	public static int indexOf(final int[] $array, final int $value) {
		for (int $i = 0; $i < $array.length; $i++) {
			if ($array[$i] == $value) {
				return $i;
			}
		}
		return -1;
	}

	public static int lastIndexOf(final int[] $array, final int $value) {
		for (int $i = $array.length - 1; $i >= 0; $i--) {
			if ($array[$i] == $value) {
				return $i;
			}
		}
		return -1;
	}

	public static int indexOf(final long[] $array, final long $value) {
		for (int $i = 0; $i < $array.length; $i++) {
			if ($array[$i] == $value) {
				return $i;
			}
		}
		return -1;
	}

	public static int lastIndexOf(final long[] $array, final long $value) {
		for (int $i = $array.length - 1; $i >= 0; $i--) {
			if ($array[$i] == $value) {
				return $i;
			}
		}
		return -1;
	}

	public static int indexOf(final char[] $array, final char $value) {
		for (int $i = 0; $i < $array.length; $i++) {
			if ($array[$i] == $value) {
				return $i;
			}
		}
		return -1;
	}

	public static int lastIndexOf(final char[] $array, final char $value) {
		for (int $i = $array.length - 1; $i >= 0; $i--) {
			if ($array[$i] == $value) {
				return $i;
			}
		}
		return -1;
	}

	public static int indexOf(final short[] $array, final short $value) {
		for (int $i = 0; $i < $array.length; $i++) {
			if ($array[$i] == $value) {
				return $i;
			}
		}
		return -1;
	}

	public static int lastIndexOf(final short[] $array, final short $value) {
		for (int $i = $array.length - 1; $i >= 0; $i--) {
			if ($array[$i] == $value) {
				return $i;
			}
		}
		return -1;
	}

	public static int indexOf(final byte[] $array, final byte $value) {
		for (int $i = 0; $i < $array.length; $i++) {
			if ($array[$i] == $value) {
				return $i;
			}
		}
		return -1;
	}

	public static int lastIndexOf(final byte[] $array, final byte $value) {
		for (int $i = $array.length - 1; $i >= 0; $i--) {
			if ($array[$i] == $value) {
				return $i;
			}
		}
		return -1;
	}

	/**
	 * Determines if an object of the specified class is contained in a given
	 * array.
	 * <p>
	 * Example:<br>
	 * <code>containsType(new Object[] { 7, 8, 9 }, Long.class)</code><br>
	 * will return <var>false</var>, whereas
	 * <code>containsType(new Object[] { 7, 8, 10L }, Long.class}</code><br>
	 * will return <var>true</var>.
	 */
	public static <T, U extends T> boolean containsType(final T[] $arr, final Class<U> $elem) {
		for (Object $e : $arr) {
			if ($elem.isAssignableFrom($e.getClass()))
				return true;
		}
		return false;
	}

	/**
	 * Retrieves the first object of the given type from an array.
	 * <p>
	 * Example:<br>
	 * <code>firstOfType(new Object[] { 7, "hello", 9.3, 8, "world" }, java.lang.String.class)</code>
	 * <br>
	 * will return <tt>"hello"</tt>.
	 */
	@SuppressWarnings("unchecked")
	public static <T, U extends T> U firstOfType(final T[] $arr, final Class<U> $elem) {
		for (Object $e : $arr) {
			if ($elem.isAssignableFrom($e.getClass()))
				return (U) $e;
		}
		return null;
	}

	/**
	 * Swaps two values at the specified indexes in the $array of ints.
	 */
	public static void swap(final int[] $array, final int $1, final int $2) {
		int swapVar = $array[$1];
		$array[$1] = $array[$2];
		$array[$2] = swapVar;
	}

	/**
	 * Swaps two values at the specified indexes in the $array of longs.
	 */
	public static void swap(final long[] $array, final int $1, final int $2) {
		long swapVar = $array[$1];
		$array[$1] = $array[$2];
		$array[$2] = swapVar;
	}

	/**
	 * Swaps two values at the specified indexes in the $array.
	 */
	public static <T> void swap(final T[] $array, final int $index1, final int $index2) {
		T swapVar = $array[$index1];
		$array[$index1] = $array[$index2];
		$array[$index2] = swapVar;
	}

	/**
	 * Equivalent to <code>shuffle($array, $rand, $array.length);</code>.
	 */
	public static void shuffle(final long[] $array, final Random $rand) {
		shuffle($array, $rand, $array.length);
	}

	/**
	 * Shuffles the given $array of ints using the given Random-Number-Generator
	 * $rand doing $randomness many swaps.
	 * 
	 * @param $array
	 *            The array to shuffle.
	 * @param $rand
	 *            The random number generator used to gather randomness.
	 * @param $randomness
	 *            The number of swaps to be done. If to low, some indices will
	 *            not be touched at all.
	 */
	public static void shuffle(final long[] $array, final Random $rand, final int $randomness) {
		for (int i = 0; i < $randomness; i++) {
			swap($array, $rand.nextInt($array.length), $rand.nextInt($array.length));
		}
	}

	/**
	 * Equivalent to <code>shuffle($array, $rand, $array.length);</code>.
	 */
	public static void shuffle(final int[] $array, final Random $rand) {
		shuffle($array, $rand, $array.length);
	}

	/**
	 * Shuffles the given $array of ints using the given Random-Number-Generator
	 * $rand doing $randomness many swaps.
	 * 
	 * @param $array
	 *            The array to shuffle.
	 * @param $rand
	 *            The random number generator used to gather randomness.
	 * @param $randomness
	 *            The number of swaps to be done. If to low, some indices will
	 *            not be touched at all.
	 */
	public static void shuffle(final int[] $array, final Random $rand, final int $randomness) {
		for (int i = 0; i < $randomness; i++) {
			swap($array, $rand.nextInt($array.length), $rand.nextInt($array.length));
		}
	}

	/**
	 * Equivalent to <code>shuffle($array, $rand, $array.length);</code>.
	 */
	public static <T> void shuffle(final T[] $array, final Random $rand) {
		shuffle($array, $rand, $array.length);
	}

	/**
	 * Shuffles the given $array of Objects using the given
	 * Random-Number-Generator $rand doing $randomness many swaps.
	 * <p>
	 * 
	 * @param $array
	 *            The array to shuffle.
	 * @param $rand
	 *            The random number generator used to gather randomness.
	 * @param $randomness
	 *            The number of swaps to be done. If to low, some indices will
	 *            not be touched at all.
	 */
	public static <T> void shuffle(final T[] $array, final Random $rand, final int $randomness) {
		for (int i = 0; i < $randomness; i++) {
			swap($array, $rand.nextInt($array.length), $rand.nextInt($array.length));
		}
	}

	/**
	 * Returns a copy of the $array.
	 */
	public static int[] clone(final int[] $array) {
		int[] $newArray = new int[$array.length];
		System.arraycopy($array, 0, $newArray, 0, $array.length);
		return $newArray;
	}

	/**
	 * Returns a copy of the specified interval in the array.
	 */
	public static int[] clone(final int[] $array, final int $from, final int $to) {
		int[] $newArray = new int[$to - $from];
		System.arraycopy($array, $from, $newArray, 0, $to - $from);
		return $newArray;
	}

	/**
	 * Returns a copy of the $array.
	 */
	public static long[] clone(final long[] $array) {
		long[] $newArray = new long[$array.length];
		System.arraycopy($array, 0, $newArray, 0, $array.length);
		return $newArray;
	}

	/**
	 * Returns a copy of the specified interval in the array.
	 */
	public static long[] clone(final long[] $array, final int $from, final int $to) {
		long[] $newArray = new long[$to - $from];
		System.arraycopy($array, $from, $newArray, 0, $to - $from);
		return $newArray;
	}

	/**
	 * Returns a copy of the $array.
	 */
	public static char[] clone(final char[] $array) {
		char[] $newArray = new char[$array.length];
		System.arraycopy($array, 0, $newArray, 0, $array.length);
		return $newArray;
	}

	/**
	 * Returns a copy of the specified interval in the array.
	 */
	public static char[] clone(final char[] $array, final int $from, final int $to) {
		char[] $newArray = new char[$to - $from];
		System.arraycopy($array, $from, $newArray, 0, $to - $from);
		return $newArray;
	}

	/**
	 * Returns a copy of the $array.
	 */
	public static short[] clone(final short[] $array) {
		short[] $newArray = new short[$array.length];
		System.arraycopy($array, 0, $newArray, 0, $array.length);
		return $newArray;
	}

	/**
	 * Returns a copy of the specified interval in the array.
	 */
	public static short[] clone(final short[] $array, final int $from, final int $to) {
		short[] $newArray = new short[$to - $from];
		System.arraycopy($array, $from, $newArray, 0, $to - $from);
		return $newArray;
	}

	/**
	 * Returns a copy of the byte-$array.
	 */
	public static byte[] clone(final byte[] $array) {
		byte[] $newArray = new byte[$array.length];
		System.arraycopy($array, 0, $newArray, 0, $array.length);
		return $newArray;
	}

	/**
	 * Returns a copy of the specified interval in the array.
	 */
	public static byte[] clone(final byte[] $array, final int $from, final int $to) {
		byte[] $newArray = new byte[$to - $from];
		System.arraycopy($array, $from, $newArray, 0, $to - $from);
		return $newArray;
	}

	/**
	 * Returns a copy of the String-$array.
	 */
	public static String[] clone(final String[] $array) {
		String[] $newArray = new String[$array.length];
		System.arraycopy($array, 0, $newArray, 0, $array.length);
		return $newArray;
	}

	/**
	 * Returns a copy of the specified interval in the array.
	 */
	public static String[] clone(final String[] $array, final int $from, final int $to) {
		String[] $newArray = new String[$to - $from];
		System.arraycopy($array, $from, $newArray, 0, $to - $from);
		return $newArray;
	}

	/**
	 * Returns just a part of $array, split into $numberOfPieces, the piece
	 * numbered $pieceIndex. Numbering starts at zero.
	 */
	public static int[] split(final int $numberOfPieces, final int[] $array, final int $pieceIndex) {
		int $sliceLength = $array.length / $numberOfPieces;
		int $sliceStartIndex = $pieceIndex * $sliceLength;
		int $sliceEndIndex = (($pieceIndex + 1) * $sliceLength);
		if (($pieceIndex + 1) == $numberOfPieces) {
			$sliceEndIndex = $array.length;
		}
		$sliceLength = $sliceEndIndex - $sliceStartIndex;
		int[] $new = new int[$sliceLength];
		System.arraycopy($array, $sliceStartIndex, $new, 0, $sliceLength);
		return $new;
	}
}
