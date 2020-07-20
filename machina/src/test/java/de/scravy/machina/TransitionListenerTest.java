package de.scravy.machina;

import static de.scravy.machina.testdata.ThreeEvents.*;
import static de.scravy.machina.testdata.ThreeStates.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.scravy.machina.testdata.ThreeEvents;
import de.scravy.machina.testdata.ThreeProperties;
import de.scravy.machina.testdata.ThreeStates;

public class TransitionListenerTest {

  public interface ThreeStateMachine
      extends
      StateMachineWithContext<ThreeStates, ThreeEvents, ThreeEvents, ThreeProperties> {

  }

  @Test
  public void test() {
    final List<ThreeEvents> events = new LinkedList<>();
    final ThreeStateMachine fsm = StateMachineBuilder
        .createWithContext(ThreeStateMachine.class)

        .withTransition(S_ONE, E_ONE, S_ONE)
        .withTransition(S_ONE, E_THREE, S_THREE)
        .withTransition(S_ONE, E_TWO, S_TWO)

        .withTransition(S_TWO, E_TWO, S_TWO)
        .withTransition(S_TWO, E_THREE, S_THREE)

        .withTransition(S_THREE, E_THREE, S_THREE)

        .withListener(
            new TransitionListener<ThreeStates, ThreeEvents, ThreeEvents, ThreeProperties>() {

              @Override
              public void onTransition(
                  final ThreeStates from, final ThreeEvents eventType,
                  final ThreeStates to,
                  final ThreeEvents event, final ThreeProperties context) {
                events.add(eventType);
              }
            })

        .build();

    fsm.run(S_ONE, E_TWO, ThreeProperties.EXAMPLE_0);

    Assert.assertTrue(events.get(0) == E_TWO);
  }
}
