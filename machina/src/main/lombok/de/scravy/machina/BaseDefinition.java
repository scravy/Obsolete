package de.scravy.machina;

import lombok.NonNull;
import lombok.Value;

/**
 * INTERNAL
 */
@Value
class BaseDefinition<S, E, T, C> {

  private @NonNull Class<S> states;
  private @NonNull Class<E> events;
  private @NonNull Class<T> eventTypes;
  private Class<C> context;

}
