package de.scravy.machina.testdata;

import lombok.Value;
import de.scravy.machina.EventWithType;

@Value
public class SomeEvent implements EventWithType<String> {

  private final String type;
}
