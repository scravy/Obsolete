package de.scravy.machina.testdata;

import java.math.BigInteger;

import de.scravy.machina.StateWithEnterHandler;

public enum ThreeFunctionalStates implements
    StateWithEnterHandler<ThreeEvents, ThreeProperties> {

  S_ONE,
  S_TWO,
  S_THREE;

  @Override
  public ThreeProperties onEnter(
      final ThreeEvents event,
      final ThreeProperties context) {
    return context.withTwo(context.getTwo().add(BigInteger.ONE));
  }

}
