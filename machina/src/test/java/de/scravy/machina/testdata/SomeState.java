package de.scravy.machina.testdata;

public abstract class SomeState {

  public static final SomeState S1 = new EnterState();
  public static final SomeState S2 = new EnterState();
  public static final SomeState S3 = new BuggyEnterState();
  public static final SomeState S4 = new BuggyExitState();
}
