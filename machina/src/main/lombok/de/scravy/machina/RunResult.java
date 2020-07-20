package de.scravy.machina;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import de.scravy.pair.Pair;

@Value()
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RunResult<S, C> implements Pair<S, C> {

  private final S state;
  private final C context;

  @Override
  public S getFirst() {
    return this.state;
  }

  @Override
  public C getSecond() {
    return this.context;
  }

}
