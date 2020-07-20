package de.scravy;

public class Comparables {

    @SafeVarargs
    public static <T extends Comparable<T>> int compare(final T... comparables) {
        for (int i = 0; i + 1 < comparables.length; i += 2) {
            if (comparables[i] == comparables[i+1]) {
                return 0;
            }
            if (comparables[i] == null) {
                return -1;
            }
            final int result = comparables[i].compareTo(comparables[i+1]);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }
}
