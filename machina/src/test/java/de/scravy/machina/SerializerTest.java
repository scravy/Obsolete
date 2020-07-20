package de.scravy.machina;

import org.junit.Assert;
import org.junit.Test;

import de.scravy.machina.StateMachines.OutputFormat;
import de.scravy.machina.testdata.ExampleStateMachines;

public class SerializerTest {

  @Test
  public void graphvizWithContext() {
    final String withContext = StateMachines.toString(
        ExampleStateMachines.WITH_CONTEXT,
        OutputFormat.Graphviz);
    Assert.assertTrue(withContext instanceof String);
  }

  @Test
  public void graphvizTurnstile() {
    final String turnstile = StateMachines.toString(
        ExampleStateMachines.TURNSTILE,
        OutputFormat.Graphviz);
    Assert.assertTrue(turnstile instanceof String);
  }

  @Test
  public void jsonWithContext() {
    final String withContext = StateMachines.toString(
        ExampleStateMachines.WITH_CONTEXT,
        OutputFormat.JSON);
    Assert.assertTrue(withContext instanceof String);
  }

  @Test
  public void jsonTurnstile() {
    final String turnstile = StateMachines.toString(
        ExampleStateMachines.TURNSTILE,
        OutputFormat.JSON);
    Assert.assertTrue(turnstile instanceof String);
  }

  @Test
  public void xmlWithContext() {
    final String withContext = StateMachines.toString(
        ExampleStateMachines.WITH_CONTEXT,
        OutputFormat.XML);
    Assert.assertTrue(withContext instanceof String);
  }

  @Test
  public void xmlTurnstile() {
    final String turnstile = StateMachines.toString(
        ExampleStateMachines.TURNSTILE,
        OutputFormat.XML);
    Assert.assertTrue(turnstile instanceof String);
  }
}
