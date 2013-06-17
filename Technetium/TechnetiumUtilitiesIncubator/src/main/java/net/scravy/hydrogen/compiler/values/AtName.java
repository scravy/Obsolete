package net.scravy.hydrogen.compiler.values;


public class AtName {

	private final String name;
	private final String prefix;

	public AtName(String declaredValue) {
		this.prefix = null;
		this.name = declaredValue;
	}

	public AtName(String prefix, String declaredValue) {
		this.prefix = prefix;
		this.name = declaredValue;
	}

	public String getName() {
		return name;
	}

	public String getPrefix() {
		return prefix;
	}

}
