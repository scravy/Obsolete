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

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used to annotate a default value on a function parameter or a property.
 * <p>
 * This is particularly useful for creating mockup objects.
 * <p>
 * Another possible applications for this one would be, to annotate parameters of a
 * method such that multiple method are being created, e.g.<br>
 * <code>public void method(int $arg1, int @Default(intValue = 73) $arg2) { ... }</code>
 * would result in two methods:
 * <code>public void method(int $arg1) {<br>
 *   method($arg1, 73);
 * }</code><br>
 * and<br>
 * <code>public void method(int $arg1, int $arg2) { ... }</code>
 * <p>
 * This is yet only an idea. For a project similar in spirit see <i>Project Lombok</i>.
 */
@Author("Julian Fleischer")
@Version("2011-08-13")
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Default {
	
	/**
	 * The string default value of this param or property. Only applicable if <i>this</i>
	 * is of type String or is a complex type which can be represented as a String (such
	 * as a {@link java.util.Date Date}.)
	 */
	String value() default "";
	
	/**
	 * The int default value of this param or property. Only applicable if <i>this</i>
	 * is of type int.
	 */
	int intValue() default 0;

	/**
	 * The long default value of this param or property. Only applicable if <i>this</i>
	 * is of type long.
	 */
	long longValue() default 0L;

	/**
	 * The float default value of this param or property. Only applicable if <i>this</i>
	 * is of type float.
	 */
	float floatValue() default 0F;

	/**
	 * The double default value of this param or property. Only applicable if <i>this</i>
	 * is of type double.
	 */
	double doubleValue() default 0;

	/**
	 * The short default value of this param or property. Only applicable if <i>this</i>
	 * is of type short.
	 */
	short shortValue() default 0;

	/**
	 * The byte default value of this param or property. Only applicable if <i>this</i>
	 * is of type byte.
	 */
	byte byteValue() default 0;

	/**
	 * The char default value of this param or property. Only applicable if <i>this</i>
	 * is of type char.
	 */
	char charValue() default 0;
	
	/**
	 * The boolean default value of this param or property. Only applicable if <i>this</i>
	 * is of type boolean.
	 */
	boolean booleanValue() default false;

}
