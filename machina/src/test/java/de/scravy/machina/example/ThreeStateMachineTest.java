package de.scravy.machina.example;

import static de.scravy.machina.testdata.ThreeEvents.*;
import static de.scravy.machina.testdata.ThreeStates.*;

import java.math.BigInteger;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import de.scravy.machina.StateMachineBuilder;
import de.scravy.machina.StateMachineWithContext;
import de.scravy.machina.StatefulStateMachineWithContext;
import de.scravy.machina.testdata.ThreeEvents;
import de.scravy.machina.testdata.ThreeProperties;
import de.scravy.machina.testdata.ThreeStates;
import de.scravy.pair.Pair;

public class ThreeStateMachineTest {

  final StateMachineWithContext<ThreeStates, ThreeEvents, ThreeEvents, ThreeProperties> fsm =
      StateMachineBuilder.createWithContext(
          ThreeStates.class, ThreeEvents.class, ThreeProperties.class)

          .withTransition(S_ONE, E_ONE, S_ONE)
          .withTransition(S_ONE, E_THREE, S_THREE)
          .withTransition(S_ONE, E_TWO, S_TWO)

          .withTransition(S_TWO, E_TWO, S_TWO)
          .withTransition(S_TWO, E_THREE, S_THREE)

          .withTransition(S_THREE, E_THREE, S_THREE)

          .build();

  final ThreeProperties threeProperties =
      ThreeProperties.builder().one("").two(BigInteger.ZERO).three(0.0).build();

  @Test
  public void testSeveralEvents() {
    final Pair<ThreeStates, ThreeProperties> endState =
        this.fsm.run(
            Arrays.asList(E_ONE, E_ONE, E_TWO, E_THREE),
            this.threeProperties);

    Assert.assertEquals(S_THREE, endState.getFirst());
  }

  @Test
  public void testSingleEvent() {
    final Pair<ThreeStates, ThreeProperties> state2 =
        this.fsm.run(S_TWO, E_TWO, this.threeProperties);
    final Pair<ThreeStates, ThreeProperties> state3 =
        this.fsm.run(S_TWO, E_THREE, this.threeProperties);

    Assert.assertEquals(S_TWO, state2.getFirst());
    Assert.assertEquals(S_THREE, state3.getFirst());
  }

  @Test
  public void withState() {
    final StatefulStateMachineWithContext<ThreeStates, ThreeEvents, ThreeEvents, ThreeProperties> sfsm =
        this.fsm.withState(ThreeProperties.EXAMPLE_0);

    sfsm.fire(E_ONE);
    Assert.assertEquals(S_ONE, sfsm.getState());
  }
}
