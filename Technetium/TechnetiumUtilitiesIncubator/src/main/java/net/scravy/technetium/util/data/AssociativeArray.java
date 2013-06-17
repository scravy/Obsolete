package net.scravy.technetium.util.data;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import net.scravy.technetium.util.ArrayDequeList;
import net.scravy.technetium.util.Collection2;
import net.scravy.technetium.util.function.Function;
import net.scravy.technetium.util.function.Function2;
import net.scravy.technetium.util.function.FunctionUtil;
import net.scravy.technetium.util.iterator.IterableIteratorAdapter;
import net.scravy.technetium.util.value.ValueUtil;

/**
 * An associative array (much like the arrays known from PHP).
 * 
 * An associative array is backed by a {@link HashMap}, maintaining the
 * associations, and an {@link ArrayList}, maintaining the order of the
 * elements.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class AssociativeArray
		implements Collection2<Object>, Cloneable, Serializable {

	/**
	 * An entry with an Object as key and as value.
	 * 
	 * @author Julian Fleischer
	 * @since 1.0
	 */
	public class Entry extends AbstractMap.SimpleEntry<Object, Object> {

		/**
		 * Legacy serialVersionUID.
		 */
		private static final long serialVersionUID = -7569447761667995686L;

		/**
		 * Construct a new Entry.
		 * 
		 * @param key
		 *            The key of this entry.
		 * @param value
		 *            The value of this entry.
		 */
		public Entry(Object key, Object value) {
			super(key, value);
		}
	}

	/**
	 * Legacy serialVersionUID.
	 */
	private static final long serialVersionUID = -1331381997379542817L;

	/**
	 * This class features its own thread local random generators.
	 */
	protected static ThreadLocal<Random> random = new ThreadLocal<Random>() {
		@Override
		protected Random initialValue() {
			return new Random();
		}
	};

	/**
	 * The backing HashMap to store associations.
	 */
	private final Map<Object, Object> backingMap = new HashMap<Object, Object>();

	/**
	 * The list of keys to maintain order.
	 */
	private final List<Object> keys;

	private int nextIndex = 0;

	/**
	 * The default, no-arg constructor.
	 * 
	 * @since 1.0
	 */
	public AssociativeArray() {
		this.keys = new ArrayList<Object>();
	}

	/**
	 * A constructor which initializes this Array with values from the specified
	 * Map.
	 * 
	 * @param map
	 *            The Map.
	 * @since 1.0
	 */
	public AssociativeArray(Map<? extends Object, ? extends Object> map) {
		this.keys = new ArrayList<Object>(map.entrySet());
		backingMap.putAll(map);
	}

	AssociativeArray(List<Object> keys, boolean internal) {
		this.keys = keys;
	}

	/**
	 * A constructor which initialized the specified keys (which will be
	 * associated with null values).
	 * 
	 * @param keys
	 *            The list of keys.
	 * @since 1.0
	 */
	public AssociativeArray(List<? extends Object> keys) {
		if (keys != null) {
			for (Object key : keys) {
				if (key == null) {
					throw new IllegalArgumentException(
							"No key in `keys` may be null.");
				}
			}
			this.keys = new ArrayList<Object>(keys);
			for (Object ix : keys) {
				if (ix instanceof Integer) {
					nextIndex = Math.max(nextIndex, ((Integer) ix) + 1);
				}
			}
		} else {
			this.keys = new ArrayList<Object>();
		}
	}

	/**
	 * A constructor which initialized the specified keys (which will be
	 * associated with null values).
	 * 
	 * @param keys
	 *            The array of keys.
	 * @since 1.0
	 */
	public AssociativeArray(Object... keys) {
		this(Arrays.asList(keys));
	}

	@Override
	public boolean add(Object value) {
		put(Integer.valueOf(nextIndex++), value);
		return true;
	}

	/**
	 * Adds all specified objects to this Array.
	 * 
	 * @since 1.0
	 * @param objects
	 *            The objects.
	 * @return This method returns true always.
	 */
	@Override
	public boolean addAll(Object... objects) {
		for (Object obj : objects) {
			add(obj);
		}
		return true;
	}

	/**
	 * Adds all specified objects to this Array – this method exists for the
	 * easier use of legacy APIs which return an {@link Enumeration}.
	 * 
	 * @since 1.0
	 * @param objects
	 *            The objects.
	 * @return This method returns true always.
	 */
	public boolean addAll(Enumeration<?> objects) {
		while (objects.hasMoreElements()) {
			add(objects.nextElement());
		}
		return true;
	}

	/**
	 * Adds all specified objects to this Array.
	 * 
	 * @since 1.0
	 * @param objects
	 *            The objects.
	 * @return This method returns true always.
	 */
	@Override
	public boolean addAll(Iterable<? extends Object> objects) {
		for (Object obj : objects) {
			add(obj);
		}
		return true;
	}

	/**
	 * Adds all specified objects to this Array.
	 * 
	 * @since 1.0
	 * @param objects
	 *            The objects.
	 * @return This method returns true always.
	 */
	@Override
	public boolean addAll(Collection<? extends Object> objects) {
		for (Object obj : objects) {
			add(obj);
		}
		return true;
	}

	@Override
	public void clear() {
		backingMap.clear();
	}

	@Override
	public Object rand() {
		return get(keys.get(random.get().nextInt(keys.size())));
	}

	@Override
	public Object rand(Random random) {
		return get(keys.get(random.nextInt(keys.size())));
	}

	/**
	 * Returns an Iterable object over the Entries in this array.
	 * 
	 * @since 1.0
	 * @return The Iterable object.
	 */
	public Iterable<Entry> entries() {
		return new IterableIteratorAdapter<Entry>() {
			Iterator<Object> keys = AssociativeArray.this.keys.iterator();

			@Override
			public boolean hasNext() {
				return keys.hasNext();
			}

			@Override
			public Entry next() {
				Object key = keys.next();
				return new Entry(key, get(key));
			}
		};
	}

	/**
	 * Shuffles the contents of this record (the keys are mixed up).
	 * 
	 * @since 1.0
	 * @return This.
	 */
	public AssociativeArray shuffle() {
		Collections.shuffle(keys);
		return this;
	}

	/**
	 * Shuffles the contents of this record, using the specified random
	 * generator.
	 * 
	 * @param random
	 *            The random generator.
	 * 
	 * @since 1.0
	 * @return This.
	 */
	public AssociativeArray shuffle(Random random) {
		Collections.shuffle(keys, random);
		return this;
	}

	public AssociativeArray slice(int offset) {
		AssociativeArray record = new AssociativeArray();
		for (int i = 0; i < nextIndex; i++) {
			if (backingMap.containsKey(offset + i)) {
				record.add(backingMap.get(offset + i));
			}
		}
		return record;
	}

	/**
	 * Retrieve a copy of a slice from the array.
	 * 
	 * @param offset
	 *            The index at which to start slicing.
	 * @param preserveKeys
	 *            Whether or not to preserve numeric keys. If false, the slice
	 *            is renumbered starting at 0, if true, numeric indexes are
	 *            retained.
	 * @return A copy of this array from offset to length.
	 */
	public AssociativeArray slice(int offset, boolean preserveKeys) {
		if (preserveKeys) {
			AssociativeArray record = new AssociativeArray();
			for (int i = 0; i < nextIndex; i++) {
				if (backingMap.containsKey(offset + i)) {
					record.put(offset + i, backingMap.get(offset + i));
				}
			}
			return record;
		}
		return slice(offset);
	}

	public AssociativeArray slice(int offset, int count) {
		AssociativeArray record = new AssociativeArray();
		for (int i = 0; i < count; i++) {
			if (backingMap.containsKey(offset + i)) {
				record.add(backingMap.get(offset + i));
			}
		}
		return record;
	}

	/**
	 * @param offset
	 * @param count
	 * @param preserveKeys
	 * @return
	 */
	public AssociativeArray slice(int offset, int count, boolean preserveKeys) {
		if (preserveKeys) {
			AssociativeArray record = new AssociativeArray();
			for (int i = 0; i < count; i++) {
				if (backingMap.containsKey(offset + i)) {
					record.put(offset + i, backingMap.get(offset + i));
				}
			}
			return record;
		}
		return slice(offset, count);
	}

	/**
	 * Sort the contents of this record with respect to the values.
	 * 
	 * @since 1.0
	 * @return This.
	 */
	public AssociativeArray sort() {
		sort(new AnyComparator());
		return this;
	}

	/**
	 * Sort the contents of this record with respect to the values (ascending),
	 * using the given comparator for sorting.
	 * 
	 * @param comparator
	 *            The comparator used for sorting.
	 * 
	 * @since 1.0
	 * @return This.
	 */
	public AssociativeArray sort(final Comparator<Object> comparator) {
		ArrayList<Map.Entry<Object, Object>> entries =
				new ArrayList<Map.Entry<Object, Object>>(backingMap.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<Object, Object>>() {
			@Override
			public int compare(Map.Entry<Object, Object> left,
					Map.Entry<Object, Object> right) {
				return comparator.compare(left.getValue(), right.getValue());
			}
		});
		keys.clear();
		for (Map.Entry<Object, Object> entry : entries) {
			keys.add(entry.getKey());
		}
		return this;
	}

	/**
	 * Sort the contents of this array with respect to the values (reverse,
	 * descending), using the given comparator for sorting.
	 * 
	 * @param comparator
	 *            The comparator used for sorting.
	 * 
	 * @since 1.0
	 * @return This.
	 */
	public AssociativeArray rsort(final Comparator<Object> comparator) {
		ArrayList<Map.Entry<Object, Object>> entries =
				new ArrayList<Map.Entry<Object, Object>>(backingMap.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<Object, Object>>() {
			@Override
			public int compare(
					Map.Entry<Object, Object> left,
					Map.Entry<Object, Object> right) {
				return comparator.compare(right.getValue(), left.getValue());
			}
		});
		keys.clear();
		for (Map.Entry<Object, Object> entry : entries) {
			keys.add(entry.getKey());
		}
		return this;
	}

	/**
	 * Sort the contents of this array with respect to the values (reverse,
	 * descending), using the given function as comparator for sorting.
	 * 
	 * @param function
	 *            The function used as comparator for sorting.
	 * 
	 * @since 1.0
	 * @return This.
	 */
	public AssociativeArray sort(Function2<Object, Object, Integer> function) {
		sort(FunctionUtil.comparator(function));
		return this;
	}

	/**
	 * Sorts the contents of this Record with respect to the keys.
	 * <p>
	 * This method works in-place, but returns this Record. Use
	 * <code>record.clone().sort()</code> if you want your record not to be
	 * modified.
	 * </p>
	 * 
	 * @since 1.0
	 * @return This, sorted.
	 */
	public AssociativeArray ksort() {
		Collections.sort(keys, new AnyComparator());
		return this;
	}

	@Override
	public AssociativeArray clone() {
		AssociativeArray clone = new AssociativeArray(keys);
		clone.backingMap.putAll(backingMap);
		return clone;
	}

	/**
	 * @param startIndex
	 * @param count
	 * @param value
	 * @since 1.0
	 */
	public void fill(int startIndex, int count, Object value) {
		for (int i = 0; i < count; i++) {
			backingMap.put(startIndex + i, value);
		}
	}

	/**
	 * @return
	 * @since 1.0
	 */
	public AssociativeArray flipped() {
		AssociativeArray flipped = new AssociativeArray();
		for (Object key : keys) {
			flipped.backingMap.put(backingMap.get(key), key);
		}
		return flipped;
	}

	/**
	 * @param key
	 * @return
	 * @since 1.0
	 */
	public boolean containsKey(Object key) {
		return keys.contains(key);
	}

	/**
	 * @return
	 * @since 1.0
	 */
	public AssociativeArray keys() {
		AssociativeArray keys = new AssociativeArray();
		for (Object key : this.keys) {
			keys.add(key);
		}
		return keys;
	}

	/**
	 * @param value
	 * @return
	 * @since 1.0
	 */
	public AssociativeArray keys(Object value) {
		AssociativeArray keys = new AssociativeArray();
		for (Object key : keys) {
			if (ValueUtil.equals(value, backingMap.get(key))) {
				keys.add(key);
			}
		}
		return keys;
	}

	/**
	 * @return
	 * @since 1.0
	 */
	public List<Object> keysList() {
		List<Object> keys = new ArrayList<Object>(this.keys.size());
		for (Object key : this.keys) {
			keys.add(key);
		}
		return keys;
	}

	/**
	 * @param value
	 * @return
	 * @since 1.0
	 */
	public List<Object> keysList(Object value) {
		List<Object> keys = new ArrayList<Object>();
		for (Object key : this.keys) {
			if (ValueUtil.equals(value, backingMap.get(key))) {
				keys.add(key);
			}
		}
		return keys;
	}

	/**
	 * Retrieves only the values, not the keys, from this AssociativeArray.
	 * 
	 * @return The values.
	 * @since 1.0
	 */
	public AssociativeArray values() {
		AssociativeArray values = new AssociativeArray();
		for (Object key : keys) {
			values.add(backingMap.get(key));
		}
		return values;
	}

	/**
	 * Retrieves the values from this AssociativeArray as a List.
	 * 
	 * @return The values as a List.
	 * @since 1.0
	 */
	public ArrayDequeList<Object> valuesList() {
		ArrayDequeList<Object> values = new ArrayDequeList<Object>();
		for (Object key : keys) {
			values.add(backingMap.get(key));
		}
		return values;
	}

	/**
	 * Retrieves the values which are of the specified type or of a subtype of
	 * the specified type from this array.
	 * 
	 * @param clazz
	 *            The Type.
	 * @return The values.
	 * @since 1.0
	 */
	public <T> AssociativeArray values(Class<T> clazz) {
		AssociativeArray values = new AssociativeArray();
		for (Object key : keys) {
			Object value = backingMap.get(key);
			if (clazz.isAssignableFrom(value.getClass())) {
				values.add(value);
			}
		}
		return values;
	}

	/**
	 * Retrieves the values which are of the specified type or of a subtype of
	 * the specified type from this array as a List.
	 * 
	 * @param clazz
	 *            The Type.
	 * @return The values as a List.
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public <T> ArrayDequeList<T> valuesList(Class<T> clazz) {
		ArrayDequeList<T> values = new ArrayDequeList<T>();
		for (Object key : keys) {
			Object value = backingMap.get(key);
			if (clazz.isAssignableFrom(value.getClass())) {
				values.add((T) value);
			}
		}
		return values;
	}

	/**
	 * Retrieves the value associated with the given key.
	 * 
	 * @param key
	 *            The key.
	 * @return The value associated with the given key (which may be null) of
	 *         null, is no value is associated with the given key or the key
	 *         does not exist. Use {@link #containsKey(Object)} to check if the
	 *         key exist.
	 * @since 1.0
	 */
	public Object get(Object key) {
		return backingMap.get(key);
	}

	/**
	 * Retrieve the value associated with the given key as the given type (if it
	 * is compatible with this type).
	 * 
	 * @param key
	 *            The key.
	 * @param clazz
	 *            The type.
	 * @return The value associated with the given key, as an instance of type
	 *         `clazz`, or null if this type does not exist or is not an
	 *         instance of the specified `clazz`.
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(Object key, Class<T> clazz) {
		Object value = backingMap.get(key);
		if (value != null && clazz.isAssignableFrom(value.getClass())) {
			return (T) value;
		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		return backingMap.isEmpty();
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @since 1.0
	 */
	public AssociativeArray put(Object key, Object value) {
		set(key, value);
		return this;
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @since 1.0
	 */
	public AssociativeArray set(Object key, Object value) {
		if (key == null) {
			throw new IllegalArgumentException("The `key` must not be null.");
		}
		if (!keys.contains(key)) {
			if (key instanceof Integer) {
				nextIndex = Math.max(nextIndex, ((Integer) key) + 1);
			}
			keys.add(key);
		}
		backingMap.put(key, value);
		return this;
	}

	/**
	 * @param array
	 * @return
	 * @since 1.0
	 */
	public AssociativeArray merge(AssociativeArray array) {
		putAll(array.entries());
		return this;
	}

	/**
	 * @param array
	 * @return
	 */
	public AssociativeArray mergeInto(AssociativeArray array) {
		array.putAll(entries());
		return this;
	}

	/**
	 * @param entries
	 * @return
	 * @since 1.0
	 */
	public AssociativeArray putAll(
			Iterable<? extends Map.Entry<? extends Object, ? extends Object>> entries) {
		for (Map.Entry<? extends Object, ? extends Object> e : entries) {
			put(e.getKey(), e.getValue());
		}
		return this;
	}

	/**
	 * @param map
	 * @return
	 * @since 1.0
	 */
	public AssociativeArray putAll(Map<? extends Object, ? extends Object> map) {
		for (Map.Entry<? extends Object, ? extends Object> e : map.entrySet()) {
			put(e.getKey(), e.getValue());
		}
		return this;
	}

	@Override
	public boolean remove(Object object) {
		if (keys.contains(object)) {
			backingMap.remove(object);
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		return backingMap.size();
	}

	@Override
	public boolean equals(Object object) {
		return backingMap.equals(object);
	}

	@Override
	public int hashCode() {
		return backingMap.hashCode();
	}

	/**
	 * Finds the key of the first occurence of the specified value.
	 * 
	 * @param value
	 *            The value.
	 * @return The key of the first occurence of the specified value or null, if
	 *         this value is not contained in this array.
	 * @since 1.0
	 */
	public Object firstOccurenceOf(Object value) {
		for (Object key : keys) {
			if (ValueUtil.equals(value, backingMap.get(key))) {
				return key;
			}
		}
		return null;
	}

	/**
	 * Applies a function on every element in the array which is of the same
	 * type, or subtype, as the first argument of the function.
	 * <p>
	 * This method works in place.
	 * </p>
	 * 
	 * @param function
	 *            The function to apply on the elements in this array.
	 * @return This.
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public AssociativeArray walk(
			Function<? extends Object, ? extends Object> function) {
		Class<?> type = null;
		for (Type iface : function.getClass().getGenericInterfaces()) {
			if (iface == Function.class) {
				Type[] types = ((ParameterizedType) iface)
						.getActualTypeArguments();
				if (types[0] instanceof Class) {
					type = (Class<?>) types[0];
				} else {
					throw new IllegalArgumentException();
				}
			}
		}
		for (Object key : keys) {
			Object value = backingMap.get(key);
			if (type.isAssignableFrom(value.getClass())) {
				backingMap.put(key,
						((Function<Object, Object>) function).apply(value));
			}
		}
		return this;
	}

	/**
	 * Equivalent to calling <code>clone().walk(function)</code>.
	 * 
	 * @see #walk(Function)
	 * 
	 * @param function
	 *            The function to apply on the elements in this array.
	 * @return The newly created array.
	 * @since 1.0
	 */
	public AssociativeArray map(
			Function<? extends Object, ? extends Object> function) {
		return clone().walk(function);
	}

	/**
	 * @param function
	 * @return
	 * @since 1.0
	 */
	public AssociativeArray filter(Function<? extends Object, Boolean> function) {
		AssociativeArray clone = clone();
		clone.retainAll(function);
		return clone;
	}

	/**
	 * Changes the given key (the value associated with oldKey will be
	 * associated with newKey).
	 * 
	 * @param oldKey
	 *            The old key.
	 * @param newKey
	 *            The new key.
	 * @return This.
	 * @since 1.0
	 */
	public AssociativeArray changeKey(Object oldKey, Object newKey) {
		keys.set(keys.indexOf(oldKey), newKey);
		return this;
	}

	@Override
	public Iterator<Object> iterator() {
		return new Iterator<Object>() {
			Iterator<? extends Object> it = keys.iterator();
			Object lastKey = null;

			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public Object next() {
				return get(it.next());
			}

			@Override
			public void remove() {
				if (lastKey == null) {
					throw new IllegalStateException();
				}
				AssociativeArray.this.remove(lastKey);
			}
		};
	}

	@Override
	public boolean contains(Object value) {
		return backingMap.containsValue(value);
	}

	@Override
	public boolean containsAll(Collection<?> objects) {
		for (Object obj : objects) {
			if (!backingMap.containsValue(obj)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Removes all elements from this associative array which match the
	 * specified selector function.
	 * 
	 * The selector function may take any type as its first argument. If that
	 * first type is not Object, the function is only applied to elements of the
	 * same type (or subtype) as the first argument of the selector function.
	 * 
	 * Example: If you have the array <code>{0, 1, "guadaloupe"}</code> and
	 * apply a function <code>Function&lt;Integer, Boolean></code> which returns
	 * true for 1 and false for 0, than 1 will be removed (not the String, since
	 * it's not an Integer).
	 * 
	 * @param selector
	 *            The selector function.
	 * @return This.
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public AssociativeArray removeAll(
			Function<? extends Object, Boolean> selector) {

		Class<?> type = null;
		for (Type iface : selector.getClass().getGenericInterfaces()) {
			if (iface == Function.class) {
				Type[] types = ((ParameterizedType) iface)
						.getActualTypeArguments();
				if (types[0] instanceof Class) {
					type = (Class<?>) types[0];
				} else {
					throw new IllegalArgumentException();
				}
			}
		}
		for (Object key : keys) {
			Object value = backingMap.get(key);
			if (type.isAssignableFrom(value.getClass())) {
				if (((Function<Object, Boolean>) selector).apply(value)) {
					backingMap.remove(key);
				}
			}
		}
		return this;
	}

	@Override
	public boolean removeAll(Collection<?> objects) {
		boolean touched = false;
		for (Object key : keys) {
			if (objects.contains(backingMap.get(key))) {
				backingMap.remove(key);
				touched = true;
			}
		}
		return touched;
	}

	/**
	 * Removes all elements from this associative array which do not match the
	 * specified selector function.
	 * 
	 * The selector function may take any type as its first argument. If that
	 * first type is not Object, the function is applied to elements of the same
	 * type (or subtype) as the first argument of the selector function. All
	 * elements which are not of that type (or a subtype) will be removed to.
	 * 
	 * Example: If you have the array <code>{0, 1, "guadaloupe"}</code> and
	 * apply a function <code>Function&lt;Integer, Boolean></code> which returns
	 * true for 1 and false for 0, than 0 and "guadaloupe" will be removed (not
	 * the String, since it's not an Integer).
	 * 
	 * @param filter
	 *            The selector function.
	 * @return This.
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public AssociativeArray retainAll(Function<? extends Object, Boolean> filter) {
		Class<?> type = null;
		for (Type iface : filter.getClass().getGenericInterfaces()) {
			if (iface == Function.class) {
				Type[] types = ((ParameterizedType) iface)
						.getActualTypeArguments();
				if (types[0] instanceof Class) {
					type = (Class<?>) types[0];
				} else {
					throw new IllegalArgumentException();
				}
			}
		}
		for (Object key : keys) {
			Object value = backingMap.get(key);
			if (type.isAssignableFrom(value.getClass())) {
				if (((Function<Object, Boolean>) filter).apply(value)) {
					continue;
				}
			}
			backingMap.remove(key);
		}
		return this;
	}

	@Override
	public boolean retainAll(Collection<?> objects) {
		boolean touched = false;
		for (Object key : keys) {
			if (!objects.contains(backingMap.get(key))) {
				backingMap.remove(key);
				touched = true;
			}
		}
		return touched;
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[keys.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = get(keys.get(i));
		}
		return array;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Creates a new list from this Records values.
	 * 
	 * @since 1.0
	 * @return A list containing the values from this record.
	 */
	public List<Object> toList() {
		List<Object> values = new ArrayList<Object>(keys.size());
		for (Object key : keys) {
			values.add(get(key));
		}
		return values;
	}

	/**
	 * Creates a new list from this Records values, containing only the elements
	 * of the specified type (or subtype).
	 * 
	 * @param clazz
	 *            The Type.
	 * @return A list containing the values from this record of type `clazz`.
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> toList(Class<T> clazz) {
		List<T> values = new ArrayList<T>(keys.size());
		for (Object key : keys) {
			Object value = get(key);
			if (clazz.isAssignableFrom(value.getClass())) {
				values.add((T) value);
			}
		}
		return values;
	}

	/**
	 * Creates a new {@link Map} from this Record.
	 * 
	 * @since 1.0
	 * @return A new Map containing the key/value pairs from this Record.
	 */
	public Map<Object, Object> toMap() {
		return new HashMap<Object, Object>(backingMap);
	}

	/**
	 * Creates a new {@link SortedMap} from this Record.
	 * 
	 * @since 1.0
	 * @return A new Map containing the key/value pairs from this Record.
	 */
	public SortedMap<Object, Object> toSortedMap() {
		TreeMap<Object, Object> map = new TreeMap<Object, Object>(
				new AnyComparator());
		map.putAll(backingMap);
		return map;
	}

	/**
	 * Creates a new {@link NavigableMap} from this Record.
	 * 
	 * @since 1.0
	 * @return A new Map containing the key/value pairs from this Record.
	 */
	public NavigableMap<Object, Object> toNavigableMap() {
		TreeMap<Object, Object> map = new TreeMap<Object, Object>(
				new AnyComparator());
		map.putAll(backingMap);
		return map;
	}

	/**
	 * Returns an unmodifiable view on the backing map.
	 * 
	 * @since 1.0
	 * @return A view on the backing map.
	 */
	public Map<Object, Object> getMap() {
		return Collections.unmodifiableMap(backingMap);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		toString(builder);
		return builder.toString();
	}

	/**
	 * Writes the StringRepresentation into the specified StringBuilder.
	 * 
	 * @param builder
	 *            The StringBuilder.
	 * @return This.
	 * @since 1.0
	 */
	public AssociativeArray toString(StringBuilder builder) {
		boolean first = true;
		for (Entry entry : entries()) {
			if (first) {
				first = false;
			} else {
				builder.append(", ");
			}
			builder.append(entry.getKey().toString());
			builder.append(": ");
			Object value = entry.getValue();
			if (value == null) {
				builder.append("<null>");
			} else {
				builder.append(value);
			}
		}
		return this;
	}
}