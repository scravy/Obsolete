package de.scravy.machina;

import java.util.List;

public interface StateMachine<S, E, T> {

  /**
   * Get the transitions defined in this state machine.
   *
   * @since 1.0.0
   * @return A List of Transitions
   */
  List<Transition<S, T>> getTransitions();

  /**
   * Get the class that the events of this state machine inherit from.
   *
   * @since 1.0.0
   * @return The class that the events of this state machine inherit from.
   */
  Class<E> getEventClass();

  /**
   * Get the class that the event types of this state machine inherit from.
   *
   * @since 1.0.0
   * @return The class that the event types of this state machine inherit from.
   */
  Class<T> getEventTypeClass();

  /**
   * The class that the states of this state machine inherit from.
   *
   * @since 1.0.0
   * @return Get the class that the states of this state machine inherit from.
   */
  Class<S> getStateClass();

  S getInitialState();

}
