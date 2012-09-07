package net.abusingjava.swing.magix.types;

import java.util.ArrayList;
import java.util.Iterator;

public class ClassNames implements Iterable<String> {

	final ArrayList<String> $names;
	
	public ClassNames(final String $value) {
		String[] $names = $value.trim().split("[,; ]+");
		this.$names = new ArrayList<String>($names.length);
		for (String $name : $names) {
			this.$names.add($name);
		}
	}
	
	public boolean contains(final String $name) {
		return $names.contains($name.trim());
	}

	@Override
	public Iterator<String> iterator() {
		return $names.iterator();
	}
}
