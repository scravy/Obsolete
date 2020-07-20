package de.scravy.machina;

import java.io.IOException;

/**
 * INTERNAL
 */
interface StateMachineSerializer {

  <S, T> void serialize(
      final StateMachine<S, ?, T> stateMachine, final Appendable appendable)
      throws IOException;
}
