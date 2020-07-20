package de.scravy;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class ListView<T> extends AbstractList<T> implements List<T>, Serializable {

    public static void main(final String... args) {
        final List<Integer> xs = Arrays.asList(1, 2, 3, 4, 5);

        final List<Integer> ys = ListView.of(xs, 1);
        final List<Integer> zs = ListView.of(ys, 1);

        System.out.println(xs.get(0));
        System.out.println(ys.get(0));
        System.out.println(zs.get(0));

        System.out.println(ListView.of(zs, 3).isEmpty());

        zs.forEach(System.out::println);
    }

    private final List<T> parent;
    private final int offset;

    private ListView(final List<T> parent, final int offset) {
        this.parent = parent;
        this.offset = offset;
    }

    public static <T> ListView<T> of(final T[] parent) {
        return of(Arrays.asList(parent));
    }

    public static <T> ListView<T> of(final T[] parent, final int offset) {
        return of(Arrays.asList(parent), offset);
    }

    public static <T> ListView<T> of(final List<T> parent) {
        if (parent instanceof ListView) {
            return (ListView<T>) parent;
        }
        return new ListView<>(parent, 0);
    }

    public static <T> ListView<T> of(final List<T> parent, final int offset) {
        if (parent instanceof ListView) {
            final ListView<T> listView = (ListView<T>) parent;
            return new ListView<>(listView.parent, listView.offset + offset);
        }
        return new ListView<>(parent, offset);
    }


    @Override
    public int size() {
        return parent.size() - offset;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return parent.listIterator(offset);
    }

    @Override
    public Object[] toArray() {
        final Object[] array = new Object[size()];
        int ix = 0;
        for (final Object obj : this) {
            array[ix] = obj;
            ix += 1;
        }
        return array;
    }

    @Override
    public boolean add(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(final Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(final Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T get(int index) {
        return parent.get(offset + index);
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator() {
        return parent.listIterator(offset);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return parent.listIterator(offset + index);
    }

    @Override
    public String toString() {
        return stream().map(Object::toString).collect(Collectors.joining(", "));
    }
}
