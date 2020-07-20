package de.scravy.machina;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import de.scravy.cons.Cons;

/**
 * A value class that represents a transition, with an optional guard attached.
 *
 * @since 1.0.0
 * @author Julian Fleischer
 *
 * @param <S>
 *          State
 * @param <T>
 *          Event Type
 */
@Value
@AllArgsConstructor
public final class Transition<S, T> {

  private @NonNull S fromState;
  private T eventType;
  private @NonNull S toState;
  private Guard<?, ?> guard;

  public Transition(final S fromState, final T eventType, final S toState) {
    this(fromState, eventType, toState, null);
  }

  /**
   * A TransitionBuilder that builds transitions.
   *
   * @author Julian Fleischer
   * @since 1.2.0
   *
   * @param <S>
   *          State. This builder is not aware of the Event Type yet.
   */
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class TransitionBuilder<S> {

    final S state;

    /**
     * @since 1.2.0
     * @param eventType
     * @param to
     * @return
     */
    public <T> TransitionBuilder2<S, T> on(final T eventType, final S to) {
      return new TransitionBuilder2<S, T>(
          Cons.singleton(new Transition<S, T>(state, eventType, to)));
    }

    /**
     * @since 1.2.0
     * @param eventType
     * @param to
     * @param guard
     * @return
     */
    @SuppressWarnings("rawtypes")
    public <T> TransitionBuilder2<S, T> on(
        final T eventType, final S to, final String guard) {
      return new TransitionBuilder2<S, T>(
          Cons.singleton(new Transition<S, T>(state, eventType, to,
              new ExpressionGuard(guard))));
    }

  }

  /**
   * A TransitionBuilder that builds transitions.
   *
   * The difference to {@link de.scravy.machina.Transition.TransitionBuilder} is
   * that it does not know the Event Type yet, this transition builder OTOH
   * does.
   *
   * @author Julian Fleischer
   * @since 1.2.0
   *
   * @param <S>
   *          State.
   * @param <T>
   *          Event Type.
   */
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class TransitionBuilder2<S, T> {

    final @Getter(AccessLevel.PACKAGE) Cons<Transition<S, T>> transitions;

    /**
     * @since 1.2.0
     * @param eventType
     * @param to
     * @return
     */
    public TransitionBuilder2<S, T> on(
        final T eventType, final S to) {
      return new TransitionBuilder2<S, T>(
          transitions.cons(new Transition<S, T>(
              transitions.getHead().getFromState(), eventType, to)));
    }

    /**
     * If no transition was eligible, fallback to this transition.
     *
     * This is the same as {@link #catchall(Object)}.
     *
     * @since 1.2.0
     * @param to
     * @return
     */
    public TransitionBuilder2<S, T> fallback(final S to) {
      return catchall(to);
    }

    /**
     * If no transition was eligible, fallback to this transition.
     *
     * @since 1.3.0
     * @param to
     * @return
     */
    public TransitionBuilder2<S, T> catchall(final S to) {
      return new TransitionBuilder2<S, T>(
          transitions.cons(new Transition<S, T>(
              transitions.getHead().getFromState(), null, to)));
    }

    /**
     * @since 1.2.0
     * @param eventType
     * @param to
     * @param guard
     * @return
     */
    @SuppressWarnings("rawtypes")
    public TransitionBuilder2<S, T> on(
        final T eventType, final S to, final String guard) {
      return new TransitionBuilder2<S, T>(
          transitions.cons(new Transition<S, T>(
              transitions.getHead().getFromState(), eventType, to,
              new ExpressionGuard(guard))));
    }
  }

  /**
   * @since 1.2.0
   * @param state
   * @return
   */
  public static <S> TransitionBuilder<S> from(final S state) {
    return new TransitionBuilder<S>(state);
  }
}
