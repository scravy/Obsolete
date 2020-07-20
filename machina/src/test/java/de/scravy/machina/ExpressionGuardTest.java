package de.scravy.machina;

import java.util.HashSet;

import lombok.Value;

import org.junit.Assert;
import org.junit.Test;

import de.scravy.machina.testdata.ThreeEvents;

public class ExpressionGuardTest {

  final Guard<ThreeEvents, Context> c1 =
      new ExpressionGuard<>("c.value == 'XYZ'");
  final Guard<ThreeEvents, Context> c2 =
      new ExpressionGuard<>("c.value == 'XYZ'");
  final Guard<ThreeEvents, Context> c3 =
      new ExpressionGuard<>("c.value != 'XYZ'");
  final Guard<ThreeEvents, Context> c4 =
      new ExpressionGuard<>("7 + 3");
  
  @Value
  public static final class Context {
    private final String value;
    private final int intValue;
  }

  public static final class BuggyContext {
    public String getValue() {
      throw new RuntimeException();
    }
  }

  @Test
  public void evaluate() {
    final Guard<ThreeEvents, Context> condition =
        new ExpressionGuard<>("c.value == 'XYZ'");
    final Context context = new Context("XYZ", 4711);
    final Object result = condition.evaluate(ThreeEvents.E_ONE, context);
    Assert.assertTrue(result instanceof Boolean);
    Assert.assertTrue((Boolean) result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullExpression() {
    new ExpressionGuard<>(null);
  }

  @Test(expected = InvalidExpressionException.class)
  public void invalidExpression() {
    new ExpressionGuard<>("c.value ~~= 'XYZ'");
  }

  @Test
  public void illegalTypes() {
    final Guard<ThreeEvents, Context> condition =
        new ExpressionGuard<>("c.intValue == 'XYZ'");
    final Context context = new Context("XYZ", 4711);
    final Object result = condition.evaluate(ThreeEvents.E_ONE, context);
    Assert.assertTrue(result instanceof Boolean);
    Assert.assertFalse((Boolean) result);
  }

  @Test(expected = GuardEvaluationException.class)
  public void buggyContext() {
    final Guard<ThreeEvents, BuggyContext> condition =
        new ExpressionGuard<>("c.intValue == 'XYZ'");
    final BuggyContext context = new BuggyContext();
    condition.evaluate(ThreeEvents.E_ONE, context);
  }
  
  @Test
  public void notBooleanExpression() {
    Assert.assertFalse(c4.evaluate(ThreeEvents.E_ONE, new Context("", 0)));
  }

  @Test
  public void equals() {
    Assert.assertEquals(c1, c1);
    Assert.assertEquals(c1, c2);
  }

  @Test
  public void notEquals() {
    Assert.assertNotEquals(c1, c3);
    Assert.assertNotEquals(c3, c1);
    Assert.assertFalse(c1.equals(null));
  }

  @Test
  public void hashCodeTest() {
    final HashSet<Guard<ThreeEvents, Context>> set = new HashSet<>();

    set.add(c1);
    Assert.assertTrue(set.contains(c1));
    Assert.assertTrue(set.contains(c2));
    Assert.assertFalse(set.contains(c3));
  }
}
