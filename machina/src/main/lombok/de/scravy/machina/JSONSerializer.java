package de.scravy.machina;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import de.scravy.pair.Pairs;

/**
 * INTERNAL
 */
class JSONSerializer implements StateMachineSerializer {

  private <S> Map<S, String> generateStateMapping(final Set<S> states) {
    final Map<S, String> stateMapping = new TreeMap<>(new Comparator<S>() {

      @Override
      public int compare(final S left, final S right) {
        return left.toString().compareTo(right.toString());
      }

    });

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

    final Set<Transition<S, T>> transitions = new TreeSet<>(
        new Comparator<Transition<S, T>>() {

          @Override
          public int compare(
              final Transition<S, T> left,
              final Transition<S, T> right) {
            return Pairs.compare(
                left.getFromState().toString(),
                left.getEventType().toString(),
                right.getFromState().toString(),
                right.getEventType().toString());
          }
        });
    transitions.addAll(stateMachine.getTransitions());

    final Set<S> states = StateMachines.getStatesFrom(stateMachine);
    final Map<S, String> stateMapping = generateStateMapping(states);

    appendable.append("{\n");
    appendable.append(" \"states\": [");

    boolean first = true;
    for (final Entry<S, String> state : stateMapping.entrySet()) {
      if (first) {
        first = false;
      } else {
        appendable.append(",");
      }
      appendable.append('\n');
      appendable.append(String.format(
          "  {\"state\": \"%s\", \"label\": \"%s\"}",
          state.getValue(), state.getKey()));
    }
    appendable.append("\n ],\n");

    appendable.append(" \"transitions\": [");
    first = true;
    for (final Transition<S, T> transition : transitions) {
      if (first) {
        first = false;
      } else {
        appendable.append(",");
      }
      final String from = stateMapping.get(transition.getFromState());
      final String to = stateMapping.get(transition.getToState());
      final String on = transition.getEventType() == null
          ? "*" : transition.getEventType().toString();
      appendable.append('\n');
      appendable.append(String.format(
          "  {\"from\": \"%s\", \"on\": \"%s\", \"to\": \"%s\" }",
          from, on, to));
    }
    appendable.append("\n ]\n}\n");

  }
}
