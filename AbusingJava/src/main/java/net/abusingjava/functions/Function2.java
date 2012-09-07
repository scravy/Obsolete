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

import net.abusingjava.Author;
import net.abusingjava.Version;

/**
 * Represents a function that takes two arguments and returns a result.
 *
 * @param <I> The Type of the first argument to this function (in)
 * @param <J> The Type of the second argument to this function (in)
 * @param <O> The return type of this function (out)
 * 
 * @see Function
 * @see Callable
 */
@Author("Julian Fleischer")
@Version("2011-07-24")
public interface Function2<I, J, O> {

	/**
	 * Retrieves a function (one argument) with the second argument already applied.
	 * 
	 * @param $arg The second argument for this Function2.
	 * @return A Function which is this Function2 but with the second argument already fixed.
	 */
	public Function<I,O> asFunction(J $arg);

	/**
	 * Retrieves a function (one argument) with the first argument already applied.
	 * 
	 * @param $arg The first argument for this Function2.
	 * @return A Function which is this Function2 but with the first argument already fixed.
	 */
	public Function<J,O> asFunctionFlipped(I $arg);

	/**
	 * Invokes this function with the first and second argument $in1 and $in2.
	 */
	public O call(I $in1, J $in2);
}
