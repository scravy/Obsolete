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

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Author("Julian Fleischer")
@Version("2012-01-04")
final public class AbusingJava {

	private static final Logger $logger = LoggerFactory.getLogger(AbusingJava.class);

	private AbusingJava() {
	}

	/**
	 * Creates a Tuple.
	 * <p>
	 * <code>import static net.abusingjava.AbusingJava.t;<br>
	 * ...<br>
	 * t(7, 8)
	 * t("hello", "world");
	 * t(9.4, new Date());</code>
	 * 
	 * @param $a
	 *            The first component of the Tuple to be created.
	 * @param $b
	 *            The second component of the Tuple to be created.
	 * 
	 * @return The newly created Tuple.
	 */
	@Stable
	public static <A, B> Tuple<A, B> t(final A $a, final B $b) {
		return new Tuple<A, B>($a, $b);
	}

	/**
	 * Creates a Triple.
	 * <p>
	 * <code>import static net.abusingjava.AbusingJava.t;<br>
	 * ...<br>
	 * t(7, 8, 9)
	 * t("hello", "world", "out there");
	 * t(9.4, new Date(), "yea, different types ARE possible");</code>
	 * 
	 * @param $a
	 *            The first component of the Tuple to be created.
	 * @param $b
	 *            The second component of the Tuple to be created.
	 * @param $c
	 *            The third component of the Tuple to be created.
	 * 
	 * @return The newly created Triple.
	 */
	@Stable
	public static <A, B, C> Triple<A, B, C> t(final A $a, final B $b, final C $c) {
		return new Triple<A, B, C>($a, $b, $c);
	}

	public static <A, B, C, D> Quadruple<A, B, C, D> t(final A $a, final B $b, final C $c, final D $d) {
		return new Quadruple<A, B, C, D>($a, $b, $c, $d);
	}

	/**
	 * Like {@link Float#parseFloat(String)}, but instead of throwing a
	 * {@link NumberFormatException} returns a $default value.
	 */
	@Stable
	public static float parseFloat(final String $value, final float $default) {
		try {
			return Float.parseFloat($value);
		} catch (NumberFormatException $exc) {
			return $default;
		}
	}

	/**
	 * Like {@link Double#parseDouble(String)}, but instead of throwing a
	 * {@link NumberFormatException} returns a $default value.
	 */
	@Stable
	public static double parseDouble(final String $value, final double $default) {
		try {
			return Double.parseDouble($value);
		} catch (NumberFormatException $exc) {
			return $default;
		}
	}

	/**
	 * Like {@link Byte#parseByte(String)}, but instead of throwing a
	 * {@link NumberFormatException} returns a $default value.
	 */
	@Stable
	public static short parseByte(final String $value, final byte $default) {
		try {
			return Byte.parseByte($value);
		} catch (NumberFormatException $exc) {
			return $default;
		}
	}

	/**
	 * Like {@link Short#parseShort(String)}, but instead of throwing a
	 * {@link NumberFormatException} returns a $default value.
	 */
	@Stable
	public static short parseShort(final String $value, final short $default) {
		try {
			return Short.parseShort($value);
		} catch (NumberFormatException $exc) {
			return $default;
		}
	}

	/**
	 * Like {@link Integer#parseInt(String)}, but instead of throwing a
	 * {@link NumberFormatException} returns a $default value.
	 */
	@Stable
	public static int parseInt(final String $value, final int $default) {
		try {
			return Integer.parseInt($value);
		} catch (NumberFormatException $exc) {
			return $default;
		}
	}

	/**
	 * Like {@link Long#parseLong(String)}, but instead of throwing a
	 * {@link NumberFormatException} returns a $default value.
	 */
	@Stable
	public static long parseLong(final String $value, final long $default) {
		try {
			return Long.parseLong($value);
		} catch (NumberFormatException $exc) {
			return $default;
		}
	}

	/**
	 * Like {@link Boolean#parseBoolean(String)}, returning a default value if
	 * neither “true” nor “false” have been recognized.
	 */
	@Stable
	public static boolean parseBoolean(final String $value, final boolean $default) {
		if ("true".equalsIgnoreCase($value)) {
			return true;
		} else if ("false".equalsIgnoreCase($value)) {
			return false;
		}
		return $default;
	}

	public static boolean parseBoolean(final String $value) {
		return "true".equalsIgnoreCase($value);
	}

	/**
	 * Retrieves the via {@link Author @Author} annotated authors of a given
	 * $class.
	 * <p>
	 * If the class is a member class and does not have any @Author annotation,
	 * the @Author information from its enclosing class will be returned.
	 */
	public static String[] authorsOf(final Class<?> $class) {
		Author $annotation = $class.getAnnotation(Author.class);
		if ($annotation != null) {
			return $annotation.value();
		}
		if ($class.isMemberClass()) {
			return authorsOf($class.getEnclosingClass());
		}
		return new String[0];
	}

	/**
	 * Retrieves the via {@link Author @Author} annotated authors of a method
	 * with $name and parameter types $args.
	 * <p>
	 * If the method does not have a @Author annotation, the authors of the
	 * class that declares the method will be returned.
	 */
	public static String[] authorsOf(final Class<?> $class, final String $name, final Class<?>... $args) {
		try {
			Method $method = $class.getMethod($name, $args);
			Author $annotation = $method.getAnnotation(Author.class);
			if ($annotation != null) {
				return $annotation.value();
			}
			return authorsOf($method.getDeclaringClass());
		} catch (NoSuchMethodException $exc) {
			$logger.warn("There is no method “" + $name + "” to retrieve author-information for.", $exc);
		}
		return new String[0];
	}

	/**
	 * Retrieves the via {@link Version @Version} annotated version information
	 * of a given $class.
	 * <p>
	 * If the class is a member class and does not have any @Version annotation,
	 * the version information from its enclosing class will be returned.
	 */
	public static String versionOf(final Class<?> $class) {
		Version $annotation = $class.getAnnotation(Version.class);
		if ($annotation != null) {
			return $annotation.value();
		}
		if ($class.isMemberClass()) {
			return versionOf($class.getEnclosingClass());
		}
		return "";
	}

	/**
	 * Retrieves the via {@link Version @Version} annotated version information
	 * of a method with $name and parameter types $args.
	 * <p>
	 * If the method does not have a @Version annotation, the version
	 * information of the class that declares the method will be returned.
	 */
	public static String versionOf(final Class<?> $class, final String $name, final Class<?>... $args) {
		try {
			Method $method = $class.getMethod($name, $args);
			Version $annotation = $method.getAnnotation(Version.class);
			if ($annotation != null) {
				return $annotation.value();
			}
			return versionOf($class);
		} catch (NoSuchMethodException $exc) {
			$logger.warn("There is no method “" + $name + "” to retrieve version-information for.", $exc);
		}
		return "";
	}

	/**
	 * Returns $ifNull if $object is null, or $object otherwise.
	 * <p>
	 * Use like so: <code>import static net.abusingjava.AbusingJava.elvis</code>.
	 * 
	 * @param $object
	 *            Object to check for null.
	 * @param $ifNull
	 *            Return-Value if $object is null.
	 */
	public static <T> T elvis(final T $object, final T $ifNull) {
		if ($object == null) {
			return $ifNull;
		}
		return $object;
	}

	/**
	 * Returns the String representation of the boxed object.
	 */
	public static String toString(final Object $object, final Object $ifNull) {
		if ($object == null) {
			return $ifNull.toString();
		}
		return $object.toString();
	}

	/**
	 * Tries to sleep $millis milli seconds.
	 * 
	 * @return If we did sleep for as long as we wanted to.
	 */
	public static boolean trySleep(final long $millis) {
		try {
			Thread.sleep($millis);
			return true;
		} catch (InterruptedException $exc) {
			return false;
		}
	}

}
