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

import java.lang.reflect.Method;

import net.abusingjava.Author;
import net.abusingjava.Null;
import net.abusingjava.Version;


/**
 * A Callback allows for calling a function which is looked up by its name at runtime (that is: when
 * issuing the call).
 * <p>
 * A Callback is a reference to a method using a name. This implies that if there is more than one
 * method with that name the actual method is looked up by its name and type arguments when calling
 * the callback (using call()).
 * <p>
 * Future thoughts: A callback may respect an annotation like @Default. However, this might impose
 * to heavy load on the runtime system, a solution using the approach Projekt Lombok has taken might
 * be more effective.
 */
@Author("Julian Fleischer")
@Version("2011-07-17")
public class Callback implements Callable<Object> {

	private final Object $obj;

	private final String $method;

	private final Class<?> $class;


	public Callback(final Class<?> $class, final String $method) {
		$obj = null;
		this.$class = $class;
		this.$method = $method;
	}

	public Callback(final Object obj, final String method) {
		this.$obj = obj;
		this.$method = method;
		$class = obj.getClass();
	}

	@Override
	public Object call(final Object... $args) {
		Class<?>[] $argTypes = new Class<?>[$args.length];
		for (int $i = 0; $i < $args.length; $i++) {
			$argTypes[$i] = $args[$i].getClass();
		}
		try {
			try {
				return $class.getMethod($method, $argTypes).invoke($obj, $args);
			} catch (NoSuchMethodException $exc) {
				outer: for (Method $m : $class.getMethods()) {
					Class<?>[] $types = $m.getParameterTypes();
					if ($types.length != $argTypes.length)
						continue;
					for (int $j = 0; $j < $args.length; $j++) {
						if (!$types[$j].isAssignableFrom($argTypes[$j]))
							continue outer;
					}
					return $m.invoke($obj, $args);
				}
				throw $exc;
			}
		} catch (Exception $exc) {
			return Null.OBJECT;
		}
	}

	/**
	 * 
	 */
	public <I, O> Function<I,O> getFunction(final Class<I> $in, @SuppressWarnings("unused") final Class<O> $out)
			throws SecurityException, NoSuchMethodException {
		if ($obj == null) {
			return new GenericFunction<I,O>($class, $method, $in);
		}
		return new GenericFunction<I,O>($obj, $method, $in);
	}

	/**
	 * 
	 */
	public <I, J> Function<I,?> getFunction(final Class<I> $argType, final J $arg2) {
		try {
			Method $m = $class.getMethod($method, $argType, $arg2.getClass());
			return getFunction($argType, $arg2, $m.getReturnType());
		} catch (java.lang.NoSuchMethodException $exc) {
			throw new NoSuchMethodException($exc);
		}
	}

	/**
	 * 
	 */
	public <I, J, O> Function<I,O> getFunction(final Class<I> $argType, final J $arg2,
											   final Class<O> $returnType)
			throws SecurityException, NoSuchMethodException {
		@SuppressWarnings("unchecked")
		Function2<I,J,O> $f2 = (Function2<I,J,O>) getFunction2($argType, $arg2.getClass(), $returnType);
		return $f2.asFunction($arg2);
	}

	/**
	 * 
	 */
	public <ReturnType> Function<ReturnType,?> getFunction(final Class<ReturnType> $argType) {
		try {
			Method $m = $class.getMethod($method, $argType);
			return getFunction($argType, $m.getReturnType());
		} catch (java.lang.NoSuchMethodException $exc) {
			throw new NoSuchMethodException($exc);
		}
	}

	/**
	 * 
	 */
	public <I, J> Function2<I,J,?> getFunction2(final Class<I> $argType, final Class<J> $argType2) {
		try {
			Method $m = $class.getMethod($method, $argType, $argType2);
			return getFunction2($argType, $argType2, $m.getReturnType());
		} catch (java.lang.NoSuchMethodException $exc) {
			throw new NoSuchMethodException($exc);
		}
	}

	/**
	 * 
	 */
	public <I, J, O> Function2<I,J,O> getFunction2(final Class<I> $in, final Class<J> $in2,
												   @SuppressWarnings("unused") final Class<O> $out)
			throws SecurityException, NoSuchMethodException {
		if ($obj == null) {
			return new GenericFunction2<I,J,O>($class, $method, $in, $in2);
		}
		return new GenericFunction2<I,J,O>($obj, $method, $in, $in2);
	}
}
