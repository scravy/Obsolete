package de.scravy.machina;

/**
 * A state machine.
 *
 * @since 1.0.0
 * @author Julian Fleischer
 *
 * @param <S>
 *          State
 * @param <E>
 *          Event
 * @param <T>
 *          Event Type
 */
public interface SimpleStateMachine<S, E, T> extends StateMachine<S, E, T> {

  /**
   * Executes the state machine with a series of Events.
   *
   * The state machine will start in the initial state.
   *
   * @since 1.0.0
   * @param events
   *          The series of events as an Iterable.
   * @return The state the state machine ends up in.
   * @throws DeadEndException
   *           If the state machine is stuck due to no transition being
   *           eligible.
   * @throws EventTypeClassifierException
   *           If the {@link EventTypeClassifier} associated with this state
   *           machine fails during execution. The original exception will be
   *           the cause for this exception.
   * @throws StateHandlerException
   *           If one of the encompassed states implements
   *           {@link StateWithEnterHandler} or {@link StateWithExitHandler} and
   *           throws an error during execution of its handler method.
   * @throws StateMachineException
   *           If an internal error happens, for example a
   *           {@link GuardEvaluationException}.
   */
  S run(final Iterable<? extends E> events) throws StateMachineException;

  /**
   * Executes the state machine for a single event, starting in the specified
   * state.
   *
   * @since 1.0.0
   * @param current
   *          The state to start in.
   * @param event
   *          The event to execute the state machine for.
   * @return The state the state machine end up in.
   * @throws DeadEndException
   *           If the state machine is stuck due to no transition being
   *           eligible.
   * @throws EventTypeClassifierException
   *           If the {@link EventTypeClassifier} associated with this state
   *           machine fails during execution. The original exception will be
   *           the cause for this exception.
   * @throws StateHandlerException
   *           If one of the encompassed states implements
   *           {@link StateWithEnterHandler} or {@link StateWithExitHandler} and
   *           throws an error during execution of its handler method.
   * @throws StateMachineException
   *           If an internal error happens, for example a
   *           {@link GuardEvaluationException}.
   */
  S run(final S current, final E event) throws StateMachineException;

  SimpleStatefulStateMachine<S, E, T> withState();

  SimpleStatefulStateMachine<S, E, T> withState(final S current);
}
