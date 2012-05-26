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
package net.abusingjava.mockups;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.HashSet;

import net.abusingjava.*;
import net.abusingjava.Cloneable;
import net.abusingjava.event.OffersPropertyChangeEvents;


@Author("Julian Fleischer")
@Version("2011-07-09")
public class AbusingMockups {

	static Method $add;
	static Method $remove;
	static Method $get;

	static {
		try {
			$add = OffersPropertyChangeEvents.class.getDeclaredMethod("addPropertyChangeListener", PropertyChangeListener.class);
			$remove = OffersPropertyChangeEvents.class.getDeclaredMethod("removePropertyChangeListener", PropertyChangeListener.class);
			$get = OffersPropertyChangeEvents.class.getDeclaredMethod("getPropertyChangeListeners");
		} catch (NoSuchMethodException $exc) {
			throw new NotGonnaHappenException($exc);
		}
	}
	
	/**
	 * Mocks up as many objects of type $interface, as specified by $size, and puts them into the given $collection.
	 */
	public static <T, C extends Collection<T>> C mockupCollection(Class<T> $interface, C $collection, int $size, final ValueProvider $v) {
		if (!$interface.isInterface())
			throw new IllegalArgumentException("Argument “Class<T> $interface” must be an interface");
		
		for (int $i = 0; $i < $size; $i++) {
			$collection.add(mockupObject($interface, $v));
		}
		return $collection;
	}
	
	/**
	 * Mocks up an array of $length objects of type $interface and fills bean properties with the given ValueProvider $v. 
	 */
	public static <T> T[] mockupArray(Class<T> $interface, int $length, final ValueProvider $v) {
		if (!$interface.isInterface())
			throw new IllegalArgumentException("Argument “Class<T> $interface” must be an interface");
		
		T[] $array = AbusingArrays.createArray($interface, $length);
		
		for (int $i = 0; $i < $length; $i++) {
			$array[$i] = mockupObject($interface, $v);
		}
		return $array;
	}
	
	public static <T> T mockupObject(final Class<T> $interface, final ValueProvider $v) {
		if (!$interface.isInterface())
			throw new IllegalArgumentException("Argument “Class<T> $interface” must be an interface");

		final InvocationHandlerWithPropertyChangeSupport $h = new MockupInvocationHandler<T>($interface, $v);

		@SuppressWarnings("unchecked")
		T $mockup = (T) Proxy.newProxyInstance(
				$interface.getClassLoader(),
				new Class<?>[] {
					$interface, OffersPropertyChangeEvents.class, Cloneable.class, UniqueID.class },
				$h);
		$h.setPropertyChangeSupport(new PropertyChangeSupport($mockup));
		
		return $mockup;
	}

	public static interface X {
		int getInt();
		X setInt(int $);
		
		boolean getBool();
		X setBool(boolean $);
	}
	
	public static interface XX {
		int getVal();
		void setVal(int $);
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String... $$) {
		ValueProvider $v = new DefaultValueProvider();
		XX $x1 = mockupObject(XX.class, $v);
		XX $x2 = mockupObject(XX.class, $v);
		XX $x3 = ((Cloneable<XX>)$x2).clone();
		System.out.println(((UniqueID<Long>) $x1).uniqueID());
		System.out.println(((UniqueID<Long>) $x2).uniqueID());
		System.out.println(((UniqueID<Long>) $x3).uniqueID());
		$x2.setVal(2);
		$x3.setVal(3);
		System.out.println($x2.getVal());
		System.out.println($x3.getVal());
	}
	
	public static void main2(@SuppressWarnings("unused") String... $args) {
		Collection<X> $set = mockupCollection(X.class, new HashSet<X>(), 7, new DefaultValueProvider());
		X $x = $set.iterator().next();
		((OffersPropertyChangeEvents) $x).addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent $ev) {
				System.out.println($ev.getOldValue());
				System.out.println($ev.getNewValue());
				System.out.println($ev.getPropertyName());
			}
		});
		$x.setInt(4711337);
	}
}
