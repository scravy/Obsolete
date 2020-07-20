package de.scravy.machina;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.Iterator;

import lombok.experimental.UtilityClass;

/**
 * Static utility methods for dealing with events of all kinds of sorts.
 *
 * @since 1.0.0
 * @author Julian Fleischer
 */
@UtilityClass
public class Events {

  /**
   * Build an iterable that returns the characters in a string - useful for
   * parsing strings and treating characters as events.
   *
   * @param string
   *          The string.
   * @return An iterable which allows to iterate over the characters in the
   *         given string.
   */
  public static Iterable<Integer> fromString(final String string) {
    return fromReader(new StringReader(string));
  }

  public static Iterable<Integer> fromStream(final InputStream stream) {
    return fromReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
  }

  public static Iterable<Integer> fromReader(final Reader reader) {
    return new Iterable<Integer>() {

      @Override
      public Iterator<Integer> iterator() {

        final Iterator<Integer> it = new Iterator<Integer>() {

          int c = 0;

          @Override
          public boolean hasNext() {
            return c > -2;
          }

          @Override
          public Integer next() {
            final int cc = c;
            try {
              if (cc < 0) {
                c -= 1;
              } else {
                c = reader.read();
              }
            } catch (final IOException exc) {
              throw new RuntimeException(exc);
            }
            return Integer.valueOf(cc);
          }

          @Override
          public void remove() {
            throw new UnsupportedOperationException();
          }

        };
        it.next();
        return it;
      }

    };
  }
}
