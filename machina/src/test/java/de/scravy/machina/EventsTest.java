package de.scravy.machina;

import org.junit.Assert;
import org.junit.Test;

public class EventsTest {

  @Test
  public void stringEvents() {
    final String string = "xyz";
    final StringBuilder builder = new StringBuilder();
    for (final Integer c : Events.fromString(string)) {
      if (c < 0) {
        break;
      }
      builder.append((char) (int) c);
    }
    Assert.assertEquals(string, builder.toString());
  }
}
