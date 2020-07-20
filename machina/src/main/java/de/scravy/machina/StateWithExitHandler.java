package de.scravy.machina;

/**
 * An interface to be implemented by States that handle outgoing events.
 *
 * @since 1.0.0
 * @author Julian Fleischer
 *
 * @param <C>
 *          Context
 */
public interface StateWithExitHandler<E, C> {

  /**
   * Executed when a {@link SimpleStateMachine} transits away from this state.
   *
   * @since 1.0.0
   * @param outgoingEvent
   * @param context
   *          A computational context (if the {@link SimpleStateMachine} is not a
   *          {@link StateMachineWithContext} <code>null</code> will be passed)
   * @return The (possibly transformed) context.
   */
  C onExit(final E outgoingEvent, final C context);
}
