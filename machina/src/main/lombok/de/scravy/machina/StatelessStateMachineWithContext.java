package de.scravy.machina;

import java.util.Collection;

/**
 * INTERNAL
 */
class StatelessStateMachineWithContext<S, E, T, C>
    extends AbstractStateMachine<S, E, T, C>
    implements StateMachineWithContext<S, E, T, C> {

  StatelessStateMachineWithContext(final S initial,
      final Iterable<Transition<S, T>> transitionList, final Class<S> states,
      final Class<E> events, final Class<T> eventTypes,
      final Class<C> contextClass,
      final EventTypeClassifier<E, T> eventTypeClassifier,
      final Collection<TransitionListener<S, E, T, C>> listeners) {
    super(initial, transitionList, states, events, eventTypes, contextClass,
        eventTypeClassifier, listeners);
  }

  @Override
  public RunResult<S, C> run(final Iterable<? extends E> events, final C context) {
    if (context == null) {
      throw new IllegalArgumentException("context");
    }
    return super.run(events, context);
  }

  @Override
  public RunResult<S, C> run(final S currentState, final E event,
      final C context) {
    if (context == null) {
      throw new IllegalArgumentException("context");
    }
    return super.run(currentState, event, context);
  }

  @Override
  public StatefulStateMachineWithContext<S, E, T, C> withState(final C context) {
    return new StateMachineWithStateAndContext<S, E, T, C>(this, context);
  }

  @Override
  public StatefulStateMachineWithContext<S, E, T, C> withState(
      final C context, final S currentState) {
    return new StateMachineWithStateAndContext<S, E, T, C>(
        this, context, currentState);
  }

}
