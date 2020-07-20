package de.scravy.machina;

/**
 * An interface to be implemented by States that handle incoming events.
 *
 * @since 1.0.0
 * @author Julian Fleischer
 *
 * @param <C>
 *          Context
 */
public interface StateWithEnterHandler<E, C> {

  /**
   * Executed when a {@link SimpleStateMachine} transits into this state.
   *
   * @since 1.0.0
   * @param incomingEvent
   * @param context
   *          A computational context (if the {@link SimpleStateMachine} is not a
   *          {@link StateMachineWithContext} <code>null</code> will be passed)
   * @return The (possibly transformed) context.
   */
  C onEnter(final E incomingEvent, final C context);
}
