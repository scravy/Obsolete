package de.scravy.machina;

import lombok.Getter;

/**
 *
 * @since 1.0.0
 * @author Julian Fleischer
 */
public class StateMachineException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final @Getter Object currentState;
  private final @Getter Object event;
  private final @Getter Object context;

  StateMachineException(
      final Object currentState,
      final Object event,
      final Object context,
      final Exception cause) {

    super(String.format("%s %s\n", currentState, event), cause);

    this.currentState = currentState;
    this.event = event;
    this.context = context;
  }

}
