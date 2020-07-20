package de.scravy.machina;

/**
 * An interface to be implemented by Events that are distinct from their types.
 *
 * In Machina you can either define Events and Event Types to be the same
 * (simple approach) or you can separate the two and have complex event objects
 * that carry a certain payload. Events can be accessed in guards.
 *
 * To determine the type of an even you can either build your state machine with
 * {@link StateMachineBuilder#withEventTypeClassifier(EventTypeClassifier)} or
 * have your Events implement {@link EventWithType}.
 *
 * For example you could implement events in different classes that implement a
 * common super type and have them return <code>getClass</code> in
 * {@link #getType()} to use their classes as actual event types.
 *
 * <h2>Example</h2>
 *
 * <pre>
 * public class EventsWithPayload {
 * 
 *   public static enum EventType {
 *     COIN,
 *     PUSH;
 *   }
 * 
 *   public static class Event implements EventWithType&lt;EventType&gt; {
 * 
 *     private final EventType type;
 * 
 *     public Event(final EventType type) {
 *       this.type = type;
 *     }
 * 
 *     &#64;Override
 *     public EventType getType() {
 *       return this.type;
 *     }
 *   }
 * 
 *   public static class Coin extends Event {
 *     final &#64;Getter int amount;
 * 
 *     public static final Coin FIFTY_CENTS = new Coin(50);
 *     public static final Coin TWENTY_CENTS = new Coin(20);
 *     public static final Coin TEN_CENTS = new Coin(10);
 * 
 *     public Coin(final int amount) {
 *       super(EventType.COIN);
 *       this.amount = amount;
 *     }
 *   }
 * 
 *  public static class Push extends Event {
 * 
 *     public Push() {
 *       super(EventType.PUSH);
 *     }
 *   }
 * 
 *   &#64;Value
 *   public static class CoinSafe {
 *     private final &#64;Wither int amount;
 *   }
 * 
 *   public static enum State implements StateWithEnterHandler&lt;Event, CoinSafe&gt; {
 *     LOCKED {
 *       &#64;Override
 *       public CoinSafe onEnter(final Event incomingEvent, final CoinSafe context) {
 *         if (incomingEvent instanceof Coin) {
 *           return context.withAmount(
 *               context.getAmount() + ((Coin) incomingEvent).getAmount());
 *         }
 *         return context;
 *       }
 *     },
 *     UNLOCKED {
 *       &#64;Override
 *       public CoinSafe onEnter(final Event incomingEvent, final CoinSafe context) {
 *         if (incomingEvent instanceof Coin) {
 *           return context.withAmount(
 *               context.getAmount() + ((Coin) incomingEvent).getAmount() - 50);
 *         }
 *         return context;
 *       }
 *     };
 *   }
 * 
 *   public static void main(String... args) {
 *     final StateMachineWithContext&lt;State, Event, EventType, CoinSafe&gt; turnstile =
 *         StateMachineBuilder
 *             .createWithContext(
 *                 State.class, Event.class, EventType.class, CoinSafe.class)
 *             .transition(State.LOCKED, EventType.COIN, State.UNLOCKED,
 *                 "c.amount + e.amount &gt;= 50")
 *             .transition(State.LOCKED, EventType.COIN, State.LOCKED)
 *             .transition(State.UNLOCKED, EventType.PUSH, State.LOCKED)
 *             .transition(State.UNLOCKED, EventType.COIN, State.UNLOCKED)
 *             .build();
 * 
 *     final Pair&lt;State, CoinSafe&gt; result = turnstile.run(
 *         Arrays.asList(
 *             Coin.TWENTY_CENTS,
 *             Coin.TWENTY_CENTS,
 *             Coin.TWENTY_CENTS),
 *         new CoinSafe(0));
 *     System.out.println(
 *         Pairs.from(State.UNLOCKED, new CoinSafe(10)), result);
 *   }
 * </pre>
 *
 * @author Julian Fleischer
 *
 * @since 1.0.0
 *
 * @param <T>
 *          Event Type
 */
public interface EventWithType<T> {

  /**
   * Get the type of this Event.
   *
   * @since 1.0.0
   * @return The type of this event.
   */
  T getType();
}
