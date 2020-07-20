package de.scravy.machina.example;

import static de.scravy.machina.testdata.ThreeEvents.*;
import static de.scravy.machina.testdata.ThreeStates.*;

import java.math.BigInteger;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import de.scravy.machina.DeadEndException;
import de.scravy.machina.StateMachineBuilder;
import de.scravy.machina.StateMachineWithContext;
import de.scravy.machina.testdata.ThreeEvents;
import de.scravy.machina.testdata.ThreeProperties;
import de.scravy.machina.testdata.ThreeStates;

public class ThreeStateMachineWithContextAndGuardsTest {

  final StateMachineWithContext<ThreeStates, ThreeEvents, ThreeEvents, ThreeProperties> fsm =
      StateMachineBuilder.createWithContext(
          ThreeStates.class, ThreeEvents.class, ThreeProperties.class)
          .withTransition(S_ONE, E_ONE, S_ONE)
          .withTransition(S_ONE, E_TWO, S_THREE, "c.three >= 3")
          .withTransition(S_ONE, E_TWO, S_TWO)
          .build();

  @Test
  public void testSeveralEventsFirstGuard() {
    final ThreeStates endState = this.fsm.run(
        Arrays.asList(E_ONE, E_ONE, E_TWO),
        ThreeProperties.builder()
            .one("")
            .two(BigInteger.ONE)
            .three(Math.PI)
            .build()
        ).getFirst();

    Assert.assertEquals(S_THREE, endState);
  }

  @Test
  public void testSeveralEventsDefaultGuard() {
    final ThreeStates endState = this.fsm.run(
        Arrays.asList(E_ONE, E_ONE, E_TWO),
        ThreeProperties.builder()
            .one("")
            .two(BigInteger.ONE)
            .three(Math.E)
            .build()
        ).getFirst();

    Assert.assertEquals(S_TWO, endState);
  }

  @Test(expected = DeadEndException.class)
  public void testDeadEnd() {
    this.fsm.run(S_TWO, E_ONE, ThreeProperties.EXAMPLE_0);
  }
}
