package net.scravy.hydrogen.compiler.values;


public class UnderscoreName {

	private final String name;
	private final String prefix;

	public UnderscoreName(String declaredValue) {
		this.prefix = null;
		this.name = declaredValue;
	}

	public UnderscoreName(String prefix, String declaredValue) {
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
