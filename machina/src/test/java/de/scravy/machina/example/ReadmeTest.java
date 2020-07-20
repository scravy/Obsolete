package de.scravy.machina.example;

import static de.scravy.machina.example.ReadmeTest.TurnStileEvent.*;
import static de.scravy.machina.example.ReadmeTest.TurnStileState.*;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import de.scravy.machina.SimpleStateMachine;
import de.scravy.machina.SimpleStatefulStateMachine;
import de.scravy.machina.StateMachineBuilder;

public class ReadmeTest {

  public static enum TurnStileState {
    LOCKED, UNLOCKED
  }

  public static enum TurnStileEvent {
    PUSH, COIN
  }

  final SimpleStateMachine<TurnStileState, TurnStileEvent, TurnStileEvent> turnstile =
      StateMachineBuilder
          .create(TurnStileState.class, TurnStileEvent.class)
          .withTransition(LOCKED, PUSH, LOCKED)
          .withTransition(LOCKED, COIN, UNLOCKED)
          .withTransition(UNLOCKED, PUSH, LOCKED)
          .withTransition(UNLOCKED, COIN, UNLOCKED)
          .build();

  @Test
  public void test() {
    TurnStileState state = this.turnstile.run(Arrays.asList(PUSH)); // state is
    // LOCKED
    Assert.assertEquals(LOCKED, state);
    state = this.turnstile.run(state, COIN); // state is UNLOCKED
    Assert.assertEquals(UNLOCKED, state);
    state = this.turnstile.run(state, PUSH); // state is LOCKED
    Assert.assertEquals(LOCKED, state);

    // in one go
    state = this.turnstile.run(Arrays.asList(PUSH, COIN, PUSH)); // state is
                                                                 // LOCKED
  }

  @Test
  public void withState() {

    final SimpleStatefulStateMachine<TurnStileState, TurnStileEvent, TurnStileEvent> sfsm =
        this.turnstile.withState();

    Assert.assertEquals(LOCKED, sfsm.getState());
    sfsm.fire(PUSH);
    Assert.assertEquals(LOCKED, sfsm.getState());
    sfsm.fire(COIN);
    Assert.assertEquals(UNLOCKED, sfsm.getState());
    sfsm.fire(PUSH);
    Assert.assertEquals(LOCKED, sfsm.getState());
  }
}
