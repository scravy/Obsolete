package de.scravy.machina.testdata;

import de.scravy.machina.StateWithExitHandler;

public class ExitState extends SomeState implements
    StateWithExitHandler<SomeEvent, SomeContext> {

  @Override
  public SomeContext onExit(final SomeEvent outgoingEvent, final SomeContext context) {
    return context;
  }

}
