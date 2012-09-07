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
package net.abusingjava.functions;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.abusingjava.AbusingArrays;
import net.abusingjava.Author;
import net.abusingjava.Version;

/**
 * Collection of functions commonly known from functional programming languages or more dynamic languages.
 */
@Author("Julian Fleischer")
@Version("2011-07-24")
final public class AbusingFunctions {
	
	AbusingFunctions() {}
	
	/**
	 * Retrieves a callback to a static method, which is looked up based on its name.
	 * 
	 * @param $class
	 * 		The class which defines the method.
	 * @param $method
	 * 		The name of the method. Note that a callback is looked up when calling it,
	 * 		not when the callback is created. Thus, if there are multiple methods sharing
	 * 		a name, the actual methods being called depends on the arguments of the call.
	 * 
	 * @see Callable#call(Object...)
	 */
	public static Callable<?> callback(final Class<?> $class, final String $method) {
		return new Callable<Object>() {
			@Override
			public Object call(final Object... $args) {
				Class<?>[] $argTypes = new Class<?>[$args.length];
				for (int $i = 0; $i < $args.length; $i++) {
					$argTypes[$i] = $args[$i].getClass();
				}
				try {
					return $class.getMethod($method, $argTypes).invoke(null, $args);
				} catch (Exception $exc) {
					// TODO: Exception handling
					throw new DynamicInvocationTargetException($exc);
				}
			}
		};
	}

	/**
	 * Retrieves a callback to a method, which is looked up based on its name.
	 * 
	 * @param $obj
	 * 		The object on which the method is defined and will be invoked on when invoked.
	 * @param $method
	 * 		The name of the method. Note that a callback is looked up when calling it,
	 * 		not when the callback is created. Thus, if there are multiple methods sharing
	 * 		a name, the actual methods being called depends on the arguments of the call.
	 * 
	 * @see Callable#call(Object...)
	 */
	public static Callable<?> callback(
			final Object $obj,
			final String $method) {
		final Class<?> $class = $obj.getClass();
		return new Callable<Object>() {
			@Override
			public Object call(final Object... $args) {
				Class<?>[] $argTypes = new Class<?>[$args.length];
				for (int i = 0; i < $args.length; i++) {
					$argTypes[i] = $args[i].getClass();
				}
				try {
					return $class.getMethod($method, $argTypes).invoke($obj, $args);
				} catch (Exception $exc) {
					// TODO: Exception handling
					throw new DynamicInvocationTargetException($exc);
				}
			}
		};
	}

	public static Callable<?> accessibleCallback(
			final Object $obj,
			final String $method) {
		final Class<?> $class = $obj.getClass();
		return new Callable<Object>() {
			@Override
			public Object call(final Object... $args) {
				Class<?>[] $argTypes = new Class<?>[$args.length];
				for (int i = 0; i < $args.length; i++) {
					$argTypes[i] = $args[i].getClass();
				}
				try {
					Method $m = $class.getMethod($method, $argTypes);
					$m.setAccessible(true);
					return $m.invoke($obj, $args);
				} catch (Exception $exc) {
					// TODO: Exception handling
					throw new DynamicInvocationTargetException($exc);
				}
			}
		};
	}
	
	static <I, O> Function<I,O> function(
			final Callback $callback,
			final Class<I> $inType,
			final Class<O> $outType) {
		try {
			return $callback.getFunction($inType, $outType);
		} catch (Exception $exc) {
			// TODO: Exception handling
			return null;
		}
	}

	public static <I, J, O> Function2<I,J,O> function2(
			final Object $obj,
			final String $method,
			final Class<I> $inType,
			final Class<J> $inType2,
			final Class<O> $outType) {
		try {
			return new Callback($obj, $method).getFunction2($inType, $inType2, $outType);
		} catch (Exception $exc) {
			// TODO: Exception handling
			return null;
		}
	}
	
	public static <I, J, O> Function2<I,J,O> function2(
			final Class<?> $class,
			final String $method,
			final Class<I> $inType,
			final Class<J> $inType2,
			final Class<O> $outType) {
		try {
			return new Callback($class, $method).getFunction2($inType, $inType2, $outType);
		} catch (Exception $exc) {
			// TODO: Exception handling
			return null;
		}
	}
	
	static <I, J, O> Function2<I,J,O> function2(
			final Callable<?> $callable,
			final Class<I> $inType1,
			final Class<J> $inType2,
			final Class<O> $outType) {
		try {
			return function2($callable, "call", $inType1, $inType2, $outType);
		} catch (Exception $exc) {
			return null;
		}
	}
	
	public static <I, O> Function<I,O> function(
			final Class<?> $class,
			final String $method,
			final Class<I> $inType,
			final Class<O> $outType) {
		try {
			return new Callback($class, $method).getFunction($inType, $outType);
		} catch (Exception $exc) {
			// TODO: Exception handling
			return null;
		}
	}

	public static <I, O> Function<I,O> function(
			final Object $obj,
			final String $method,
			final Class<I> $inType,
			final Class<O> $outType) {
		try {
			return new Callback($obj, $method).getFunction($inType, $outType);
		} catch (Exception $exc) {
			return null;
		}
	}
	
	static <I, O> Function<I,O> function(
			final Callable<?> $callable,
			final Class<I> $inType,
			final Class<O> $outType) {
		try {
			return function($callable, "call", $inType, $outType);
		} catch (Exception $exc) {
			return null;
		}
	}
	

	public static <T> boolean all(final Function<? super Character,Boolean> $function, final String $string) {
		for (int $i = 0; $i < $string.length(); $i++)
			if (!$function.call($string.charAt($i))) return false;
		return true;
	}

	public static <T> boolean all(final Function<? super T,Boolean> $function, final Iterable<T> $iterable) {
		for (T $elem : $iterable)
			if (!$function.call($elem)) return false;
		return true;
	}

	public static <T> boolean all(final Function<? super T,Boolean> $function, final T[] $array) {
		for (T $elem : $array)
			if (!$function.call($elem)) return false;
		return true;
	}

	public static <T> boolean any(final Function<? super Character,Boolean> $function, final String $string) {
		for (int $i = 0; $i < $string.length(); $i++)
			if ($function.call($string.charAt($i))) return true;
		return false;
	}

	public static <T> boolean any(final Function<? super T,Boolean> $function, final Iterable<T> $array) {
		for (T $elem : $array)
			if ($function.call($elem)) return true;
		return false;
	}

	public static <T> boolean any(final Function<? super T,Boolean> $function, final T[] $array) {
		for (T $elem : $array)
			if ($function.call($elem)) return true;
		return false;
	}

	public static <T> List<T> drain(final Function<? super T,Boolean> $function, final Iterable<T> $collection) {
		ArrayList<T> $result = new ArrayList<T>();
		for (T $elem : $collection)
			if (!$function.call($elem)) $result.add($elem);
		return $result;
	}

	public static <T> List<T> drain(final Function<? super T,Boolean> $function, final List<T> $collection) {
		ArrayList<T> $result = new ArrayList<T>($collection.size());
		for (T $elem : $collection)
			if (!$function.call($elem)) $result.add($elem);
		return $result;
	}

	public static <T> T[] drain(final Function<? super T,Boolean> $function, final T[] $array) {
		if ($array.length == 0) return $array;
		ArrayList<T> $result = new ArrayList<T>($array.length);
		for (int i = 0; i < $array.length; i++)
			if (!$function.call($array[i])) $result.add($array[i]);
		@SuppressWarnings("unchecked")
		T[] resultArray = (T[]) Array.newInstance($array.getClass().getComponentType(), $result.size());
		for (int i = 0; i < $result.size(); i++)
			resultArray[i] = $result.get(i);
		return resultArray;
	}

	public static <T> void drainInPlace(final Function<? super T,Boolean> $function, final Iterable<T> $collection) {
		Iterator<T> $iterator = $collection.iterator();
		for (T $elem = $iterator.next(); $iterator.hasNext();)
			if ($function.call($elem)) $iterator.remove();
	}

	public static <T> String dropWhile(final Function<? super Character,Boolean> $function, final String $string) {
		int $i;
		for ($i = 0; $i < $string.length(); $i++)
			if (!$function.call($string.charAt($i))) break;
		return $string.substring($i, $string.length());
	}

	public static <T> List<T> dropWhile(final Function<? super T,Boolean> $function, final Iterable<T> $iterable) {
		List<T> $list = new LinkedList<T>();
		Iterator<T> $iterator = $iterable.iterator();
		T $elem;
		while ($iterator.hasNext()) {
			$elem = $iterator.next();
			if (!$function.call($elem)) {
				$list.add($elem);
				break;
			}
		}
		while ($iterator.hasNext())
			$list.add($iterator.next());
		return $list;
	}

	public static <T> T[] dropWhile(final Function<? super T,Boolean> $function, final T[] $array) {
		if ($array.length == 0) return $array;
		int $i = 0;
		for (T $elem : $array) {
			if (!$function.call($elem)) break;
			$i++;
		}
		T[] $resultArray = AbusingArrays.createArray($array, $i);
		System.arraycopy($array, $i, $resultArray, 0, $array.length - $i);
		return $resultArray;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] filter(final Function<? super T,Boolean> $function, final Iterable<T> $collection) {
		if ($collection.iterator().hasNext()) {
			ArrayList<T> $result = new ArrayList<T>();
			for (T $elem : $collection) {
				if ($function.call($elem)) {
					$result.add($elem);
				}
			}
			T[] $resultArray = AbusingArrays.createArray((Class<T>) $result.get(0).getClass(), $result.size());
			for (int $i = 0; $i < $result.size(); $i++) {
				$resultArray[$i] = $result.get($i);
			}
			return $resultArray;
		}
		return (T[]) new Object[0];
	}

	public static <T> List<T> filter(final Function<? super T,Boolean> $function, final List<T> $list) {
		ArrayList<T> $result = new ArrayList<T>($list.size());
		for (T $elem : $list) {
			if ($function.call($elem)) {
				$result.add($elem);
			}
		}
		return $result;
	}

	public static <T> T[] filter(final Function<? super T,Boolean> $function, final T[] $array) {
		if ($array.length == 0) return $array;
		ArrayList<T> $result = new ArrayList<T>($array.length);
		for (int i = 0; i < $array.length; i++) {
			if ($function.call($array[i])) {
				$result.add($array[i]);
			}
		}
		T[] $resultArray = AbusingArrays.createArray($array, $result.size());
		for (int $i = 0; $i < $result.size(); $i++) {
			$resultArray[$i] = $result.get($i);
		}
		return $resultArray;
	}

	public static <T> void filterInPlace(final Function<? super T,Boolean> $function, final Iterable<T> $iterable) {
		Iterator<T> $iterator = $iterable.iterator();
		while ($iterator.hasNext()) {
			T $elem = $iterator.next();
			if (!$function.call($elem)) $iterator.remove();
		}
	}

	public static <I, J> I foldl(final Function2<I,J,I> $function, I $first, final Iterable<J> $iterable) {
		for (J $elem : $iterable) {
			$first = $function.call($first, $elem);
		}
		return $first;
	}

	public static <I, J> I foldl(final Function2<I,J,I> $function, I $first, final J[] $array) {
		for (int $i = 0; $i < $array.length; $i++) {
			$first = $function.call($first, $array[$i]);
		}
		return $first;
	}

	public static <I, J> J foldr(final Function2<I,J,J> $function, J $first, final I[] $array) {
		for (int $i = 0; $i < $array.length; $i++) {
			$first = $function.call($array[$i], $first);
		}
		return $first;
	}

	public static <I, J> J foldr(final Function2<I,J,J> f, J $first, final Iterable<I> $iterable) {
		for (I elem : $iterable) {
			$first = f.call(elem, $first);
		}
		return $first;
	}

	@SuppressWarnings("unchecked")
	public static <I, O, L extends List<O>> L map(final Callable<? super I> $callback,
													 final I[] $collection,
													 final L $target,
													 final Object... $additionalArgs) {
		Object[] $args = new Object[$additionalArgs.length + 1];
		for (int $i = 0; $i < $additionalArgs.length; $i++)
			$args[$i + 1] = $additionalArgs[$i];
		for (I $elem : $collection) {
			$args[0] = $elem;
			$target.add((O) $callback.call($args));
		}
		return $target;
	}

	@SuppressWarnings("unchecked")
	public static <I, O, L extends List<O>> L map(final Callable<? super I> $callback,
													 final Iterable<I> $collection,
													 final L $target,
													 final Object... $additionalArgs) {
		Object[] $args = new Object[$additionalArgs.length + 1];
		for (int $i = 0; $i < $additionalArgs.length; $i++)
			$args[$i + 1] = $additionalArgs[$i];
		for (I $elem : $collection) {
			$args[0] = $elem;
			$target.add((O) $callback.call($args));
		}
		return $target;
	}

	@SuppressWarnings("unchecked")
	public static <I, O> O[] map(final Function<? super I,O> $function, final I[] $collection) {
		if ($collection.length > 0) {
			ArrayList<O> $result = new ArrayList<O>($collection.length);
			for (I $elem : $collection) {
				$result.add($function.call($elem));
			}
			O[] $array = (O[]) Array.newInstance($result.get(0).getClass(), $result.size());
			for (int $i = 0; $i < $result.size(); $i++) {
				$array[$i] = $result.get($i);
			}
			return $array;
		}
		return (O[]) new Object[0];
	}
	
	/**
	 * Invokes the named method on each element in the array.
	 * 
	 * @return The same array.
	 */
	@SuppressWarnings("unused")
	public static <T> T[] map(final String $method, final T[] $array, final Object... $args) {
		// TODO: Implement this.
		return null;
	}
	
	/**
	 * 
	 * @return A newly created array.
	 */
	@SuppressWarnings("unused")
	public static <I, O> I[] map(final String $method, final O[] $returnType, final I[] $array, final Object... $args) {
		// TODO: Implement this.
		return null;
	}

	public static <I, O> List<O> map(final Function<? super I,O> $function, final Iterable<I> $collection) {
		List<O> $result = new LinkedList<O>();
		for (I $elem : $collection)
			$result.add($function.call($elem));
		return $result;
	}

	public static <T> void mapInPlace(final Callable<T> $callable, final T[] $collection,
									  final Object... $additionalArgs) {
		Object[] $args = new Object[$additionalArgs.length + 1];
		for (int $i = 0; $i < $additionalArgs.length; $i++) {
			$args[$i + 1] = $additionalArgs[$i];
		}
		for (int $i = 0; $i < $collection.length; $i++) {
			$args[0] = $collection[$i];
			$collection[$i] = $callable.call($args);
		}
	}

	public static <T> void mapInPlace(final Function<? super T,T> $function, final T[] $collection) {
		for (int i = 0; i < $collection.length; i++)
			$collection[i] = $function.call($collection[i]);
	}

	public static <T> String takeWhile(final Function<? super Character,Boolean> $function, final String $string) {
		int $i;
		for ($i = 0; $i < $string.length(); $i++) {
			if (!$function.call($string.charAt($i))) {
				break;
			}
		}
		return $string.substring(0, $i);
	}

	public static <T> List<T> takeWhile(final Function<? super T,Boolean> $function, final Iterable<T> $iterable) {
		List<T> $list = new LinkedList<T>();
		Iterator<T> $iterator = $iterable.iterator();
		T $elem;
		while ($iterator.hasNext()) {
			$elem = $iterator.next();
			if (!$function.call($elem)) {
				break;
			}
			$list.add($elem);
		}
		return $list;
	}

	public static <T> T[] takeWhile(final Function<? super T,Boolean> $function, final T[] $array) {
		if ($array.length == 0) {
			return $array;
		}
		int $i = 0;
		for (T elem : $array) {
			if (!$function.call(elem)) {
				break;
			}
			$i++;
		}
		T[] $resultArray = AbusingArrays.createArray($array, $i);
		System.arraycopy($array, 0, $resultArray, 0, $i);
		return $resultArray;
	}

	@SuppressWarnings("unchecked")
	public static <I, J, O> O[] zipWith(final Function2<I,J,O> $function, final I[] $a, final J[] $b) {
		int $size = Math.min($a.length, $b.length);
		if ($size == 0) {
			return (O[]) new Object[0];
		}
		O $fst = $function.call($a[0], $b[0]);
		O[] $resultArray = AbusingArrays.createArray((Class<O>) $fst.getClass(), $size);
		$resultArray[0] = $fst;
		for (int $i = 1; $i < $size; $i++) {
			$resultArray[$i] = $function.call($a[$i], $b[$i]);
		}
		return $resultArray;
	}

	@SuppressWarnings("unchecked")
	public static <I, J, O> O[] zipWith(final Function2<I,J,O> $function, final I[] $a, final List<J> $b) {
		int $size = Math.min($a.length, $b.size());
		if ($size == 0) return (O[]) new Object[0];
		O $fst = $function.call($a[0], $b.get(0));
		O[] $resultArray = (O[]) Array.newInstance($fst.getClass(), $size);
		$resultArray[0] = $fst;
		for (int i = 1; i < $size; i++) {
			$resultArray[i] = $function.call($a[i], $b.get(i));
		}
		return $resultArray;
	}

	public static <I, J, O> List<O> zipWith(final Function2<I,J,O> $function, final List<I> $a, final J[] $b)
			throws IllegalArgumentException {
		int $size = Math.min($a.size(), $b.length);
		List<O> $result = new ArrayList<O>($size);
		for (int i = 0; i < $size; i++) {
			$result.add($function.call($a.get(i), $b[i]));
		}
		return $result;
	}

	public static <I, J, O> List<O> zipWith(final Function2<I,J,O> $function, final List<I> $a, final List<J> $b)
			throws IllegalArgumentException {
		int $size = Math.min($a.size(), $b.size());
		List<O> $result = new ArrayList<O>($size);
		for (int $i = 0; $i < $size; $i++) {
			$result.add($function.call($a.get($i), $b.get($i)));
		}
		return $result;
	}

}
