package de.scravy.machina;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.scravy.machina.StateMachines.OutputFormat;

/**
 * INTERNAL
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
abstract class AbstractStateMachine<S, E, T, C>
    implements StateMachine<S, E, T> {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(StateMachine.class);

  private final S initial;
  private final @Getter Class<C> contextClass;
  private final @Getter Class<S> stateClass;
  private final @Getter Class<E> eventClass;
  private final @Getter Class<T> eventTypeClass;
  private final TransitionMap<C, S, E, T> transitions;
  private final EventTypeClassifier<E, T> eventTypeClassifier;
  private final List<TransitionListener<S, E, T, C>> transitionListeners;

  AbstractStateMachine(
      final S initial,
      final Iterable<Transition<S, T>> transitionList,
      final Class<S> states,
      final Class<E> events,
      final Class<T> eventTypes,
      final Class<C> contextClass,
      final EventTypeClassifier<E, T> eventTypeClassifier,
      final Collection<TransitionListener<S, E, T, C>> listeners) {
    this(initial, contextClass, states, events, eventTypes,
        new TransitionMap<C, S, E, T>(transitionList), eventTypeClassifier,
        new ArrayList<>(listeners));
  }

  public RunResult<S, C> run(final Iterable<? extends E> events, final C context) {
    RunResult<S, C> currentState = new RunResult<>(this.initial, context);
    for (final E ev : events) {
      currentState = run(currentState.getFirst(), ev, currentState.getSecond());
    }
    return currentState;
  }

  @SuppressWarnings("unchecked")
  S getNextState(
      S currentState, final E event, final T eventType, final C context) {
    final Set<S> testedStates = new HashSet<>();
    S next = null;

    for (;;) {
      next = transitions.getNextStateFor(
          currentState, event, eventType, context);

      if (next == null) {
        next = transitions.getNextStateFor(
            currentState, event, null, context);
      }

      if (next == null) {
        testedStates.add(currentState);
        if (currentState instanceof StateWithParent) {
          currentState = ((StateWithParent<S>) currentState).getParent();
          if (testedStates.contains(currentState)) {
            throw new RuntimeException("Hit an endless loop");
          }
          continue;
        }
      }
      break;
    }

    return next;
  }

  @SuppressWarnings("unchecked")
  public RunResult<S, C> run(final S currentState, final E event,
      final C context) {

    final T eventType;
    try {
      eventType = this.eventTypeClassifier.classify(event);
    } catch (final Exception exc) {
      throw new EventTypeClassifierException(currentState, event, context, exc);
    }

    final S next;
    try {
      next = getNextState(currentState, event, eventType, context);
    } catch (final Exception exc) {
      throw new StateMachineException(currentState, event, context, exc);
    }

    if (next == null) {
      throw new DeadEndException(currentState, event, context);
    }

    final C contextAfterExit;
    if (currentState instanceof StateWithExitHandler) {
      try {
        contextAfterExit =
            ((StateWithExitHandler<E, C>) currentState).onExit(event, context);
      } catch (final Exception exc) {
        throw new StateHandlerException(currentState, event, context, exc);
      }
    } else {
      contextAfterExit = context;
    }

    for (final TransitionListener<S, E, T, C> listener : this.transitionListeners) {
      try {
        listener.onTransition(currentState, eventType, next, event,
            contextAfterExit);
      } catch (final Exception exc) {
        LOGGER.warn("A TransitionListener throwed an exception.", exc);
      }
    }

    if (next instanceof StateWithEnterHandler) {
      try {
        final C contextAfterEnter =
            ((StateWithEnterHandler<E, C>) next).onEnter(
                event, contextAfterExit);
        return new RunResult<>(next, contextAfterEnter);
      } catch (final Exception exc) {
        throw new StateHandlerException(
            currentState, event, contextAfterExit, exc);
      }
    }
    return new RunResult<S, C>(next, context);
  }

  public S run(final Iterable<? extends E> events) {
    return run(events, null).getFirst();
  }

  public S run(final S current, final E event) {
    return run(current, event, null).getFirst();
  }

  @Override
  public List<Transition<S, T>> getTransitions() {
    return this.transitions.getTransitions();
  }

  @Override
  public S getInitialState() {
    return this.initial;
  }

  @Override
  public String toString() {
    return StateMachines.toString(this, OutputFormat.Graphviz);
  }

}
