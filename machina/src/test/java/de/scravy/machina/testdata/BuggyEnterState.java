package de.scravy.machina.testdata;

import de.scravy.machina.StateWithEnterHandler;

public class BuggyEnterState extends SomeState implements
    StateWithEnterHandler<SomeEvent, SomeContext> {

  @Override
  public SomeContext onEnter(final SomeEvent incomingEvent, final SomeContext context) {
    throw new NullPointerException();
  }

}
