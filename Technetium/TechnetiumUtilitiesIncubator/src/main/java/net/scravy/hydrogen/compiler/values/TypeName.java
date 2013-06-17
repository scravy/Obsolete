package net.scravy.hydrogen.compiler.values;


public class TypeName {

	private final String name;
	private final String prefix;

	public TypeName(String declaredValue) {
		this.prefix = null;
		this.name = declaredValue;
	}

	public TypeName(String prefix, String declaredValue) {
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
