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
public class GenericFunction2<I, J, O> extends AbstractFunction2<I,J,O> {

	final Method $underlyingMethod;
	final Object $methodOwner;

	
	public GenericFunction2(final Class<?> $class, final String $methodName,
							final Class<I> $argType, final Class<J> $argType2) {
		try {
			$underlyingMethod = $class.getMethod($methodName, $argType, $argType2);
			$methodOwner = null;
		} catch (java.lang.NoSuchMethodException $exc) {
			throw new NoSuchMethodException($exc);
		}
	}


	public GenericFunction2(final Object $obj, final String $methodName,
							final Class<I> $argType, final Class<J> $argType2) {
		try {
			$underlyingMethod = $obj.getClass().getMethod($methodName, $argType, $argType2);
			$methodOwner = $obj;
		} catch (java.lang.NoSuchMethodException $exc) {
			throw new NoSuchMethodException($exc);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public O call(final I $arg1, final J $arg2) {
		try {
			return (O) $underlyingMethod.invoke($methodOwner, $arg1, $arg2);
		} catch (Exception $exc) {
			throw new DynamicInvocationTargetException($exc);
		}
	}

}
