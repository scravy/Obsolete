package de.scravy.machina;

public interface SimpleStatefulStateMachine<S, E, T> extends
    StatefulStateMachine<S, E, T> {

  void fire(final E event);

}
