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


import java.beans.PropertyChangeListener;
import java.lang.reflect.*;
import java.math.BigInteger;
import java.util.*;
import java.util.Map.Entry;

import net.abusingjava.event.OffersPropertyChangeEvents;



/**
 * For the easy use of reflection. Most of these methods are short hand methods for commonly used reflection patterns.
 */
@Author("Julian Fleischer")
@Version("2011-07-26")
final public class AbusingReflection {

	private AbusingReflection() {}

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
	 * Adds PropertyChangeSupport to an object for which there is a known interface.
	 * <p>
	 * This function will take an object, examine which interfaces it implements, and
	 * create a new object which will have the same methods as specified in these
	 * interfaces. The new object will delegate all requests to any methods to the
	 * original method, except for the methods as defined in {@link net.abusingjava.event.OffersPropertyChangeEvents}.
	 * These will be handled by a newly created {@link java.beans.PropertyChangeSupport PropertyChangeSupport-Object}.
	 * <p>
	 * It is worth noting, that methods which are not exposed in an interface are
	 * not reproduced and are therefore not accessible in the new object,
	 * <b>although the return type promises it</b>. The behavior of the newly created
	 * object is undefined for methods which are not exposed in one of it’s interface;
	 * most likely a ClassCastException will come up.
	 * <p>
	 * This method is to be used for prototyping only or when it is absolutely impossible
	 * to modify existing code, but also necessary to have PropertyChangeSupport on a given
	 * object.
	 */
	@SuppressWarnings("unchecked")
	@Experimental
	public static <T> T addPropertyChangeSupport(final T $obj) {
		final Map<String,Class<?>> $properties = properties($obj.getClass());
		
		return (T) Proxy.newProxyInstance(
		    $obj.getClass().getClassLoader(),
		    AbusingArrays.add($obj.getClass().getInterfaces(), OffersPropertyChangeEvents.class),
		    new InvocationHandler() {
		    	java.beans.PropertyChangeSupport $propertyChangeSupport = new java.beans.PropertyChangeSupport($obj);
		    	
				@Override
				public Object invoke(final Object $proxy, final Method $method, final Object[] $args) throws Throwable {
					String $n = $method.getName();
					if ($add.equals($method)) {
						$propertyChangeSupport.addPropertyChangeListener((PropertyChangeListener) $args[0]);
					} else if ($remove.equals($method)) {
						$propertyChangeSupport.removePropertyChangeListener((PropertyChangeListener) $args[0]);
					} else if ($get.equals($method)) {
						return $propertyChangeSupport.getPropertyChangeListeners();
					} else if ($n.equals("hashCode") && (($args == null) || ($args.length == 0))) {
						return $obj.hashCode();
					} else if ($n.equals("equals") && ($args != null) && ($args.length == 1)) {
						return $obj.equals($args[1]);
					} else {
						if ($n.startsWith("set") && ($args != null) && ($args.length == 1)) {
							$n = $n.substring(3);
							if ($properties.containsKey($n)) {
								Object $oldValue = $obj.getClass().getMethod("get" + $n).invoke($obj);
								Object $newValue = $args[0];
								$method.invoke($obj, $args);
								$propertyChangeSupport.firePropertyChange($n, $oldValue, $newValue);
							}
						} else {
							return $method.invoke($obj, $args);
						}
					}
					return null;
				}
		    });
	}
	
	/**
	 * Retrieves a Map of all properties of a given class, based on it’s API.
	 * <p>
	 * A property is considered a property, if there exists a getter and a
	 * setter for it, where the setter takes exactly one argument which is of the
	 * same type as the return type of the getter. The setters return type does
	 * not matter, but the getter may not have any parameters to be considered as such.
	 * <p>
	 * Please note that this definition especially does not allow for the return
	 * value of the getter to be a subclass of the setters argument and vice versa.
	 * <p>
	 * The name of a property is believed to start with an upper case character.
	 * This is due to the fact, that relying on Javas CamelCase, one can not tell
	 * whether
	 */
	public static Map<String,Class<?>> properties(final Class<?> $class) {

		Map<String,Class<?>> $properties = new HashMap<String,Class<?>>();
		
		Map<String,Class<?>> $getters = new HashMap<String,Class<?>>();
		Map<String,Class<?>> $setters = new HashMap<String,Class<?>>();
		
		for (Method $m : $class.getMethods()) {
			String $n = $m.getName();
			if ((($m.getModifiers() & Modifier.PUBLIC) > 0) && ($n.length() > 3)) {
				if ($n.startsWith("get")
						&& Character.isUpperCase($n.charAt(3))
						&& ($m.getParameterTypes().length == 0)
						&& ($m.getReturnType() != void.class)) {
					$getters.put($n.substring(3), $m.getReturnType());
				} else if ($n.startsWith("set")
						&& Character.isUpperCase($n.charAt(3))
						&& ($m.getParameterTypes().length == 1)) {
					$setters.put($n.substring(3), $m.getParameterTypes()[0]);
				}
			}
		}
		
		for (Entry<String,Class<?>> $getter : $getters.entrySet()) {
			if ($setters.containsKey($getter.getKey())
					&& ($getter.getValue() == $setters.get($getter.getKey()))) {
				$properties.put($getter.getKey(), $setters.get($getter.getKey()));
			}
		}
		
		return $properties;
	}
	
	/**
	 * <code>properties($object.getClass());</code>
	 */
	public static Map<String,Class<?>> properties(final Object $object) {
		return properties($object.getClass());
	}
	
	/**
	 * Get all superclasses of a given class. The last element in the resulting array is the top-most superclass (i.e. <code>Object.class</code>).
	 * <p>
	 * @return An array containing all superclasses. When invoked with Object.class, the result will be an empty array.
	 */
	public static Class<?>[] parents(Class<?> $class) {
		List<Class<?>> $parents = new LinkedList<Class<?>>();
		if ($class.equals(Object.class)) {
			return new Class<?>[0];
		}
		while (($class = $class.getSuperclass()) != null) {
			$parents.add($class);
		}
		return $parents.toArray(new Class<?>[$parents.size()]);
	}
	/**
	 * Returns an array of all declared fields in the given $class.
	 */
	public static Field[] fields(final Class<?> $class) {
		Class<?>[] $parents = parents($class);
		Map<String,Field> $fields = new HashMap<String,Field>();
		for (int $i = $parents.length - 1; $i >= 0; $i--) {
			Field[] $f = $parents[$i].getDeclaredFields();
			for (int $j = 0; $j < $f.length; $j++) {
				$fields.put($f[$j].getName(), $f[$j]);
			}
		}
		Field[] $f = $class.getDeclaredFields();
		for (int $j = 0; $j < $f.length; $j++) {
			$fields.put($f[$j].getName(), $f[$j]);
		}
		return $fields.values().toArray(new Field[$fields.size()]);
	}
	
	
	/**
	 * Determines whether a given $object as a method named $method.
	 * 
	 * @param $object The object to examine. If null is given, the result will be false.
	 */
	public static boolean hasMethod(final Object $object, final String $method) {
		if ($object == null) {
			return false;
		}
		for (Method $m : $object.getClass().getMethods()) {
			if ($m.getName().equals($method)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines whether a given $object has a method named $method and return the given $returnType.
	 * 
	 * @param $object The object to examine. If null is given, the result will be false.
	 */
	public static boolean hasMethodOfType(final Object $object, final String $method, final Class<?> $returnType) {
		if ($object == null) {
			return false;
		}
		for (Method $m : $object.getClass().getMethods()) {
			if ($m.getName().equals($method) && $m.getReturnType().equals($returnType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines whether a given $object has a specific $method with the given $paremeterTypes.
	 * 
	 * @param $object The object to examine. If null is given, the result will be false.
	 */
	public static boolean hasMethodWithArguments(final Object $object, final String $method, final Class<?>... $parameterTypes) {
		if ($object == null) {
			return false;
		}
		try {
			return $object.getClass().getMethod($method, $parameterTypes) != null;
		} catch (NoSuchMethodException $exc) {
			return false;
		}
	}
	
	/**
	 * Determines a generic base type, e.g. the Integer in <code>Set&lt;Integer></code>, or null if no such type can be determined.
	 */
	public static Class<?> genericBaseType(final Type $type) {
		if ($type instanceof ParameterizedType) {
			ParameterizedType $parameterizedType = (ParameterizedType) $type;
			if ($parameterizedType.getActualTypeArguments().length == 1) {
				Type $actualType = $parameterizedType.getActualTypeArguments()[0];
				if ($actualType instanceof Class<?>) {
					return (Class<?>) $actualType;
				}
			}
		}
		return null;
	}
	
	/**
	 * Returns the calling {@link Class}-Object (this is not the calling object, but the object representing the calling class).
	 */
	public static Class<?> callingClass() {
		StackTraceElement[] $trace = new Exception().getStackTrace();
		if ($trace.length >= 3) {
			try {
				return Class.forName($trace[2].getClassName());
			} catch (ClassNotFoundException $exc) {
				throw new NotGonnaHappenException($exc);
			}
		}
		return null;
	}

	/**
	 * Returns the name of the calling class.
	 */
	public static String callingClassName() {
		StackTraceElement[] $trace = new Exception().getStackTrace();
		if ($trace.length >= 3) {
			return $trace[2].getClassName();
		}
		return null;
	}
	
	/**
	 * Returns the name of the calling method.
	 */
	public static String callingMethodName() {
		StackTraceElement[] $trace = new Exception().getStackTrace();
		if ($trace.length >= 3) {
			return $trace[2].getMethodName();
		}
		return null;
	}
	
	/**
	 * Returns the name of the calling method as “ClassName#MethodName”.
	 */
	public static String callingMethodFullName() {
		StackTraceElement[] $trace = new Exception().getStackTrace();
		if ($trace.length >= 3) {
			return $trace[2].getClassName() + '#' + $trace[2].getMethodName();
		}
		return null;
	}
	
	/**
	 * Compares the canonical name of the given $object and the given $type (as a string).
	 */
	public static boolean instanceOf(final Object $object, final String $type) {
		if ($type == null) {
			throw new IllegalArgumentException("$type must not be null.");
		}
		return $type.equals($object.getClass().getCanonicalName());
	}

	public static Object create(final String $className, final Object... $args) {
		try {
			if (($args == null) || ($args.length == 0)) {
				return Class.forName($className).newInstance();
			}
			Class<?>[] $types = new Class<?>[$args.length];
			for (int $i = 0; $i < $args.length; $i++) {
				$types[$i] = $args[$i].getClass();
			}
			return Class.forName($className).getConstructor($types).newInstance($args);
		} catch (Exception $exc) {
			return Null.OBJECT;
		}
	}

	public static Object call(final Object $object, final String $methodName, final Object... $args) {
		Class<?>[] $types = new Class<?>[$args.length];
		for (int $i = 0; $i < $args.length; $i++) {
			$types[$i] = $args[$i].getClass();
		}
		try {
			return $object.getClass().getMethod($methodName, $types).invoke($object, $args);
		} catch (Exception $exc) {
			$exc.printStackTrace(System.err);
			return Null.OBJECT;
		}
	}
	
	public static Object get(final Object $object, final String $fieldName) {
		try {
			return $object.getClass().getField($fieldName).get($object);
		} catch (Exception $exc) {
			return Null.OBJECT;
		}
	}
	
	public static void set(final Object $object, final String $fieldName, final Object $value) {
		try {
			$object.getClass().getField($fieldName).set($object, $value);
		} catch (Exception $exc) {
		}
	}
	

	public static boolean isArray(final Class<?> $class) {
		return $class.isArray();
	}

	public static boolean isArray(final Object $obj) {
		return $obj.getClass().isArray();
	}

	public static boolean isBoolean(final Class<?> $class) {
		return Boolean.class.isAssignableFrom($class) || boolean.class.isAssignableFrom($class);
	}

	public static boolean isBoolean(final Object $b) {
		if ($b == null) {
			return false;
		}
		return $b instanceof Boolean;
	}

	public static boolean isFloating(final Class<?> $class) {
		return Double.class.isAssignableFrom($class)
				|| Float.class.isAssignableFrom($class)
				|| double.class.isAssignableFrom($class)
				|| float.class.isAssignableFrom($class);
	}

	public static boolean isFloating(final Object $obj) {
		return ($obj instanceof Double)
				|| ($obj instanceof Float);
	}

	public static boolean isIntegral(final Class<?> $class) {
		return Integer.class.isAssignableFrom($class)
				|| Long.class.isAssignableFrom($class)
				|| BigInteger.class.isAssignableFrom($class)
				|| Short.class.isAssignableFrom($class)
				|| Byte.class.isAssignableFrom($class)
				|| int.class.isAssignableFrom($class)
				|| long.class.isAssignableFrom($class)
				|| short.class.isAssignableFrom($class)
				|| byte.class.isAssignableFrom($class);
	}

	public static boolean isIntegral(final Object $num) {
		return ($num instanceof Long)
				|| ($num instanceof Integer)
				|| ($num instanceof BigInteger)
				|| ($num instanceof Short)
				|| ($num instanceof Byte);
	}

	public static boolean isNatural(final double $n) {
		return Math.floor($n) == $n;
	}

	public static boolean isNatural(final Number $b) {
		return $b.doubleValue() == $b.longValue();
	}

	public static boolean isNull(final Object $obj) {
		return $obj == null;
	}

	public static boolean isNumber(final Class<?> $class) {
		if (isPrimitive($class)) {
			return !$class.equals(char.class) && !$class.equals(boolean.class);
		}
		return Number.class.isAssignableFrom($class);
	}

	public static boolean isNumber(final Object $obj) {
		if ($obj == null) {
			return false;
		}
		return $obj instanceof Number;
	}

	/**
	 * Checks whether the given $class represents a primitive type.
	 */
	public static boolean isPrimitive(final Class<?> $class) {
		return !(Object.class.isAssignableFrom($class));
	}

	@Experimental
	public static boolean isString(final Class<?> $class) {
		return $class.equals(String.class);
	}

	public static boolean isString(final Object $obj) {
		return String.class.isAssignableFrom($obj.getClass());
	}
}
