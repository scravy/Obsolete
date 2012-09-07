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
 * A Triple which holds three values of type A, B, and C. It is immutable.
 */
@Author("Julian Fleischer")
@Version("2011-07-17")
@Stable
public class Quadruple<A, B, C, D> implements Serializable {

	private static final long serialVersionUID = -4007173696628778713L;

	final A $fst;

	final B $snd;

	final C $trd;
	
	final D $fth;

	/**
	 * The default constructor which takes all three values $a, $b, and $c.
	 * 
	 * @param $a The first component of this triple.
	 * @param $b The second comonent of this triple.
	 * @param $c The third component of this triple.
	 */
	public Quadruple(final A $a, final B $b, final C $c, final D $d) {
		this.$fst = $a;
		this.$snd = $b;
		this.$trd = $c;
		this.$fth = $d;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + (($fst == null) ? 0 : $fst.hashCode());
		result = (prime * result) + (($snd == null) ? 0 : $snd.hashCode());
		result = (prime * result) + (($trd == null) ? 0 : $trd.hashCode());
		result = (prime * result) + (($fth == null) ? 0 : $fth.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object $trip) {
		try {
			if ($trip instanceof Quadruple) {
				@SuppressWarnings("unchecked")
				Quadruple<A,B,C,D> $quad = (Quadruple<A,B,C,D>) $trip;
				
				return (($fst == null) && ($quad.$fst == null)) || ((($fst != null) && $fst.equals($quad.$fst))
						&& (($snd == null) && ($quad.$snd == null))) || ((($snd != null) && $snd.equals($quad.$snd))
						&& (($trd == null) && ($quad.$trd == null))) || ((($trd != null) && $trd.equals($quad.$trd))
						&& (($fth == null) && ($quad.$fth == null))) || (($fth != null) && $fth.equals($quad.$fth));
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
	
	/**
	 * Returns the third component of this tuple.
	 * 
	 * @return The third component of this tuple.
	 */
	public C trd() {
		return $trd;
	}
	
	public D fth() {
		return $fth;
	}
}
