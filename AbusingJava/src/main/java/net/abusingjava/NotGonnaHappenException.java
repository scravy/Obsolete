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


/**
 * Used in places, where a checked java exception is reported by the compiler but the programmer knows,
 * that such an Exception will never be thrown there.
 * <p>
 * Since it is assumed that this Exception is never going to be thrown,
 * each constructor does <code>assert false</code> (such that an AssertionFailure
 * will be thrown if assertions are enabled).
 * <p>
 * This exception is only used to re-throw an exception and is an unchecked exception. 
 */
@Author("Julian Fleischer")
@Version("2011-07-17")
@Stable
public class NotGonnaHappenException extends RuntimeException {

	private static final long serialVersionUID = 7844736959889455971L;

	/**
	 * Standard-constructor to re-throw an Exception.
	 * 
	 * @param $exc The Exception to re-throw.
	 */
	public NotGonnaHappenException(final Throwable $exc) {
		super($exc);
		assert false;
	}

	/**
	 * Constructor to re-throw an Exception with a more detailed message (e.g. a hint what could have happened here).
	 * 
	 * @param $msg The message / hint.
	 * @param $exc The exception.
	 */
	public NotGonnaHappenException(final String $msg, final Throwable $exc) {
		super($msg, $exc);
		assert false;
	}

}
