package de.scravy.machina;

import lombok.Getter;

/**
 * A RuntimeException thrown by {@link StateMachineBuilder} when trying to
 * instantiate a {@link SimpleStateMachine} from an invalid definition.
 *
 * @author Julian Fleischer
 * @since 1.0.0
 */
public class StateMachineBuilderException extends RuntimeException {

  /**
   * Tge type of this exception.
   * 
   * @author Julian Fleischer
   * @since 1.0.0
   */
  public static enum ErrorType {

    /**
     * <p>
     * Thrown when there is no initial state defined.
     * 
     * <p>
     * If the states are an enum a StateMachineBuilder will simply pick the
     * first defined enum. If the states are neither an enum nor an initial
     * state has been defined using
     * {@link StateMachineBuilder#withInitialState(Object)}, an Exception of
     * this type will be thrown.
     * 
     * <p>
     * Exceptions of this type will report <b>"No initial state defined."</b>
     * 
     * @since 1.0.0
     */
    NO_INITIAL_STATE(
        "No initial state defined."),

    /**
     * <p>
     * Thrown when the states are an enum and no initial states has been defined
     * and the enum does not define any enum constants.
     * 
     * <p>
     * Exceptions of this type will report <b>"Event and EventType differ, but
     * there is no way to derive the type of an event."</b>
     * 
     * @since 1.0.0
     */
    NO_INITIAL_STATE_NO_STATES_AT_ALL(
        "No initial state defined and the State-Enum does not contain any."),

    EVENT_AND_EVENT_TYPES_DIFFER(
        "Event and EventType differ, but there is no way to derive the type of an event."),

    STATE_WITH_ENTER_HANDLER_ILLEGALY_PARAMETERIZED(
        "The state class implements StateWithEnterHandler but is not"
            + " parameterized with the correct event and context types."),

    STATE_WITH_EXIT_HANDLER_ILLEGALY_PARAMETERIZED(
        "The state class implements StateWithExitHandler but is not"
            + " parameterized with the correct event and context types.");

    private final @Getter String message;

    private ErrorType(String message) {
      this.message = message;
    }
  }

  private static final long serialVersionUID = 1L;

  StateMachineBuilderException(final ErrorType type) {
    super(type.getMessage());
  }

}
