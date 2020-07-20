package de.scravy.machina;

public interface StatefulStateMachineWithContext<S, E, T, C>
    extends StatefulStateMachine<S, E, T> {

  void fire(final E event);

  C getContext();
}
