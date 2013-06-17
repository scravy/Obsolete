package net.scravy.technetium.util.data;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableMap;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;

import net.scravy.technetium.util.ArrayDequeList;
import net.scravy.technetium.util.function.Function;
import net.scravy.technetium.util.function.Function2;
import net.scravy.technetium.util.value.ValueUtil;

/**
 * @author Julian Fleischer
 * @since 1.0
 */
public class DataTable extends ArrayDequeList<AssociativeArray> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1373638694697388218L;

	/**
	 * @since 1.0
	 */
	public DataTable() {

	}

	/**
	 * Constructs a DataTable from the specified Iterable.
	 * 
	 * @param elements
	 *            The elements.
	 */
	public DataTable(Iterable<AssociativeArray> elements) {
		super(elements);
	}

	/**
	 * Constructs a DataTable from the specified Collection.
	 * 
	 * @param collection
	 *            The Collection.
	 */
	public DataTable(Collection<AssociativeArray> collection) {
		super(collection);
	}

	@Override
	public DataTable clone() {
		return new DataTable(this);
	}

	/**
	 * Creates a <b>new</b> DataTable containing only the specified columns.
	 * 
	 * @param columns
	 *            The keys of the columns.
	 * @return The new DataTable.
	 */
	public DataTable select(Object... columns) {
		DataTable newTable = new DataTable();
		for (AssociativeArray array : this) {
			AssociativeArray newArray = new AssociativeArray();
			for (Object key : columns) {
				newArray.set(key, array.get(key));
			}
			newTable.add(newArray);
		}
		return newTable;
	}

	/**
	 * Returns an AssociativeArray containing all the values from the specified
	 * column.
	 * 
	 * @param column
	 *            The key of the column.
	 * @return The new DataTable.
	 */
	public AssociativeArray columnValues(Object column) {
		AssociativeArray array = new AssociativeArray();
		for (AssociativeArray a : this) {
			array.add(a.get(column));
		}
		return array;
	}

	/**
	 * Returns the values form the specified column as a List.
	 * 
	 * @param column
	 *            The key of the column.
	 * @return The List with the values from the column.
	 */
	public ArrayDequeList<Object> columnValuesList(Object column) {
		ArrayDequeList<Object> list = new ArrayDequeList<Object>(size());
		for (AssociativeArray a : this) {
			list.add(a.get(column));
		}
		return list;
	}

	/**
	 * Returns the values from the specified column as a Set, that is, without
	 * order and without duplicates.
	 * 
	 * @param column
	 *            The key of the column.
	 * @return The Set with the values from the column.
	 */
	public Set<Object> columnValuesSet(Object column) {
		HashSet<Object> set = new HashSet<Object>(size());
		for (AssociativeArray array : this) {
			set.add(array.get(column));
		}
		return set;
	}

	/**
	 * Returns the values from the specified column as a SortedSet, that is,
	 * without duplicates and sorted according to {@link AnyComparator}.
	 * 
	 * @param column
	 *            The key of the column.
	 * @return The SortedSet with the values from the column.
	 */
	public SortedSet<Object> columnValuesSortedSet(Object column) {
		TreeSet<Object> set = new TreeSet<Object>(new AnyComparator());
		for (AssociativeArray array : this) {
			set.add(array.get(column));
		}
		return set;
	}

	/**
	 * @param column
	 * @return
	 */
	public NavigableMap<Object, AssociativeArray> groupByUnique(Object column) {
		TreeMap<Object, AssociativeArray> map =
				new TreeMap<Object, AssociativeArray>(new AnyComparator());
		for (AssociativeArray array : this) {
			Object keyValue = array.get(column);
			if (keyValue != null) {
				map.put(keyValue, array);
			}
		}
		return map;
	}

	/**
	 * @param column
	 * @return
	 */
	public NavigableMap<Object, List<AssociativeArray>> groupBy(Object column) {
		TreeMap<Object, List<AssociativeArray>> map =
				new TreeMap<Object, List<AssociativeArray>>(new AnyComparator());
		for (AssociativeArray array : this) {
			Object keyValue = array.get(column);
			if (keyValue != null) {
				List<AssociativeArray> list;
				if (!map.containsKey(keyValue)) {
					list = new ArrayList<AssociativeArray>();
					map.put(keyValue, list);
				} else {
					list = map.get(keyValue);
				}
				list.add(array);
			}
		}
		return map;
	}

	/**
	 * @param column
	 * @param value
	 * @return
	 */
	public boolean contains(Object column, Object value) {
		for (AssociativeArray array : this) {
			if (ValueUtil.equals(value, array.get(column))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param table
	 * @param joinKey
	 * @return
	 */
	public DataTable join(DataTable table, Object joinKey) {
		return join(table, joinKey, joinKey);
	}

	/**
	 * @param table
	 * @param leftJoinKey
	 * @param rightJoinKey
	 * @return
	 */
	public DataTable join(
			DataTable table, Object leftJoinKey, Object rightJoinKey) {
		DataTable joinTable = new DataTable();
		AnyComparator comparator = new AnyComparator();

		SortedMap<Object, AssociativeArray> leftIndex =
				this.groupByUnique(leftJoinKey);
		SortedMap<Object, AssociativeArray> rightIndex =
				table.groupByUnique(rightJoinKey);
		Iterator<Object> leftIterator = leftIndex.keySet().iterator();
		Iterator<Object> rightIterator = rightIndex.keySet().iterator();
		Object leftValue = leftIterator.hasNext()
				? leftIterator.next() : null;
		Object rightValue = rightIterator.hasNext()
				? rightIterator.next() : null;

		while (leftValue != null && rightValue != null) {
			int comparison = comparator.compare(leftValue, rightValue);

			if (comparison < 0) {
				leftValue = leftIterator.hasNext()
						? leftIterator.next() : null;
			} else if (comparison > 0) {
				rightValue = rightIterator.hasNext()
						? rightIterator.next() : null;
			} else if (comparison == 0) {
				joinTable.add(new AssociativeArray()
						.merge(leftIndex.get(leftValue))
						.merge(rightIndex.get(rightValue)));
				leftValue = leftIterator.hasNext()
						? leftIterator.next() : null;
				rightValue = rightIterator.hasNext()
						? rightIterator.next() : null;
			}
		}

		return joinTable;
	}

	/**
	 * @param table
	 * @param joinKey
	 * @return
	 */
	public DataTable joinOrderPreserving(DataTable table, Object joinKey) {
		return joinOrderPreserving(table, joinKey, joinKey);
	}

	/**
	 * @param table
	 * @param leftJoinKey
	 * @param rightJoinKey
	 * @return
	 */
	public DataTable joinOrderPreserving(
			DataTable table, Object leftJoinKey, Object rightJoinKey) {
		DataTable joinTable = new DataTable();

		for (AssociativeArray arr1 : this) {
			Object value = arr1.get(leftJoinKey);
			for (AssociativeArray arr2 : table) {
				if (ValueUtil.equals(value, arr2.get(rightJoinKey))) {
					AssociativeArray array = new AssociativeArray();
					for (Object key : arr1.keysList()) {
						array.set(key, arr1.get(key));
					}
					for (Object key : arr2.keysList()) {
						array.set(key, arr2.get(key));
					}
					joinTable.add(array);
					break;
				}
			}
		}
		return joinTable;
	}

	/**
	 * @param table
	 * @param joinKey
	 * @return
	 */
	public DataTable outerJoin(DataTable table, Object joinKey) {
		return outerJoin(table, joinKey, joinKey);
	}

	/**
	 * @param table
	 * @param leftJoinKey
	 * @param rightJoinKey
	 * @return
	 */
	public DataTable outerJoin(
			DataTable table, Object leftJoinKey, Object rightJoinKey) {
		DataTable joinTable = new DataTable();
		AnyComparator comparator = new AnyComparator();

		SortedMap<Object, AssociativeArray> leftIndex =
				this.groupByUnique(leftJoinKey);
		SortedMap<Object, AssociativeArray> rightIndex =
				table.groupByUnique(rightJoinKey);
		Iterator<Object> leftIterator = leftIndex.keySet().iterator();
		Iterator<Object> rightIterator = rightIndex.keySet().iterator();
		Object leftValue = leftIterator.hasNext()
				? leftIterator.next() : null;
		Object rightValue = rightIterator.hasNext()
				? rightIterator.next() : null;
		while (leftValue != null && rightValue != null) {
			int comparison = comparator.compare(leftValue, rightValue);

			if (comparison < 0) {
				joinTable.add(new AssociativeArray()
						.merge(leftIndex.get(leftValue)));
				leftValue = leftIterator.hasNext()
						? leftIterator.next() : null;
			} else if (comparison > 0) {
				joinTable.add(new AssociativeArray()
						.merge(rightIndex.get(rightValue)));
				rightValue = rightIterator.hasNext()
						? rightIterator.next() : null;
			} else if (comparison == 0) {
				joinTable.add(new AssociativeArray()
						.merge(leftIndex.get(leftValue))
						.merge(rightIndex.get(rightValue)));
				leftValue = leftIterator.hasNext()
						? leftIterator.next() : null;
				rightValue = rightIterator.hasNext()
						? rightIterator.next() : null;
			}
		}
		while (leftValue != null) {
			joinTable.add(new AssociativeArray()
					.merge(leftIndex.get(leftValue)));
			leftValue = leftIterator.hasNext()
					? leftIterator.next() : null;
		}
		while (rightValue != null) {
			joinTable.add(new AssociativeArray()
					.merge(rightIndex.get(rightValue)));
			rightValue = rightIterator.hasNext()
					? rightIterator.next() : null;
		}

		return joinTable;
	}

	/**
	 * @param table
	 * @param joinKey
	 * @return
	 */
	public DataTable leftJoin(DataTable table, Object joinKey) {
		return leftJoin(table, joinKey, joinKey);
	}

	/**
	 * @param table
	 * @param leftJoinKey
	 * @param rightJoinKey
	 * @return
	 */
	public DataTable leftJoin(
			DataTable table, Object leftJoinKey, Object rightJoinKey) {
		DataTable joinTable = new DataTable();
		for (AssociativeArray arr1 : this) {
			Object value = arr1.get(leftJoinKey);
			boolean found = false;
			for (AssociativeArray arr2 : table) {
				if (ValueUtil.equals(value, arr2.get(rightJoinKey))) {
					AssociativeArray array = new AssociativeArray();
					for (Object key : arr1.keysList()) {
						array.set(key, arr1.get(key));
					}
					for (Object key : arr2.keysList()) {
						array.set(key, arr2.get(key));
					}
					joinTable.add(array);
					found = true;
					break;
				}
			}
			if (!found) {
				AssociativeArray array = new AssociativeArray();
				for (Object key : arr1.keysList()) {
					array.set(key, arr1.get(key));
				}
				joinTable.add(array);
			}
		}
		return joinTable;
	}

	/**
	 * @param table
	 * @param joinKey
	 * @return
	 */
	public DataTable rightJoin(DataTable table, Object joinKey) {
		return rightJoin(table, joinKey, joinKey);
	}

	/**
	 * @param table
	 * @param leftJoinKey
	 * @param rightJoinKey
	 * @return
	 */
	public DataTable rightJoin(
			DataTable table, Object leftJoinKey, Object rightJoinKey) {
		DataTable joinTable = new DataTable();
		for (AssociativeArray arr2 : table) {
			Object value = arr2.get(rightJoinKey);
			boolean found = false;
			for (AssociativeArray arr1 : this) {
				if (ValueUtil.equals(value, arr1.get(leftJoinKey))) {
					AssociativeArray array = new AssociativeArray();
					for (Object key : arr2.keysList()) {
						array.set(key, arr2.get(key));
					}
					for (Object key : arr1.keysList()) {
						array.set(key, arr1.get(key));
					}
					joinTable.add(array);
					found = true;
					break;
				}
			}
			if (!found) {
				AssociativeArray array = new AssociativeArray();
				for (Object key : arr2.keysList()) {
					array.set(key, arr2.get(key));
				}
				joinTable.add(array);
			}
		}
		return joinTable;
	}

	/**
	 * @param column
	 * @param needle
	 * @param replacement
	 * @return
	 */
	public int replace(Object column, Object needle, Object replacement) {
		int count = 0;
		for (AssociativeArray array : this) {
			if (ValueUtil.equals(needle, array.get(column))) {
				array.put(column, replacement);
				count++;
			}
		}
		return count;
	}

	/**
	 * @param column
	 * @param regex
	 * @param replacement
	 * @return
	 */
	public int replaceRegex(Object column, Pattern regex, Object replacement) {
		int count = 0;
		for (AssociativeArray array : this) {
			if (regex.matcher(String.valueOf(array.get(column))).matches()) {
				array.put(column, replacement);
				count++;
			}
		}
		return count;
	}

	/**
	 * @param column
	 * @param regex
	 * @param replacement
	 * @return
	 */
	public int replaceRegex(Object column, String regex, Object replacement) {
		int count = 0;
		for (AssociativeArray array : this) {
			if (Pattern.matches(regex, String.valueOf(array.get(column)))) {
				array.put(column, replacement);
				count++;
			}
		}
		return count;
	}

	/**
	 * @param column
	 * @return
	 */
	public double sum(Object column) {
		double sum = 0;
		for (AssociativeArray array : this) {
			Object value = array.get(column);
			if (value instanceof Number) {
				sum += ((Number) value).doubleValue();
			}
		}
		return sum;
	}

	/**
	 * @param column
	 * @return
	 */
	public double avg(Object column) {
		double sum = 0;
		long num = 0;
		for (AssociativeArray array : this) {
			Object value = array.get(column);
			if (value instanceof Number) {
				sum += ((Number) value).doubleValue();
				num++;
			}
		}
		return sum / num;
	}

	/**
	 * @param column
	 * @return
	 */
	public long integralSum(Object column) {
		long sum = 0;
		for (AssociativeArray array : this) {
			Object value = array.get(column);
			if (value instanceof Integer || value instanceof Long
					|| value instanceof Short || value instanceof Byte) {
				sum += ((Number) value).longValue();
			}
		}
		return sum;
	}

	/**
	 * @param column
	 * @return
	 */
	public BigDecimal bigSum(Object column) {
		BigDecimal sum = BigDecimal.ZERO;
		for (AssociativeArray array : this) {
			Object value = array.get(column);
			if (value instanceof BigInteger) {
				sum = sum.add(new BigDecimal((BigInteger) value));
			} else if (value instanceof BigDecimal) {
				sum = sum.add((BigDecimal) value);
			} else if (value instanceof Integer || value instanceof Long
					|| value instanceof Short || value instanceof Byte) {
				sum = sum.add(BigDecimal.valueOf(((Number) value).longValue()));
			}
		}
		return sum;
	}

	/**
	 * @param column
	 * @return
	 */
	public BigDecimal bigAvg(Object column) {
		BigDecimal sum = BigDecimal.ZERO;
		int count = 0;
		for (AssociativeArray array : this) {
			Object value = array.get(column);
			if (value instanceof BigInteger) {
				sum = sum.add(new BigDecimal((BigInteger) value));
			} else if (value instanceof BigDecimal) {
				sum = sum.add((BigDecimal) value);
			} else if (value instanceof Integer || value instanceof Long
					|| value instanceof Short || value instanceof Byte) {
				sum = sum.add(BigDecimal.valueOf(((Number) value).longValue()));
			} else {
				continue;
			}
			count++;
		}
		return sum.divide(BigDecimal.valueOf(count));
	}

	/**
	 * @param column
	 * @return
	 */
	public BigInteger bigIntegralSum(Object column) {
		BigInteger sum = BigInteger.ZERO;
		for (AssociativeArray array : this) {
			Object value = array.get(column);
			if (value instanceof BigInteger) {
				sum = sum.add((BigInteger) value);
			} else if (value instanceof Integer || value instanceof Long
					|| value instanceof Short || value instanceof Byte) {
				sum = sum.add(BigInteger.valueOf(((Number) value).longValue()));
			}
		}
		return sum;
	}

	/**
	 * Count how many elements in the column are not empty.
	 * 
	 * Emptiness is checked using {@link ValueUtil#isEmpty(Object)}.
	 * 
	 * @param column
	 *            The key of the column to count.
	 * @return The number of values in the column for which
	 *         {@link ValueUtil#isEmpty(Object)} does not return true.
	 * @since 1.0
	 */
	public int count(Object column) {
		int count = 0;
		for (AssociativeArray array : this) {
			if (!ValueUtil.isEmpty(array.get(column))) {
				count++;
			}
		}
		return count;
	}

	/**
	 * @param column
	 * @param where
	 * @return
	 */
	public int count(Object column, Function<Object, Boolean> where) {
		int count = 0;
		for (AssociativeArray array : this) {
			if (where.apply(array.get(column))) {
				count++;
			}
		}
		return count;
	}

	/**
	 * @param column
	 * @param where
	 * @return
	 */
	public int count(Object column,
			Function2<AssociativeArray, Object, Boolean> where) {
		int count = 0;
		for (AssociativeArray array : this) {
			if (where.apply(array, column)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Count how many keys are in the column.
	 * 
	 * @param column
	 *            The key of the column to count.
	 * @return The number of elements in the column for which
	 *         {@link AssociativeArray#containsKey(Object)} returns true.
	 * @since 1.0
	 */
	public int countKeys(Object column) {
		int count = 0;
		for (AssociativeArray array : this) {
			if (array.containsKey(column)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Count how many values in the column are not null.
	 * 
	 * @param column
	 *            The key of the column to count.
	 * @return The number of elements in the column which are not null.
	 * @since 1.0
	 */
	public int countValues(Object column) {
		int count = 0;
		for (AssociativeArray array : this) {
			if (array.get(column) != null) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Count how many values in the column are not null.
	 * 
	 * This method is not the exact opposite of {@link #countValues(Object)}, as
	 * it only checks cells for which a key is actually associated (that is, the
	 * following expression is checked:
	 * <code>array.containsKey(column) && array.get(column) == null</code>).
	 * 
	 * @param column
	 *            The key of the column to count.
	 * @return The number of elements in the column which are null.
	 * @since 1.0
	 */
	public int countNull(Object column) {
		int count = 0;
		for (AssociativeArray array : this) {
			if (array.containsKey(column) && array.get(column) == null) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Count how many values are of the specified class.
	 * 
	 * @param column
	 *            The key of the column to count.
	 * @param clazz
	 *            The class.
	 * @return The number of elements in the column for whose type
	 *         {@link Class#isAssignableFrom(Class)} (applied on clazz) returns
	 *         true.
	 * @since 1.0
	 */
	public int count(Object column, Class<?> clazz) {
		int count = 0;
		for (AssociativeArray array : this) {
			Object value = array.get(column);
			if (value != null && clazz.isAssignableFrom(value.getClass())) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Count how many values are equal to the specified value.
	 * 
	 * @param column
	 *            The key of the column to count.
	 * @param value
	 *            The value used for comparison.
	 * @return The number of elements in the for which
	 *         {@link ValueUtil#equals(Object,Object)} returns true (applied on
	 *         the specified value and the value in the cell).
	 * @since 1.0
	 */
	public int count(Object column, Object value) {
		int count = 0;
		for (AssociativeArray array : this) {
			if (ValueUtil.equals(value, array.get(column))) {
				count++;
			}
		}
		return count;
	}

	/**
	 * @param column
	 * @param regex
	 * @param replacement
	 * @return
	 */
	public int countRegex(Object column, String regex, Object replacement) {
		int count = 0;
		for (AssociativeArray array : this) {
			if (Pattern.matches(regex, ValueUtil.toString(array.get(column)))) {
				count++;
			}
		}
		return count;
	}

	/**
	 * @param byColumn
	 * @return
	 */
	public DataTable sort(final Object... byColumn) {
		Collections.sort(this, new Comparator<AssociativeArray>() {
			AnyComparator comparator = new AnyComparator();

			@Override
			public int compare(AssociativeArray left, AssociativeArray right) {
				for (Object column : byColumn) {
					int comparison = comparator.compare(
							left.get(column), right.get(column));
					if (comparison != 0) {
						return comparison;
					}
				}
				return 0;
			}
		});
		return this;
	}

	/**
	 * @param byColumn
	 * @return
	 */
	public DataTable orderBy(final Object... byColumn) {
		return clone().sort(byColumn);
	}

	/**
	 * @param function
	 * @return
	 */
	public DataTable walk(Function<AssociativeArray, AssociativeArray> function) {
		for (int i = 0; i < size(); i++) {
			set(i, function.apply(get(i)));
		}
		return this;
	}

	/**
	 * @param function
	 * @return
	 */
	public DataTable map(Function<AssociativeArray, AssociativeArray> function) {
		return clone().walk(function);
	}

	/**
	 * @param column
	 * @param function
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public DataTable walk(Object column,
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
		for (AssociativeArray array : this) {
			Object value = array.get(column);
			if (type.isAssignableFrom(value.getClass())) {
				array.set(column,
						((Function<Object, Object>) function).apply(value));
			}
		}
		return this;
	}

	/**
	 * @param column
	 * @param function
	 * @return
	 */
	public DataTable map(Object column,
			Function<? extends Object, ? extends Object> function) {
		return clone().walk(column, function);
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof DataTable)) {
			return false;
		}
		DataTable table = (DataTable) object;
		if (size() != table.size()) {
			return false;
		}
		Iterator<AssociativeArray> left = iterator();
		Iterator<AssociativeArray> right = table.iterator();

		while (left.hasNext() && right.hasNext()) {
			AssociativeArray dataLeft = left.next();
			AssociativeArray dataRight = right.next();
			Set<Object> keysLeft = new HashSet<Object>(dataLeft.keysList());
			Set<Object> keysRight = new HashSet<Object>(dataRight.keysList());
			if (!keysLeft.equals(keysRight)) {
				return false;
			}
			for (Object key : keysLeft) {
				if (!ValueUtil.equals(dataLeft.get(key), dataRight.get(key))) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		toString(builder);
		return builder.toString();
	}

	/**
	 * @param builder
	 * @return
	 */
	public DataTable toString(StringBuilder builder) {
		for (AssociativeArray array : this) {
			builder.append(array.toString());
			builder.append('\n');
		}
		return this;
	}
}