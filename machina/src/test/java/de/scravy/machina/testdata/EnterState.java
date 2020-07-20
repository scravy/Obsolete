package de.scravy.machina.testdata;

import de.scravy.machina.StateWithEnterHandler;

public class EnterState extends SomeState implements
    StateWithEnterHandler<SomeEvent, SomeContext> {

  @Override
  public SomeContext onEnter(final SomeEvent incomingEvent, final SomeContext context) {
    return context;
  }

}
