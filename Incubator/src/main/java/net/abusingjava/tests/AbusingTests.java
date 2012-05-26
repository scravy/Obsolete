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
package net.abusingjava.tests;

import net.abusingjava.Author;
import net.abusingjava.Version;

@Author("Julian Fleischer")
@Version("2011-07-26")
public class AbusingTests {

	public final static class None extends Throwable {
		private static final long serialVersionUID = -7733727164902565826L;
		
		None() {}
	}
	
	public static void assertTrue(boolean $test, String $message) {
		if (!$test) {
			throw new AssertionFailedException($message);
		}
	}
	
	public static void assertTrue(boolean $test) {
		if (!$test) {
			throw new AssertionFailedException();
		}
	}
	
	public static void assertFalse(boolean $test) {
		assertTrue(!$test);
	}
	
	public static void assertFalse(boolean $test, String $message) {
		assertTrue(!$test, $message);
	}
	
	public static void assertNull(Object $obj) {
		assertTrue($obj == null);
	}
	
	public static void assertEquals(Object $obj1, Object $obj2) {
		if ($obj1 == null) {
			assertNull($obj2);
		} else {
			assertTrue($obj1.equals($obj2));
		}
	}
	
	public static void run(Class<?> $class) {
		TestRunner $runner = new TestRunner($class);
		$runner.run();
	}
}
