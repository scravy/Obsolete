package de.scravy.machina;

/**
 * A functional interface to determine the event type of an event.
 *
 * In Machina you can either define Events and Event Types to be the same
 * (simple approach) or you can separate the two and have complex event objects
 * that carry a certain payload. Events can be accessed in guards.
 *
 * To determine the type of an even you can either build your state machine with
 * {@link StateMachineBuilder#withEventTypeClassifier(EventTypeClassifier)} or
 * have your Events implement {@link EventWithType}.
 *
 * @author Julian Fleischer
 *
 * @since 1.0.0
 *
 * @param <E>
 *          Event
 * @param <T>
 *          Event Type
 */
public interface EventTypeClassifier<E, T> {

  /**
   * Determines the Event Type of an Event.
   *
   * @since 1.0.0
   * @param event
   *          The Event object.
   * @return The Event Type of the given event.
   */
  T classify(final E event);
}
