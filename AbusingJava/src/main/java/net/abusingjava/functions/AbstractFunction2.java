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

@Author("Julian Fleischer")
@Version("2011-07-17")
public abstract class AbstractFunction2<I, J, O> implements Function2<I,J,O> {

	@Override
	public Function<I,O> asFunction(final J $arg2) {
		final AbstractFunction2<I,J,O> $parent = this;
		return new Function<I,O>() {
			@Override
			public O call(final I $arg) {
				return $parent.call($arg, $arg2);
			}
		};
	}

	@Override
	public Function<J,O> asFunctionFlipped(final I $arg) {
		final AbstractFunction2<I,J,O> $parent = this;
		return new Function<J,O>() {
			@Override
			public O call(final J $arg2) {
				return $parent.call($arg, $arg2);
			}
		};
	}

}
