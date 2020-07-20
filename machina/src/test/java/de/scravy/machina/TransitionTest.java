package de.scravy.machina;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

public class TransitionTest {

  final Transition<String, Character> t1 = new Transition<>("Start", 'a', "End");
  final Transition<String, Character> t2 = new Transition<>("Start", 'a', "End");
  final Transition<String, Character> t3 = new Transition<>("Start", 'b', "End");

  @Test
  public void equals() {
    Assert.assertEquals(t1, t1);
    Assert.assertEquals(t1, t2);
  }

  @Test
  public void notEquals() {
    Assert.assertNotEquals(t1, t3);
    Assert.assertNotEquals(t3, t1);
    Assert.assertFalse(t1.equals(null));
  }

  @Test
  public void hashCodeTest() {
    final HashSet<Transition<String, Character>> set = new HashSet<>();

    set.add(t1);
    Assert.assertTrue(set.contains(t1));
    Assert.assertTrue(set.contains(t2));
    Assert.assertFalse(set.contains(t3));
  }
}
