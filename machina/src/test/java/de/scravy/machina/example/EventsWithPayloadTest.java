package de.scravy.machina.example;

import java.util.Arrays;

import lombok.Getter;
import lombok.Value;
import lombok.experimental.Wither;

import org.junit.Assert;
import org.junit.Test;

import de.scravy.machina.EventWithType;
import de.scravy.machina.StateMachineBuilder;
import de.scravy.machina.StateMachineWithContext;
import de.scravy.machina.StateWithEnterHandler;
import de.scravy.pair.Pair;

public class EventsWithPayloadTest {

  public static enum EventType {
    COIN,
    PUSH;
  }

  public static class Event implements EventWithType<EventType> {

    private final @Getter EventType type;

    public Event(final EventType type) {
      this.type = type;
    }

  }

  @Value
  public static class CoinSafe {
    private final @Wither int amount;
  }

  public static enum State implements StateWithEnterHandler<Event, CoinSafe> {
    LOCKED {
      @Override
      public CoinSafe onEnter(final Event incomingEvent, final CoinSafe context) {
        if (incomingEvent instanceof Coin) {
          return context.withAmount(
              context.getAmount() + ((Coin) incomingEvent).getAmount());
        }
        return context;
      }
    },
    UNLOCKED {
      @Override
      public CoinSafe onEnter(final Event incomingEvent, final CoinSafe context) {
        if (incomingEvent instanceof Coin) {
          return context.withAmount(
              context.getAmount() + ((Coin) incomingEvent).getAmount() - 50);
        }
        return context;
      }
    };
  }

  public static class Coin extends Event {
    final @Getter int amount;

    public static final Coin FIFTY_CENTS = new Coin(50);
    public static final Coin TWENTY_CENTS = new Coin(20);
    public static final Coin TEN_CENTS = new Coin(10);

    public Coin(final int amount) {
      super(EventType.COIN);
      this.amount = amount;
    }
  }

  public static class Push extends Event {

    public Push() {
      super(EventType.PUSH);
    }

  }

  @Test
  public void test() {
    final StateMachineWithContext<State, Event, EventType, CoinSafe> turnstile =
        StateMachineBuilder
            .createWithContext(
                State.class, Event.class, EventType.class, CoinSafe.class)
            .withTransition(State.LOCKED, EventType.COIN, State.UNLOCKED,
                "c.amount + e.amount >= 50")
            .withTransition(State.LOCKED, EventType.COIN, State.LOCKED)
            .withTransition(State.UNLOCKED, EventType.PUSH, State.LOCKED)
            .withTransition(State.UNLOCKED, EventType.COIN, State.UNLOCKED)
            .build();

    final Pair<State, CoinSafe> result = turnstile.run(
        Arrays.asList(
            Coin.TWENTY_CENTS,
            Coin.TWENTY_CENTS,
            Coin.TWENTY_CENTS),
        new CoinSafe(0));
    Assert.assertEquals(State.UNLOCKED, result.getFirst());
    Assert.assertEquals(new CoinSafe(10), result.getSecond());
  }
}
