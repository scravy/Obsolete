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

/**
 * A function that checks whether a given object is an instance of a given class.
 */
@Author("Julian Fleischer")
public class InstanceOf extends AbstractFunction2<Object,Class<?>,Boolean> {

	@Override
	public Boolean call(final Object obj, final Class<?> clazz) {
		return clazz.isAssignableFrom(obj.getClass());
	}
}
