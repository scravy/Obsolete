package de.scravy.machina;

/**
 *
 * @since 1.0.0
 * @author Julian Fleischer
 */
public class DeadEndException extends StateMachineException {

  private static final long serialVersionUID = 1L;

  DeadEndException(
      final Object currentState,
      final Object event,
      final Object context) {
    super(currentState, event, context, null);
  }
}
