package de.scravy.machina;

/**
 * A transition listener is invoked after a state is exited and before a new
 * state is entered.
 *
 * It can be attached via
 * {@link StateMachineBuilder#withListener(StateMachineListener)}.
 *
 * @author Julian Fleischer
 * @since 1.0.0
 *
 * @param <S>
 *          State
 * @param <E>
 *          Event
 * @param <T>
 *          Event Type
 * @param <C>
 *          Context
 */
public interface TransitionListener<S, E, T, C>
    extends StateMachineListener<S, E, T, C> {

  /**
   * @since 1.0.0
   * @param from
   *          The state which was just exit.
   * @param eventType
   *          The event type that issued this transition.
   * @param to
   *          The state the state machine is about to enter.
   * @param event
   *          The event that caused the traversal of this transition.
   * @param context
   *          The current context.
   */
  void onTransition(
      final S from,
      final T eventType,
      final S to,
      final E event,
      final C context);
}
