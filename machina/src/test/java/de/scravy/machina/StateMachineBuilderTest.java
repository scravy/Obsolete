package de.scravy.machina;

import org.junit.Assert;
import org.junit.Test;

public class StateMachineBuilderTest {

  public static enum State {
    A, B, C
  }

  public static class Event implements EventWithType<EventType> {

    @Override
    public EventType getType() {
      return null;
    }
  }

  public static class EventType {

  }

  public static class Context {

  }

  interface TrafficLights extends
      StateMachineWithContext<State, Event, EventType, Context> {

  }

  public static class BuggyState implements
      StateWithEnterHandler<Integer, Double> {

    @Override
    public Double onEnter(final Integer incomingEvent, final Double context) {
      return null;
    }

  }

  public static class BuggyState2 implements
      StateWithExitHandler<Integer, Double> {

    @Override
    public Double onExit(final Integer incomingEvent, final Double context) {
      return null;
    }

  }

  @Test
  public void testDefinitionFromInterface() {

    final TrafficLights fsm =
        StateMachineBuilder.createWithContext(TrafficLights.class).build();

    Assert.assertTrue(fsm instanceof TrafficLights);
    Assert.assertEquals(State.class, fsm.getStateClass());
    Assert.assertEquals(Event.class, fsm.getEventClass());
    Assert.assertEquals(EventType.class, fsm.getEventTypeClass());
    Assert.assertEquals(Context.class, fsm.getContextClass());
  }

  @Test(expected = StateMachineBuilderException.class)
  public void testIllegalDefinition() {
    StateMachineBuilder.createWithContext(
        BuggyState.class, Event.class, EventType.class, Context.class).build();
  }

  @Test(expected = StateMachineBuilderException.class)
  public void testIllegalDefinition2() {
    StateMachineBuilder.createWithContext(
        BuggyState2.class, Event.class, EventType.class, Context.class).build();
  }
}
