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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.abusingjava.AbusingArrays;
import net.abusingjava.AbusingReflection;
import net.abusingjava.AbusingStrings;
import net.abusingjava.NotGonnaHappenException;

/**
 * Internal: 
 */
class MockupInvocationHandler<T> implements InvocationHandlerWithPropertyChangeSupport, java.lang.Cloneable {

	final Class<T> $interface;
	final Map<String,Class<?>> $properties;
	Map<String,Object> $propertyValues = new HashMap<String,Object>();
	PropertyChangeSupport $propertyChangeSupport;
	
	final long $uniqueID = System.nanoTime();
	
	MockupInvocationHandler(final Class<T> $interface, final ValueProvider $v) {
		this.$interface = $interface;
		$properties = AbusingReflection.properties($interface);
		
		if ($v != null) {
			for (String $p : $properties.keySet()) {
				Annotation[] $annotations = new Annotation[0];
				try {
					Method $getter = $interface.getMethod("get" + $p);
					Method $setter = $interface.getMethod("set" + $p, $properties.get($p));
					$annotations = AbusingArrays.concat($getter.getAnnotations(), $setter.getAnnotations());
				} catch (Exception $exc) {
				}
				$propertyValues.put($p, $v.provide($properties.get($p), $p, $annotations));
			}
		}
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public MockupInvocationHandler<T> clone() {
		try {
			MockupInvocationHandler<T> $h = (MockupInvocationHandler<T>) super.clone();
			$h.$propertyValues = new HashMap<String,Object>();
			$h.$propertyValues.putAll($propertyValues);
			$h.$propertyChangeSupport = new PropertyChangeSupport($h);
			return $h;
		} catch (CloneNotSupportedException $exc) {
			throw new NotGonnaHappenException($exc);
		}
	}
	
	@Override
	public Object invoke(final Object $proxy, final Method $method, final Object[] $args) throws Throwable {
		String $n = $method.getName();
		if ($n.equals("uniqueID")) {
			return $uniqueID;
		} else if ($n.startsWith("set") && ($args != null) && ($args.length == 1)) {
			$n = $n.substring(3);
			Class<?> $type = $properties.get($n);
			if ($type == $method.getParameterTypes()[0]) {
				Object $oldValue = $propertyValues.put($n, $args[0]);
				$propertyChangeSupport.firePropertyChange($n, $oldValue, $args[0]);
				return $proxy;
			}
		} else if ($n.startsWith("get") && (($args == null) || ($args.length == 0))) {
			$n = $n.substring(3);
			Class<?> $type = $properties.get($n);
			if ($type == $method.getReturnType()) {
				return $propertyValues.get($n);
			}
		} else if ($n.equals("hashCode") && (($args == null) || ($args.length == 0))) {
			return $propertyValues.hashCode();
		} else if (AbusingMockups.$add.equals($method)) {
			$propertyChangeSupport.addPropertyChangeListener((PropertyChangeListener) $args[0]);
		} else if (AbusingMockups.$remove.equals($method)) {
			$propertyChangeSupport.removePropertyChangeListener((PropertyChangeListener) $args[0]);
		} else if (AbusingMockups.$get.equals($method)) {
			return $propertyChangeSupport.getPropertyChangeListeners();
		} else if ($n.equals("toString")) {
			return String.format("Mockup for %s (%s)",
					$interface.getCanonicalName(),
					AbusingStrings.implode(", ", "=", $propertyValues));
		} else if ($n.equals("clone")) {
			Object $o = $proxy.getClass().getConstructor(InvocationHandler.class).newInstance(this.clone());
			return $o;
		}
		return null;
	}
	
	@Override
	public void setPropertyChangeSupport(final PropertyChangeSupport $p) {
		$propertyChangeSupport = $p;	
	}
	
	
}