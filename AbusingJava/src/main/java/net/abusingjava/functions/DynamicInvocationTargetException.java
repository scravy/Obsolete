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

@Author("Julian Fleischer")
public class DynamicInvocationTargetException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DynamicInvocationTargetException() {
		super();
	}

	public DynamicInvocationTargetException(final String arg0) {
		super(arg0);
	}

	public DynamicInvocationTargetException(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

	public DynamicInvocationTargetException(final Throwable arg0) {
		super(arg0);
	}

}
