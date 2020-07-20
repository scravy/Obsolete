package de.scravy.machina;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import lombok.Getter;
import de.scravy.pair.Pair;

/**
 * INTERNAL
 */
final class GuardedStateList<S, E, C> implements
    Iterable<GuardedState<S, E, C>> {

  private final List<GuardedState<S, E, C>> list;
  private final @Getter S defaultState;

  public GuardedStateList(
      final List<Pair<? extends Guard<E, C>, S>> guardedStates,
      final S defaultState) {
    final List<GuardedState<S, E, C>> list = new ArrayList<>(
        guardedStates.size());

    for (final Pair<? extends Guard<E, C>, S> guardedState : guardedStates) {
      list.add(new GuardedState<S, E, C>(
          guardedState.getFirst(), guardedState.getSecond()));
    }
    this.list = Collections.unmodifiableList(list);
    this.defaultState = defaultState;
  }

  public S getStateFor(final E event, final C context) {
    if (context != null) {
      for (final GuardedState<S, E, C> guardedState : this.list) {
        if (guardedState.getGuard().evaluate(event, context)) {
          return guardedState.getState();
        }
      }
    }
    return this.defaultState;
  }

  @Override
  public Iterator<GuardedState<S, E, C>> iterator() {
    return this.list.iterator();
  }
}
