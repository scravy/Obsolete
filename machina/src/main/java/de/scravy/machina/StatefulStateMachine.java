package de.scravy.machina;

public interface StatefulStateMachine<S, E, T> extends StateMachine<S, E, T> {

  S getState();
}
