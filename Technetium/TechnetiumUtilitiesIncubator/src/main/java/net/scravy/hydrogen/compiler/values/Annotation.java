package net.scravy.hydrogen.compiler.values;

public class Annotation {

	private final String name;
	private final String prefix;

	public Annotation(String declaredValue) {
		this.prefix = null;
		this.name = declaredValue;
	}

	public Annotation(String prefix, String declaredValue) {
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
