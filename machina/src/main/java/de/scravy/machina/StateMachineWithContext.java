package de.scravy.machina;

/**
 * A {@link SimpleStateMachine} with a computational context.
 *
 * @since 1.0.0
 * @author Julian Fleischer
 *
 * @param <C>
 *          Context
 * @param <S>
 *          State
 * @param <E>
 *          Event
 * @param <T>
 *          Event Type
 */
public interface StateMachineWithContext<S, E, T, C> extends
    StateMachine<S, E, T> {

  /**
   * Executes the state machine with a series of Events and a Context.
   *
   * The state machine will start in the initial state.
   *
   * @since 1.0.0
   * @param events
   *          The series of Events as an Iterable.
   * @return A pair of the state the state machine ends up in and the (possibly)
   *         altered context.
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
  RunResult<S, C> run(final Iterable<? extends E> events, final C context)
      throws StateMachineException;

  /**
   * Executes the state machine for a single event, starting in the specified
   * state.
   *
   * @since 1.0.0
   * @param current
   *          The state to start in.
   * @param event
   *          The event to execute the state machine for.
   * @param context
   *          A context object.
   * @return A pair of the state the state machine ends up in and the (possibly)
   *         altered context.
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
  RunResult<S, C> run(final S current, final E event, final C context)
      throws StateMachineException;

  /**
   * Get the class that implements the context of this state machine.
   *
   * @since 1.0.0
   * @return The class that implements the context of this state machine.
   */
  Class<C> getContextClass();

  StatefulStateMachineWithContext<S, E, T, C> withState(final C context);

  StatefulStateMachineWithContext<S, E, T, C> withState(
      final C context, final S current);
}
