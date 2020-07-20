package de.scravy.machina;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.experimental.UtilityClass;

/**
 * Static utility methods for dealing with state machines.
 *
 * @since 1.0.0
 * @author Julian Fleischer
 */
@UtilityClass
public class StateMachines {

  /**
   * Enumeration of output formats for serializing state machine definitions.
   *
   * @author Julian Fleischer
   * @since 1.0.0
   */
  public static enum OutputFormat {

    /**
     * Output format as XML.
     *
     * @since 1.0.0
     */
    XML(XMLSerializer.class),

    /**
     * Output format as JSON.
     *
     * @since 1.0.0
     */
    JSON(JSONSerializer.class),

    /**
     * Output format in Graphviz DOT language.
     *
     * @since 1.0.0
     */
    Graphviz(GraphvizSerializer.class);

    private final Class<? extends StateMachineSerializer> serializer;

    StateMachineSerializer getSerializer() {
      try {
        return serializer.newInstance();
      } catch (final Exception exc) {
        throw new RuntimeException(exc);
      }
    }

    OutputFormat(final Class<? extends StateMachineSerializer> serializer) {
      this.serializer = serializer;
    }
  }

  private static <S, T> void toAppendable(
      final StateMachine<S, ?, T> fsm,
      final OutputFormat format,
      final Appendable appendable) {
    if (format == null) {
      throw new IllegalArgumentException("`format' must not be null.");
    }
    try {
      format.getSerializer().serialize(fsm, appendable);
    } catch (final IOException exc) {
      throw new RuntimeException(exc);
    }
  }

  /**
   * Serialize a given {@link StateMachine} definition to a String.
   *
   * @since 1.0.0
   * @param fsm
   *          The state machine.
   * @param format
   *          The format to use for serialization.
   * @return A serialization in the given output format.
   */
  public static <S, T> String toString(
      final StateMachine<S, ?, T> fsm, final OutputFormat format) {
    final StringBuilder builder = new StringBuilder();
    toString(fsm, format, builder);
    return builder.toString();
  }

  /**
   * Serialize a given {@link StateMachine} definition to a
   * {@link StringBuilder}.
   *
   * @since 1.0.0
   * @param fsm
   *          The state machine.
   * @param format
   *          The format to use for serialization.
   * @param builder
   *          The {@link StringBuilder} to append the serialization to.
   *
   * @param <S>
   *          State
   * @param <T>
   *          Event Type
   * @return That StringBuilder.
   */
  public static <S, T> StringBuilder toString(
      final StateMachine<S, ?, T> fsm, final OutputFormat format,
      final StringBuilder builder) {
    toAppendable(fsm, format, builder);
    return builder;
  }

  /**
   * Serialize a given state machine definition as a file.
   *
   * @since 1.0.0
   * @param fsm
   *          The state machine.
   * @param format
   *          The format to use for serialization.
   * @param filePath
   *          The filepath where to write to.
   *
   * @param <S>
   *          State
   * @param <T>
   *          Event Type
   */
  public static <S, T> void toFile(
      final StateMachine<S, ?, T> fsm,
      final OutputFormat format,
      final String filePath) {
    toFile(fsm, format, new File(filePath));
  }

  /**
   * Serialize a given state machine definition as a file.
   *
   * @since 1.0.0
   * @param fsm
   *          The state machine.
   * @param format
   *          The format to use for serialization.
   * @param file
   *          The file object representing that file.
   *
   * @param <S>
   *          State
   * @param <T>
   *          Event Type
   */
  public static <S, T> void toFile(
      final StateMachine<S, ?, T> fsm,
      final OutputFormat format,
      final File file) {
    try {
      toStream(fsm, format, new FileOutputStream(file));
    } catch (final IOException exc) {
      throw new RuntimeException(exc);
    }
  }

  /**
   * Serialize a given state machine definition as a file.
   *
   * @since 1.0.0
   * @param fsm
   *          The state machine.
   * @param format
   *          The format to use for serialization.
   * @param filePath
   *          The filepath where to write to.
   *
   * @param <S>
   *          State
   * @param <T>
   *          Event Type
   */
  public static <S, T> void toPath(
      final StateMachine<S, ?, T> fsm,
      final OutputFormat format,
      final Path filePath) {
    toFile(fsm, format, filePath.toFile());
  }

  /**
   * Serialize a given state machine definition to a stream.
   *
   * @since 1.0.0
   * @param fsm
   *          The state machine.
   * @param format
   *          The format to use for serialization.
   * @param stream
   *          The stream to append to.
   *
   * @param <S>
   *          State
   * @param <T>
   *          Event Type
   */
  public static <S, T> void toStream(
      final StateMachine<S, ?, T> fsm,
      final OutputFormat format,
      final OutputStream stream) {
    try (final Writer writer =
        new OutputStreamWriter(stream, Charset.forName("UTF-8"))) {
      toAppendable(fsm, format, writer);
    } catch (final IOException exc) {
      throw new RuntimeException(exc);
    }
  }

  /**
   * Serialize a given state machine definition to a writer.
   *
   * @since 1.0.0
   * @param fsm
   *          The state machine.
   * @param format
   *          The format to use for serialization.
   * @param writer
   *          The writer where to write to.
   *
   * @param <S>
   *          State
   * @param <T>
   *          Event Type
   */
  public static <S, T> void toWriter(
      final StateMachine<S, ?, T> fsm,
      final OutputFormat format,
      final Writer writer) {
    toAppendable(fsm, format, writer);
  }

  /**
   *
   * @since 1.0.0
   * @param fsm
   */
  public static <S> Set<S> getStatesFrom(
      final StateMachine<S, ?, ?> fsm) {

    final Set<S> states = new HashSet<>();

    for (final Transition<S, ?> transition : fsm.getTransitions()) {
      states.add(transition.getFromState());
      states.add(transition.getToState());
    }

    return states;
  }

  /**
   *
   * @since 1.3.0
   */
  public static <S> Set<S> getUnusuedStates() {
    // TODO
    return null;
  }

  /**
   *
   * @since 1.3.0
   */
  public static <T> Set<T> getUnusedEventTypes() {
    // TODO
    return null;
  }

  /**
   *
   * @since 1.3.0
   */
  public static <S, T> Map<S, Map<T, S>> getStatesMap() {
    final Map<S, Map<T, S>> statesMap = new HashMap<>();
    // TODO
    return Collections.unmodifiableMap(statesMap);
  }

  /**
   *
   * @since 1.0.0
   * @param fsm
   */
  public static <T> Set<T> getEventsTypesFrom(
      final StateMachine<?, ?, T> fsm) {

    final Set<T> states = new HashSet<>();

    for (final Transition<?, T> transition : fsm.getTransitions()) {
      states.add(transition.getEventType());
    }

    return states;
  }
}
