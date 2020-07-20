package de.scravy.machina;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.scravy.either.Either;
import de.scravy.pair.Pair;
import de.scravy.pair.Pairs;

/**
 * INTERNAL
 */
final class TransitionMap<C, S, E, T> {

  private final Map<Pair<S, T>, Either<S, GuardedStateList<S, E, C>>> transitions;

  public TransitionMap(final Iterable<Transition<S, T>> transitionList) {
    this.transitions = buildMap(gatherTransitions(transitionList));
  }

  private Map<Pair<S, T>, Pair<List<S>, List<Pair<? extends Guard<E, C>, S>>>>
      gatherTransitions(
          final Iterable<Transition<S, T>> transitionList) {

    final Map<Pair<S, T>, Pair<List<S>, List<Pair<? extends Guard<E, C>, S>>>> transitions = new HashMap<>();

    for (final Transition<S, T> transition : transitionList) {
      final Pair<S, T> key = Pairs.from(
          transition.getFromState(),
          transition.getEventType());

      final Pair<List<S>, List<Pair<? extends Guard<E, C>, S>>> existing;
      if (transitions.containsKey(key)) {
        existing = transitions.get(key);
      } else {
        final List<S> toStateList = new ArrayList<>();
        final List<Pair<? extends Guard<E, C>, S>> guardedStateList = new ArrayList<>();
        existing = Pairs.from(toStateList, guardedStateList);
        transitions.put(key, existing);
      }

      if (transition.getGuard() == null) {
        existing.getFirst().add(transition.getToState());
      } else {
        @SuppressWarnings("unchecked")
        final Guard<E, C> guard = (Guard<E, C>) transition.getGuard();
        existing.getSecond().add(Pairs.from(guard, transition.getToState()));
      }

    }
    return transitions;
  }

  private Map<Pair<S, T>, Either<S, GuardedStateList<S, E, C>>>
      buildMap(
          final Map<Pair<S, T>, Pair<List<S>, List<Pair<? extends Guard<E, C>, S>>>> transitions) {

    final Map<Pair<S, T>, Either<S, GuardedStateList<S, E, C>>> map = new HashMap<>();

    for (final Entry<Pair<S, T>, Pair<List<S>, List<Pair<? extends Guard<E, C>, S>>>> transition : transitions
        .entrySet()) {
      final List<S> nextStates = transition.getValue().getFirst();
      final List<Pair<? extends Guard<E, C>, S>> guardedStates =
          transition.getValue().getSecond();

      final Either<S, GuardedStateList<S, E, C>> entry;
      if (guardedStates.isEmpty()) {
        entry = Either.left(nextStates.get(0));
      } else {
        entry = Either.right(
            new GuardedStateList<>(
                guardedStates,
                nextStates.isEmpty() ? null : nextStates.get(0)));
      }
      map.put(transition.getKey(), entry);
    }

    return Collections.unmodifiableMap(map);
  }

  public S getNextStateFor(
      final S state, final E event, final T eventType, final C context) {

    final Either<S, GuardedStateList<S, E, C>> next =
        this.transitions.get(Pairs.from(state, eventType));

    if (next == null) {
      return null;
    }
    if (next.isLeft()) {
      return next.getLeft();
    }
    return next.getRight().getStateFor(event, context);
  }

  public List<Transition<S, T>> getTransitions() {
    final List<Transition<S, T>> transitions = new ArrayList<>();
    for (final Entry<Pair<S, T>, Either<S, GuardedStateList<S, E, C>>> entry : this.transitions
        .entrySet()) {
      if (entry.getValue().isRight()) {
        final GuardedStateList<S, E, C> states = entry.getValue().getRight();
        for (final GuardedState<S, E, C> guardedState : states) {
          transitions.add(new Transition<S, T>(
              entry.getKey().getFirst(),
              entry.getKey().getSecond(),
              guardedState.getState(),
              guardedState.getGuard()));
        }
        if (states.getDefaultState() != null) {
          transitions.add(new Transition<S, T>(
              entry.getKey().getFirst(),
              entry.getKey().getSecond(),
              states.getDefaultState()));
        }
      } else {
        transitions.add(new Transition<S, T>(
            entry.getKey().getFirst(),
            entry.getKey().getSecond(),
            entry.getValue().getLeft()));
      }
    }
    return transitions;
  }
}
