package net.scravy.technetium.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.RandomAccess;

import net.scravy.technetium.util.iterator.IteratorUtil;

/**
 * A List that is also a Deque (backed by an {@link ArrayList}, so that you can
 * use ArrayList as versatile as {@link LinkedList} which equally implements
 * both interfaces).
 * 
 * @author Julian Fleischer
 * @since 1.0
 * 
 * @param <E>
 *            The type of the elements.
 */
public class ArrayDequeList<E> extends ArrayList<E> implements Deque<E>,
		Collection2<E>, Serializable, Cloneable, RandomAccess {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4207296969739058777L;

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
	 * Default no-arg constructor.
	 * 
	 * @since 1.0
	 */
	public ArrayDequeList() {

	}

	/**
	 * Creates a new ArrayDequeList with the specified initial capacity.
	 * 
	 * @param capacity
	 *            The initial capacity.
	 * @since 1.0
	 */
	public ArrayDequeList(int capacity) {
		super(capacity);
	}

	/**
	 * Creates a new ArrayDequeList from the specified elements.
	 * 
	 * @param elements
	 *            The elements, given as an Iterable.
	 * @since 1.0
	 */
	public ArrayDequeList(Iterable<E> elements) {
		addAll(elements);
	}

	/**
	 * Creates a new ArrayDequeList from the specified collection.
	 * 
	 * @param elements
	 *            The elements, given as a Collection.
	 * @since 1.0
	 */
	public ArrayDequeList(Collection<E> elements) {
		this.addAll(elements);
	}

	/**
	 * Creates a new ArrayDequeList from the specified varargs array.
	 * 
	 * @param elements
	 *            The elements, given as an Array.
	 * @since 1.0
	 */
	public ArrayDequeList(E... elements) {
		addAll(elements);
	}

	@Override
	public ArrayDequeList<E> clone() {
		return new ArrayDequeList<E>(this);
	}

	@Override
	public boolean addAll(Iterable<? extends E> collection) {
		for (E e : collection) {
			add(e);
		}
		return true;
	}

	@Override
	public void addFirst(E object) {
		this.add(0, object);
	}

	@Override
	public void addLast(E object) {
		this.add(object);
	}

	@Override
	public E element() {
		return this.get(0);
	}

	@Override
	public E getFirst() {
		return this.get(0);
	}

	@Override
	public E getLast() {
		return this.get(size() - 1);
	}

	@Override
	public boolean offer(E object) {
		this.add(0, object);
		return true;
	}

	@Override
	public boolean offerFirst(E object) {
		this.add(0, object);
		return true;
	}

	@Override
	public boolean offerLast(E object) {
		this.add(object);
		return true;
	}

	@Override
	public E peek() {
		int size = this.size();
		if (size == 0) {
			return null;
		}
		return this.get(size - 1);
	}

	@Override
	public E peekFirst() {
		if (this.isEmpty()) {
			return null;
		}
		return this.get(0);
	}

	@Override
	public E peekLast() {
		int size = size();
		if (size == 0) {
			return null;
		}
		return this.get(size - 1);
	}

	@Override
	public E poll() {
		return remove(0);
	}

	@Override
	public E pollFirst() {
		return remove(0);
	}

	@Override
	public E pollLast() {
		int size = this.size();
		if (size == 0) {
			throw new NoSuchElementException();
		}
		return remove(size - 1);
	}

	@Override
	public E pop() {
		return this.remove(0);
	}

	@Override
	public void push(E object) {
		this.add(0, object);
	}

	@Override
	public E remove() {
		return this.remove(0);
	}

	@Override
	public E removeFirst() {
		return this.remove(0);
	}

	@Override
	public boolean removeFirstOccurrence(Object object) {
		for (ListIterator<E> it = this.listIterator(); it.hasNext();) {
			if (object.equals(it.next())) {
				it.remove();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean removeLastOccurrence(Object object) {
		if (isEmpty()) {
			return false;
		}
		for (ListIterator<E> it = this.listIterator(); it.hasPrevious();) {
			if (object.equals(it.previous())) {
				it.remove();
				return true;
			}
		}
		return false;
	}

	@Override
	public E removeLast() {
		int size = this.size();
		if (size == 0) {
			throw new NoSuchElementException();
		}
		return this.remove(size - 1);
	}

	@Override
	public Iterator<E> descendingIterator() {
		if (this.isEmpty()) {
			return IteratorUtil.emptyIterator();
		}
		return new Iterator<E>() {
			ListIterator<E> it = listIterator(size() - 1);

			@Override
			public boolean hasNext() {
				return it.hasPrevious();
			}

			@Override
			public E next() {
				return it.previous();
			}

			@Override
			public void remove() {
				it.remove();
			}
		};
	}

	@Override
	public E rand(Random random) {
		if (random == null) {
			throw new IllegalArgumentException(
					"`random` must not be null. Consider using rand() instead (without parameters).");
		}
		return get(random.nextInt(size()));
	}

	@Override
	public E rand() {
		return get(random.get().nextInt(size()));
	}

	@Override
	public boolean addAll(E... objects) {
		this.ensureCapacity(size() + objects.length);
		for (E object : objects) {
			this.add(object);
		}
		return true;
	}
}