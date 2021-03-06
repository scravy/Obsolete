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
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Used to annotate a getter or setter to make a property the key used for comparing objects of the class to other objects of the class.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Author("Julian Fleischer")
@Version("2011-07-24")
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
public @interface CompareKey {
	
	/**
	 * The sort order (Order.ASCENDING or Order.DESCENDING, defaulting to ASCENDING).
	 */
	Order sort() default Order.ASCENDING;
}
