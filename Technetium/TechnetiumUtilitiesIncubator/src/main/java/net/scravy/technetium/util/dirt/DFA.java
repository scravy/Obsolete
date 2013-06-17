package net.scravy.technetium.util.dirt;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import net.scravy.technetium.util.value.Tuple;
import net.scravy.technetium.util.value.ValueUtil;

// http://rw4.cs.uni-sb.de/projects/ganimal/GANIFA/page16_g.htm
public class DFA implements Cloneable {

	private final Map<String, Map<Integer, String>> delta =
			new HashMap<String, Map<Integer, String>>();

	private final Map<String, String> accepting = new HashMap<String, String>();

	private String q0 = null;
	private String failureStateLabel = "";

	public void removeFailureState() {
		delta.remove("");
		for (Map<Integer, String> rule : delta.values()) {
			Iterator<String> stringIt = rule.values().iterator();
			while (stringIt.hasNext()) {
				if (stringIt.next().isEmpty()) {
					stringIt.remove();
				}
			}
		}
	}

	public void completeEdgesWithFailureState() {
		NavigableSet<Integer> alphabet = getAlphabet();
		addAnyRule("", alphabet.first(), "");
		NavigableSet<String> states = getStates();

		for (String state : states) {
			Map<Integer, String> stateDelta = delta.get(state);
			if (stateDelta == null) {
				stateDelta = new HashMap<Integer, String>();
				delta.put(state, stateDelta);
			}
			for (Integer letter : alphabet) {
				if (!stateDelta.containsKey(letter)) {
					stateDelta.put(letter, "");
				}
			}
		}
	}

	public void removeUnreachableStates() {
		for (String unreachableState : getUnreachableStates()) {
			delta.remove(unreachableState);
		}
	}

	public boolean isComplete() {
		NavigableSet<String> states = getStates();
		if (!delta.keySet().containsAll(states)) {
			return false;
		}

		NavigableSet<Integer> alphabet = getAlphabet();
		for (Map<Integer, String> stateDelta : delta.values()) {
			if (!stateDelta.keySet().containsAll(alphabet)) {
				return false;
			}
		}

		return true;
	}

	public void minimize() {
		if (q0 == null) {
			throw new IllegalStateException("No start state set.");
		}

		boolean removeFailureStateAfterwards = !getStates().contains("");
		completeEdgesWithFailureState();

		removeUnreachableStates();

		NavigableSet<Integer> alphabet = getAlphabet();
		NavigableSet<String> states = getStates();
		// Step 3
		HashMap<Tuple<String, String>, Boolean> pairs =
				new HashMap<Tuple<String, String>, Boolean>();
		Set<String> accepting = this.accepting.keySet();
		for (String q1 : states) {
			for (String q2 : states.tailSet(q1)) {
				boolean c1 = accepting.contains(q1);
				boolean c2 = accepting.contains(q2);
				pairs.put(ValueUtil.tuple(q1, q2), c1 && !c2 || !c1 && c2);
			}
		}

		boolean doneSomething = false;
		do {
			doneSomething = false;
			for (Tuple<String, String> pair : pairs.keySet()) {
				for (Integer letter : alphabet) {
					String p1 = stateFor(pair.first, letter);
					String p2 = stateFor(pair.second, letter);
					Tuple<String, String> probablePair =
							p1.compareTo(p2) < 0
									? ValueUtil.tuple(p1, p2)
									: ValueUtil.tuple(p2, p1);
					if (pairs.get(probablePair)) {
						if (!pairs.get(pair)) {
							pairs.put(pair, Boolean.TRUE);
							doneSomething = true;
						}
					}
				}
			}
		} while (doneSomething);

		HashMap<String, Set<String>> equivalenceClasses = new HashMap<String, Set<String>>();
		for (Entry<Tuple<String, String>, Boolean> entry : pairs.entrySet()) {
			if (!entry.getValue()) {
				String q1 = entry.getKey().first;
				String q2 = entry.getKey().second;
				Set<String> eqc;
				if (equivalenceClasses.containsKey(q1)
						&& equivalenceClasses.containsKey(q2)) {
					eqc = equivalenceClasses.get(q1);
					eqc.addAll(equivalenceClasses.get(q2));
				} else if (equivalenceClasses.containsKey(q1)) {
					eqc = equivalenceClasses.get(q1);
				} else if (equivalenceClasses.containsKey(q2)) {
					eqc = equivalenceClasses.get(q2);
				} else {
					eqc = new HashSet<String>();
				}
				eqc.add(q1);
				eqc.add(q2);
				if (eqc.size() > 1) {
					equivalenceClasses.put(q1, eqc);
					equivalenceClasses.put(q2, eqc);
				}
			}
		}

		Map<String, Map<Integer, String>> newRules = new HashMap<String, Map<Integer, String>>();
		Iterator<Entry<String, Map<Integer, String>>> it = delta.entrySet()
				.iterator();
		while (it.hasNext()) {
			Entry<String, Map<Integer, String>> rule = it.next();

			if (equivalenceClasses.containsKey(rule.getKey())) {
				newRules.put(
						equivalenceClasses.get(rule.getKey()).toString(),
						rule.getValue());
				it.remove();
			}

			for (Integer key : rule.getValue().keySet()) {
				if (equivalenceClasses.containsKey(rule.getValue().get(key))) {
					rule.getValue().put(key, equivalenceClasses
							.get(rule.getValue().get(key))
							.toString());
				}
			}
		}
		delta.putAll(newRules);

		for (String q : equivalenceClasses.keySet()) {
			if (this.accepting.containsKey(q)) {
				String action = this.accepting.remove(q);
				this.accepting.put(
						equivalenceClasses.get(q).toString(), action);
			}
		}

		if (equivalenceClasses.containsKey(q0)) {
			q0 = equivalenceClasses.get(q0).toString();
		}

		if (removeFailureStateAfterwards) {
			removeFailureState();
		}
	}

	public String stateFor(String state, Integer input) {
		if (delta.containsKey(state)) {
			return delta.get(state).get(input);
		}
		return null;
	}

	public NavigableSet<String> getStates() {
		NavigableSet<String> states = new TreeSet<String>(delta.keySet());
		if (q0 != null) {
			states.add(q0);
		}
		for (Map<Integer, String> stateDelta : delta.values()) {
			states.addAll(stateDelta.values());
		}
		return states;
	}

	public NavigableSet<Integer> getAlphabet() {
		TreeSet<Integer> alphabet = new TreeSet<Integer>();
		for (Map<Integer, String> stateDelta : delta.values()) {
			alphabet.addAll(stateDelta.keySet());
		}
		return alphabet;
	}

	public NavigableSet<String> getUnreachableStates() {
		NavigableSet<String> unreachableStates =
				new TreeSet<String>(delta.keySet());
		unreachableStates.removeAll(getReachableStates());
		return unreachableStates;
	}

	/**
	 * Retrieves a Set of all states which are reachable from the starting
	 * state.
	 * 
	 * @return A Set of all states which are reachable from the starting state.
	 * @since 1.0
	 * 
	 * @throws IllegalStateException
	 *             If no start state has been set yet.
	 */
	public NavigableSet<String> getReachableStates() {
		if (q0 == null) {
			throw new IllegalStateException(
					"No start state has been set yet.");
		}

		NavigableSet<String> states = new TreeSet<String>();

		TreeSet<String> justReached = new TreeSet<String>();
		TreeSet<String> toBeChecked = new TreeSet<String>();

		states.add(q0);
		toBeChecked.add(q0);

		while (!toBeChecked.isEmpty()) {
			for (String state : toBeChecked) {
				Map<Integer, String> stateDelta = delta.get(state);
				if (stateDelta != null) {
					for (String stateReached : stateDelta.values()) {
						justReached.add(stateReached);
					}
				}
			}
			justReached.removeAll(states);
			states.addAll(justReached);
			TreeSet<String> swap = toBeChecked;
			toBeChecked = justReached;
			justReached = swap;
			justReached.clear();
		}

		return states;
	}

	public void addRule(String state, int symbol, String newState) {
		addRule(state, Integer.valueOf(symbol), newState);
	}

	private void addAnyRule(String state, Integer symbol, String newState) {
		Map<Integer, String> stateDelta;
		if (delta.containsKey(state)) {
			stateDelta = delta.get(state);
		} else {
			stateDelta = new HashMap<Integer, String>();
			delta.put(state, stateDelta);
		}
		stateDelta.put(Integer.valueOf(symbol), newState);
	}

	public void addRule(String state, Integer symbol, String newState) {
		if (state == null || state.isEmpty() || symbol == null
				|| newState == null || newState.isEmpty()) {
			throw new IllegalArgumentException();
		}
		addAnyRule(state, symbol, newState);
	}

	public void setStart(String state) {
		q0 = state;
	}

	public String getStart() {
		return q0;
	}

	public void setAccepting(String state, String token) {
		accepting.put(state, token);
	}

	public void setNotAccepting(String state) {
		accepting.remove(state);
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
			builder.append(String.format(
					"s%d [label = \"%s\"];\n", i,
					states[i].toString().isEmpty()
							? failureStateLabel
							: states[i]));
		}

		builder.append("_startNode [label =\"\", shape = point, rank = some];\n");
		builder.append("_startNode -> ");
		builder.append("s" + Arrays.binarySearch(states, q0));
		builder.append(";\n");

		for (Entry<String, Map<Integer, String>> stateDelta : delta.entrySet()) {
			for (Entry<Integer, String> rule : stateDelta.getValue().entrySet()) {
				builder.append("s" + Arrays.binarySearch(
						states, stateDelta.getKey()));
				builder.append(" -> ");
				builder.append("s" + Arrays.binarySearch(
						states, rule.getValue()));
				builder.append(" [ label = \"");
				builder.append((char) (int) rule.getKey());
				builder.append("\" ];\n");
			}
		}

		builder.append("}\n");

		return builder.toString();
	}

	public static void main(String... args) throws Exception {
		DFA dfa = new DFA();

		dfa.setStart("S");

		dfa.addRule("q0", '0', "q4");
		dfa.addRule("q0", '1', "q1");
		dfa.addRule("q1", '0', "q2");
		dfa.addRule("q1", '1', "q1");
		dfa.addRule("q2", '0', "q3");
		dfa.addRule("q3", '1', "q3");
		dfa.addRule("q4", '0', "q5");
		dfa.addRule("q5", '1', "q3");

		dfa.setStart("q0");

		dfa.setAccepting("q3", null);
		dfa.setAccepting("q5", null);

		dfa.setFailureStateLabel("q6");

		dfa.completeEdgesWithFailureState();

		dfa.showDot();

		dfa.minimize();

		dfa.showDot();
	}

	public String getFailureStateLabel() {
		return failureStateLabel;
	}

	public void setFailureStateLabel(String failureStateLabel) {
		if (failureStateLabel == null) {
			throw new IllegalArgumentException();
		}

		this.failureStateLabel = failureStateLabel;
	}
}
