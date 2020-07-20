package de.scravy.machina;

import lombok.Value;

@Value
final class GuardedState<S, E, C> {

  private final Guard<E, C> guard;
  private final S state;

}
