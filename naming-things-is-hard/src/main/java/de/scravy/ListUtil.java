package de.scravy;

import java.util.ArrayList;
import java.util.List;

public final class ListUtil {

    private ListUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Good ol' join/concat/...
     */
    public static <T> List<T> flatten(final List<? extends List<? extends T>> lists) {
        final List<T> result = new ArrayList<>();
        for (final List<? extends T> list : lists) {
            result.addAll(list);
        }
        return result;
    }
}
