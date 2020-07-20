package de.scravy.machina;

/**
 *
 * @author Julian Fleischer
 * @since 1.0.0
 */
public class EventTypeClassifierException extends StateMachineException {

  private static final long serialVersionUID = 1L;

  EventTypeClassifierException(
      final Object currentState, final Object event,
      final Object context, final Exception cause) {
    super(currentState, event, context, cause);
  }

}
