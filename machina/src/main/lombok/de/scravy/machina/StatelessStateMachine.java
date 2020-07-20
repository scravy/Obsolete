package de.scravy.machina;

import java.util.Collection;

/**
 * INTERNAL
 */
class StatelessStateMachine<S, E, T>
    extends AbstractStateMachine<S, E, T, Void>
    implements SimpleStateMachine<S, E, T> {

  StatelessStateMachine(final S initial,
      final Iterable<Transition<S, T>> transitionList,
      final Class<S> states, final Class<E> events, final Class<T> eventTypes,
      final EventTypeClassifier<E, T> eventTypeClassifier,
      final Collection<TransitionListener<S, E, T, Void>> listeners) {
    super(initial, transitionList, states, events, eventTypes, Void.class,
        eventTypeClassifier, listeners);
  }

  @Override
  public SimpleStatefulStateMachine<S, E, T> withState() {
    return new StateMachineWithState<S, E, T>(this);
  }

  @Override
  public SimpleStatefulStateMachine<S, E, T> withState(final S currentState) {
    return new StateMachineWithState<S, E, T>(this, currentState);
  }

}
