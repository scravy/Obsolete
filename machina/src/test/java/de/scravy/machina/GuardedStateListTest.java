package de.scravy.machina;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.scravy.machina.testdata.ThreeEvents;
import de.scravy.machina.testdata.ThreeProperties;
import de.scravy.machina.testdata.ThreeStates;
import de.scravy.pair.Pair;
import de.scravy.pair.Pairs;

public class GuardedStateListTest {

  final List<Pair<? extends Guard<ThreeEvents, ThreeProperties>, ThreeStates>> dataList = new ArrayList<>();
  {
    this.dataList.add(Pairs.from(
        new ExpressionGuard<ThreeEvents, ThreeProperties>("c.one == 'hello'"),
        ThreeStates.S_ONE));
    this.dataList.add(Pairs.from(
        new ExpressionGuard<ThreeEvents, ThreeProperties>("c.one == 'world'"),
        ThreeStates.S_TWO));
    this.dataList.add(Pairs.from(
        new ExpressionGuard<ThreeEvents, ThreeProperties>("c.two > 2"),
        ThreeStates.S_THREE));
    this.dataList.add(Pairs.from(
        new ExpressionGuard<ThreeEvents, ThreeProperties>("c.buggy != null"),
        ThreeStates.S_TWO));
  };

  final List<Pair<? extends Guard<ThreeEvents, ThreeProperties>, ThreeStates>> dataList2 = new ArrayList<>();
  {
    this.dataList.add(Pairs.from(
        new ExpressionGuard<ThreeEvents, ThreeProperties>("c.one == 'hello'"),
        ThreeStates.S_ONE));
  };

  @Test
  public void getStateFor() {

    final GuardedStateList<ThreeStates, ThreeEvents, ThreeProperties> guardedStateList =
        new GuardedStateList<ThreeStates, ThreeEvents, ThreeProperties>(
            this.dataList, null);

    Assert.assertEquals(
        ThreeStates.S_ONE,
        guardedStateList.getStateFor(
            ThreeEvents.E_ONE,
            ThreeProperties.EXAMPLE_1));
    Assert.assertEquals(
        ThreeStates.S_TWO,
        guardedStateList.getStateFor(
            ThreeEvents.E_ONE,
            ThreeProperties.EXAMPLE_2));
    Assert.assertEquals(
        ThreeStates.S_THREE,
        guardedStateList.getStateFor(
            ThreeEvents.E_ONE,
            ThreeProperties.EXAMPLE_3));
    Assert.assertEquals(
        null,
        guardedStateList.getStateFor(ThreeEvents.E_ONE, null));
  }

  @Test
  public void defaultState() {

    final GuardedStateList<ThreeStates, ThreeEvents, ThreeProperties> guardedStateList =
        new GuardedStateList<ThreeStates, ThreeEvents, ThreeProperties>(
            this.dataList2,
            ThreeStates.S_THREE);

    Assert.assertEquals(
        ThreeStates.S_THREE,
        guardedStateList.getStateFor(
            ThreeEvents.E_ONE, ThreeProperties.EXAMPLE_2));

  }

  @Test(expected = GuardEvaluationException.class)
  public void getStateForException() {

    final GuardedStateList<ThreeStates, ThreeEvents, ThreeProperties> guardedStateList =
        new GuardedStateList<>(this.dataList, null);

    guardedStateList.getStateFor(ThreeEvents.E_ONE, ThreeProperties.EXAMPLE_4);
  }
}
