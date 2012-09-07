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


import java.util.Iterator;


@Author("Julian Fleischer")
@Version("2011-06-20")
class Array<T> implements Iterable<T> {

	final T[] $array;
	
	@SuppressWarnings("unchecked")
	public Array(final Class<T> $class, final int $length) {
		this.$array = (T[]) java.lang.reflect.Array.newInstance($class, $length);
	}
	
	public Array(final T[] $array) {
		this.$array = $array;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new ArrayIterator();
	}

	class ArrayIterator implements Iterator<T> {
		int $pos = 0;
		
		@Override
		public boolean hasNext() {
			return $pos < $array.length;
		}

		@Override
		public T next() {
			return $array[$pos++];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		
	}
}
