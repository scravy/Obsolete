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

import java.io.Serializable;


/**
 * A Tuple which holds two values of type A and B. It is immutable.
 */
@Author("Julian Fleischer")
@Version("2011-07-17")
@Stable
public class Tuple<A, B> implements Serializable {

	private static final long serialVersionUID = 5667778393069638466L;

	final A $fst;

	final B $snd;

	/**
	 * The default constructor which takes all two values $a and $b.
	 * 
	 * @param $a The first component of this tuple.
	 * @param $b The second component of this tuple.
	 */
	public Tuple(final A $a, final B $b) {
		this.$fst = $a;
		this.$snd = $b;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + (($fst == null) ? 0 : $fst.hashCode());
		result = (prime * result) + (($snd == null) ? 0 : $snd.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object $tup) {
		try {
			if ($tup instanceof Tuple) {
				@SuppressWarnings("unchecked")
				Tuple<A,B> $triple = (Tuple<A,B>) $tup;
				
				return (($fst == null) && ($triple.$fst == null)) || ((($fst != null) && $fst.equals($triple.$fst))
						&& (($snd == null) && ($triple.$snd == null))) || ((($snd != null) && $snd.equals($triple.$snd)));
			}
		} catch (ClassCastException $exc) {}
		return false;
	}
	
	/**
	 * Returns the first component of this tuple.
	 * 
	 * @return The first component of this tuple.
	 */
	public A fst() {
		return $fst;
	}
	
	/**
	 * Returns the second component of this tuple.
	 * 
	 * @return The second component of this tuple.
	 */
	public B snd() {
		return $snd;
	}
}
