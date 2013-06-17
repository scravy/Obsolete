package net.scravy.technetium.util.dirt;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

// http://rw4.cs.uni-sb.de/projects/ganimal/GANIFA/page16_g.htm
public class NFA implements Cloneable {

	private final Map<String, Map<Integer, Set<String>>> delta =
			new HashMap<String, Map<Integer, Set<String>>>();

	private final Map<String, String> accepting = new HashMap<String, String>();

	private Set<String> q0 = new HashSet<String>();

	@Override
	public NFA clone() {
		NFA clone = new NFA();

		clone.delta.putAll(delta);
		clone.q0.addAll(q0);
		clone.accepting.putAll(accepting);

		return clone;
	}

	public void addAccepting(String state, String action) {
		accepting.put(state, action);
	}

	public void eliminateEpsilonEdges() {

		boolean containsEpsilon;
		do {
			containsEpsilon = false;
			List<String> keys = new ArrayList<String>(delta.keySet());

			for (String key : keys) {
				Map<Integer, Set<String>> stateDelta = delta.get(key);

				Set<String> epsilonEdges = stateDelta.get(null);

				if (epsilonEdges != null) {
					for (String edgeTo : epsilonEdges) {
						Map<Integer, Set<String>> edges = delta.get(edgeTo);

						for (Entry<Integer, Set<String>> edge : edges
								.entrySet()) {
							for (String to : edge.getValue()) {
								Integer symbol = edge.getKey();
								if (symbol != null || !key.equals(to)) {
									addRule(key, symbol, to);
									if (symbol == null) {
										containsEpsilon = true;
									}
								}
							}
						}
					}
					stateDelta.remove(null);
				}
				if (stateDelta.isEmpty()) {
					delta.remove(key);
				}
			}
		} while (containsEpsilon);
	}

	public void createSingleStartState(String state) {
		Map<Integer, Set<String>> stateDelta = new HashMap<Integer, Set<String>>();
		stateDelta.put(null, new HashSet<String>(q0));
		delta.put(state, stateDelta);
		q0.clear();
		q0.add(state);
	}

	public void addRule(String state, int symbol, String newState) {
		addRule(state, Integer.valueOf(symbol), newState);
	}

	public void addRule(String state, Integer symbol, String newState) {
		Map<Integer, Set<String>> stateDelta;
		if (delta.containsKey(state)) {
			stateDelta = delta.get(state);
		} else {
			stateDelta = new HashMap<Integer, Set<String>>();
			delta.put(state, stateDelta);
		}
		Set<String> newStates;
		if (stateDelta.containsKey(symbol)) {
			newStates = stateDelta.get(symbol);
		} else {
			newStates = new HashSet<String>();
			stateDelta.put(symbol, newStates);
		}
		newStates.add(newState);
	}

	@SuppressWarnings("unchecked")
	public Set<String> statesFor(String state, Integer input) {
		if (delta.containsKey(state)) {
			return Collections.unmodifiableSet(delta.get(state).get(input));
		}
		return Collections.EMPTY_SET;
	}

	public void addStartState(String state) {
		q0.add(state);
	}

	public void removeStartState(String state) {
		q0.remove(state);
	}

	public void showDot() throws IOException, InterruptedException {
		showDot("/usr/local/bin/dot", "png");
	}

	public void showDot(String pathToDot, String format) throws
			IOException, InterruptedException {
		String dot = toDot();

		File tmpFile = File.createTempFile("dot-file-", ".dot");
		FileOutputStream out = new FileOutputStream(tmpFile);
		out.write(dot.getBytes());
		out.close();

		File tmpOutFile = File.createTempFile("dot-rendering-",
				"." + format);

		ProcessBuilder pb = new ProcessBuilder(
				pathToDot, tmpFile.getAbsolutePath(),
				"-o", tmpOutFile.getAbsolutePath(),
				"-T", format);

		Process p = pb.start();

		p.waitFor();

		Desktop.getDesktop().open(tmpOutFile);

		tmpFile.delete();
		tmpOutFile.deleteOnExit();

		Thread.sleep(500);
	}

	public NavigableSet<String> getStates() {
		NavigableSet<String> states = new TreeSet<String>(delta.keySet());
		states.addAll(q0);
		for (Map<Integer, Set<String>> stateDelta : delta.values()) {
			for (Set<String> q : stateDelta.values()) {
				states.addAll(q);
			}
		}
		return states;
	}

	public String toDot() {
		StringBuilder builder = new StringBuilder();

		Object[] states = new ArrayList<String>(getStates()).toArray();
		Arrays.sort(states);

		builder.append("digraph DFA {\n");
		builder.append("rankdir=LR;\n");

		builder.append("node [shape = doublecircle];\n");

		if (accepting.size() > 0) {
			for (String state : accepting.keySet()) {
				builder.append(' ');
				builder.append("s" + Arrays.binarySearch(states, state));
			}
			builder.append(";\n");
		}

		builder.append("node [shape = circle];\n");

		for (int i = 0; i < states.length; i++) {
			builder.append(String.format("s%d [label = \"%s\"];\n", i,
					states[i]));
		}

		int i = 0;
		for (String q : q0) {
			i++;
			builder.append("_start");
			builder.append(i);
			builder.append(" [label =\"\", shape = point, rank = some];\n");
			builder.append("_start");
			builder.append(i);
			builder.append(" -> ");
			builder.append("s" + Arrays.binarySearch(states, q));
			builder.append(";\n");
		}

		for (Entry<String, Map<Integer, Set<String>>> stateDelta : delta
				.entrySet()) {
			for (Entry<Integer, Set<String>> rule : stateDelta.getValue()
					.entrySet()) {
				for (String target : rule.getValue()) {
					builder.append("s" + Arrays.binarySearch(
							states, stateDelta.getKey()));
					builder.append(" -> ");
					builder.append("s" + Arrays.binarySearch(
							states, target));
					builder.append(" [ label = \"");
					builder.append((char) (int) rule.getKey());
					builder.append("\" ];\n");
				}
			}
		}

		builder.append("}\n");

		return builder.toString();
	}

	public DFA toDFA() {
		NFA clone = clone();

		clone.createSingleStartState("_");

		clone.eliminateEpsilonEdges();

		return new DFA();
	}

	public static void main(String... args) throws Exception {

		NFA nfa = new NFA();

		nfa.addRule("s0", 'a', "s0");
		nfa.addRule("s0", 'b', "s0");
		nfa.addRule("s0", 'a', "s1");

		nfa.addRule("s1", 'b', "s2");

		nfa.addRule("s2", 'a', "s3");

		nfa.addStartState("s0");
		nfa.addAccepting("s3", "done");

		nfa.showDot();
	}
}
