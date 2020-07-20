package de.scravy.machina;

/**
 *
 * @since 1.0.0
 * @author Julian Fleischer
 */
public class StateHandlerException extends StateMachineException {

  private static final long serialVersionUID = 1L;

  StateHandlerException(
      final Object currentState,
      final Object event,
      final Object context,
      final Exception cause) {
    super(currentState, event, context, cause);
  }
}
