package net.scravy.hydrogen.compiler.values;


public class CamelCaseName {

	private final String name;
	private final String prefix;

	public CamelCaseName(String declaredValue) {
		this.prefix = null;
		this.name = declaredValue;
	}

	public CamelCaseName(String prefix, String declaredValue) {
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
