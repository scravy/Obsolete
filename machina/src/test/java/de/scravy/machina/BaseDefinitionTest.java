package de.scravy.machina;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

public class BaseDefinitionTest {

  private final BaseDefinition<Character, String, Integer, BigInteger> d1 =
      new BaseDefinition<>(
          Character.class, String.class, Integer.class, BigInteger.class);
  private final BaseDefinition<Character, String, Integer, BigInteger> d2 =
      new BaseDefinition<>(
          Character.class, String.class, Integer.class, BigInteger.class);

  @Test(expected = IllegalArgumentException.class)
  public void testNull1() {
    new BaseDefinition<Character, String, Integer, BigInteger>(null,
        String.class, Integer.class, BigInteger.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull2() {
    new BaseDefinition<Character, String, Integer, BigInteger>(Character.class,
        null, Integer.class, BigInteger.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull3() {
    new BaseDefinition<Character, String, Integer, BigInteger>(Character.class,
        String.class, null, BigInteger.class);
  }

  @Test()
  public void testNull4() {
    new BaseDefinition<Character, String, Integer, BigInteger>(Character.class,
        String.class, Integer.class, null);
  }
  
  public void testGetters() {
    Assert.assertEquals(Character.class, d1.getStates());
    Assert.assertEquals(String.class, d1.getEvents());
    Assert.assertEquals(Integer.class, d1.getEventTypes());
    Assert.assertEquals(BigInteger.class, d1.getContext());
  }
  
  @Test
  public void equals() {
    Assert.assertEquals(d1, d1);
    Assert.assertEquals(d2, d1);
  }
}
