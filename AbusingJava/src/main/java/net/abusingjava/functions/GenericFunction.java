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
import net.abusingjava.Version;


@Author("Julian Fleischer")
@Version("2011-06-07")
public class GenericFunction<I, O> implements Function<I,O> {

	final private Method $underlyingMethod;
	final private Object $methodOwner;


	public GenericFunction(final Class<?> $class, final String $methodName, final Class<I> $argType) {
		try {
			$underlyingMethod = $class.getMethod($methodName, $argType);
			$methodOwner = null;
		} catch (java.lang.NoSuchMethodException $exc) {
			throw new NoSuchMethodException($exc);
		}
	}


	public GenericFunction(final Object $obj, final String $methodName, final Class<I> $argType) {
		try {
			$underlyingMethod = $obj.getClass().getMethod($methodName, $argType);
			$methodOwner = $obj;
		} catch (java.lang.NoSuchMethodException $exc) {
			throw new NoSuchMethodException($exc);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public O call(final I $arg) {
		try {
			return (O) $underlyingMethod.invoke($methodOwner, $arg);
		} catch (Exception $exc) {
			throw new DynamicInvocationTargetException($exc);
		}
	}
}
