package de.scravy.machina.testdata;

import java.math.BigInteger;
import java.net.URL;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Builder
public class ThreeProperties {

  public static final ThreeProperties EXAMPLE_0 =
      new ThreeProperties("", BigInteger.ZERO, 0.0);

  public static final ThreeProperties EXAMPLE_1 =
      new ThreeProperties("hello", BigInteger.ONE, Math.E);

  public static final ThreeProperties EXAMPLE_2 =
      new ThreeProperties("world", BigInteger.ZERO, Math.PI);

  public static final ThreeProperties EXAMPLE_3 =
      new ThreeProperties("", BigInteger.TEN, 1.0);

  public static final ThreeProperties EXAMPLE_4 =
      new ThreeProperties("abcxyz", BigInteger.valueOf(2), 0.0);

  private final @Wither String one;

  private final @Wither BigInteger two;

  private final @Wither Double three;

  public URL getBuggy() {
    throw new RuntimeException();
  }
}
