package de.scravy.machina;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * INTERNAL
 *
 * @since 1.1.0
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AbstractDelegatingStateMachine<S, E, T, D extends StateMachine<S, E, T>>
    implements StateMachine<S, E, T> {

  protected final D fsm;

  @Override
  public List<Transition<S, T>> getTransitions() {
    return this.fsm.getTransitions();
  }

  @Override
  public Class<E> getEventClass() {
    return this.fsm.getEventClass();
  }

  @Override
  public Class<T> getEventTypeClass() {
    return this.fsm.getEventTypeClass();
  }

  @Override
  public Class<S> getStateClass() {
    return this.fsm.getStateClass();
  }

  @Override
  public S getInitialState() {
    return this.fsm.getInitialState();
  }

}
