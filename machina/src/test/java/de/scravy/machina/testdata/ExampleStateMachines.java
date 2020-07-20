package de.scravy.machina.testdata;

import static de.scravy.machina.example.ReadmeTest.TurnStileEvent.*;
import static de.scravy.machina.example.ReadmeTest.TurnStileState.*;
import static de.scravy.machina.testdata.ThreeEvents.*;
import static de.scravy.machina.testdata.ThreeStates.*;
import de.scravy.machina.SimpleStateMachine;
import de.scravy.machina.StateMachineBuilder;
import de.scravy.machina.StateMachineWithContext;
import de.scravy.machina.example.ReadmeTest.TurnStileEvent;
import de.scravy.machina.example.ReadmeTest.TurnStileState;

public class ExampleStateMachines {

  public static final StateMachineBuilder<ThreeStates, ThreeEvents, ThreeEvents, ThreeProperties, StateMachineWithContext<ThreeStates, ThreeEvents, ThreeEvents, ThreeProperties>> WITH_CONTEXT_BUILDER =
      StateMachineBuilder.createWithContext(
          ThreeStates.class, ThreeEvents.class, ThreeProperties.class)

          .withTransition(S_ONE, E_ONE, S_ONE)
          .withTransition(S_ONE, E_THREE, S_THREE)
          .withTransition(S_ONE, E_TWO, S_TWO)

          .withTransition(S_TWO, E_TWO, S_TWO)
          .withTransition(S_TWO, E_THREE, S_THREE)

          .withTransition(S_THREE, E_THREE, S_THREE);

  public static final StateMachineWithContext<ThreeStates, ThreeEvents, ThreeEvents, ThreeProperties> WITH_CONTEXT =
      WITH_CONTEXT_BUILDER.build();

  public static final StateMachineBuilder<TurnStileState, TurnStileEvent, TurnStileEvent, Void, SimpleStateMachine<TurnStileState, TurnStileEvent, TurnStileEvent>> TURNSTILE_BUILDER =

      StateMachineBuilder
          .create(TurnStileState.class, TurnStileEvent.class)

          .withTransition(LOCKED, PUSH, LOCKED)
          .withTransition(LOCKED, COIN, UNLOCKED)

          .withTransition(UNLOCKED, PUSH, LOCKED)
          .withTransition(UNLOCKED, COIN, UNLOCKED);

  public static SimpleStateMachine<TurnStileState, TurnStileEvent, TurnStileEvent> TURNSTILE =
      TURNSTILE_BUILDER.build();
}
