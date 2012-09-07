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

import static net.abusingjava.functions.AbusingFunctions.callback;
import net.abusingjava.Author;
import net.abusingjava.Version;

@Author("Julian Fleischer")
@Version("2011-07-17")
public class Call<ReturnType> implements Callable<ReturnType> {
	
	private final Callable<ReturnType> $callable;

	private final Object[] $args;

	public Call(final Callable<ReturnType> $callable, final Object... $args) {
		this.$callable = $callable;
		this.$args = $args;
	}

	@SuppressWarnings("unchecked")
	public Call(final Class<?> $class, final String $method, final Object... $args) {
		$callable = (Callable<ReturnType>) callback($class, $method);
		this.$args = $args;
	}

	@SuppressWarnings("unchecked")
	public Call(final Object obj, final String $method, final Object... $args) {
		$callable = (Callable<ReturnType>) callback(obj, $method);
		this.$args = $args;
	}

	@Override
	public ReturnType call(final Object... $arguments) {
		for (int $i = 0; ($i < $args.length) && ($i < $arguments.length); $i++) {
			$args[$i] = $arguments[$i];
		}
		return $callable.call($args);
	}
}
