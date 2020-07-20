package de.scravy.machina;

/**
 * A functional interface to determine whether a transition is eligible or not.
 *
 * @author Julian Fleischer
 * @since 1.0.0
 *
 * @param <E>
 *          Events
 * @param <C>
 *          Context
 */
public interface Guard<E, C> {

  /**
   * Determines whether the guarded transition is eligible or not.
   *
   * @since 1.0.0
   *
   * @param event
   *          The event that was fired.
   * @param context
   *          The context of the state machine.
   * @return Whether the guarded transition is eligible or not.
   */
  boolean evaluate(final E event, final C context);
}
