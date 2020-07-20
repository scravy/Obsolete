package de.scravy.machina;

import java.util.Arrays;

import org.junit.Test;

import de.scravy.machina.testdata.ExampleStateMachines;
import de.scravy.machina.testdata.SomeContext;
import de.scravy.machina.testdata.SomeEvent;
import de.scravy.machina.testdata.SomeState;
import de.scravy.machina.testdata.ThreeEvents;
import de.scravy.machina.testdata.ThreeProperties;
import de.scravy.machina.testdata.ThreeStates;

public class StateMachineTest {

  public static final StateMachineWithContext<ThreeStates, ThreeEvents, ThreeEvents, ThreeProperties> fsm1 =
      ExampleStateMachines.WITH_CONTEXT_BUILDER.withEventTypeClassifier(
          new EventTypeClassifier<ThreeEvents, ThreeEvents>() {

            @Override
            public ThreeEvents classify(final ThreeEvents event) {
              throw new RuntimeException();
            }
          }).build();

  public static final StateMachineWithContext<ThreeStates, ThreeEvents, ThreeEvents, ThreeProperties> fsm2 =
      ExampleStateMachines.WITH_CONTEXT_BUILDER
          .withListener(
              new TransitionListener<ThreeStates, ThreeEvents, ThreeEvents, ThreeProperties>() {

                @Override
                public void onTransition(final ThreeStates from,
                    final ThreeEvents eventType,
                    final ThreeStates to, final ThreeEvents event,
                    final ThreeProperties context) {
                  throw new RuntimeException();
                }
              }).build();

  public static final StateMachineWithContext<SomeState, SomeEvent, String, SomeContext> fsm3 =
      StateMachineBuilder
          .createWithContext(SomeState.class, SomeEvent.class, String.class,
              SomeContext.class)
          .withTransition(SomeState.S2, "wayne", SomeState.S1,
              new Guard<SomeEvent, SomeContext>() {
                @Override
                public boolean evaluate(final SomeEvent event,
                    final SomeContext context) {
                  throw new NullPointerException();
                }
              })
          .withInitialState(SomeState.S1)
          .withTransition(SomeState.S1, "woop", SomeState.S2)
          .withTransition(SomeState.S2, "woop", SomeState.S3)
          .withTransition(SomeState.S3, "woop", SomeState.S4)
          .withTransition(SomeState.S4, "woop", SomeState.S1)
          .build();

  @Test(expected = EventTypeClassifierException.class)
  public void testBuggyEventTypeClassifier() {
    fsm1.run(ThreeStates.S_ONE, ThreeEvents.E_THREE, ThreeProperties.EXAMPLE_0);
  }

  @Test
  public void testBuggyListener() {
    fsm2.run(
        Arrays.asList(ThreeEvents.E_ONE, ThreeEvents.E_ONE),
        ThreeProperties.EXAMPLE_0);
  }

  @Test(expected = StateHandlerException.class)
  public void testBuggyEnterHandler() {
    fsm3.run(SomeState.S2, new SomeEvent("woop"), new SomeContext());
  }

  @Test(expected = StateHandlerException.class)
  public void testBuggyExitHandler() {
    fsm3.run(SomeState.S4, new SomeEvent("woop"), new SomeContext());
  }

  @Test(expected = StateMachineException.class)
  public void testBuggyGuard() {
    fsm3.run(SomeState.S2, new SomeEvent("wayne"), new SomeContext());
  }
}
