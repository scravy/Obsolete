package de.scravy.machina;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * INTERNAL
 */
class GraphvizSerializer implements StateMachineSerializer {

  private <S> Map<S, String> generateStateMapping(final Set<S> states) {
    final Map<S, String> stateMapping = new HashMap<>(states.size());

    BigInteger i = BigInteger.ZERO;
    for (final S state : states) {
      i = i.add(BigInteger.ONE);
      final String name = "s" + i.toString();
      stateMapping.put(state, name);
    }
    return stateMapping;
  }

  @Override
  public <S, T> void serialize(
      final StateMachine<S, ?, T> stateMachine, final Appendable appendable)
      throws IOException {

    final Set<S> states = StateMachines.getStatesFrom(stateMachine);
    final Map<S, String> stateMapping = generateStateMapping(states);

    appendable.append("digraph G {\n");
    appendable.append('\n');

    for (final Entry<S, String> state : stateMapping.entrySet()) {
      appendable.append(String.format(
          "  %s [label=\"%s\"];", state.getValue(), state.getKey()));
      appendable.append('\n');
    }
    appendable.append('\n');

    for (final Transition<S, T> transition : stateMachine.getTransitions()) {
      final String from = stateMapping.get(transition.getFromState());
      final String to = stateMapping.get(transition.getToState());
      final String label = transition.getEventType() == null
          ? "*" : transition.getEventType().toString();
      appendable.append(String.format(
          "  %s -> %s [label=\"%s\"];", from, to, label));
      appendable.append('\n');
    }
    appendable.append("\n}\n");
  }
}
