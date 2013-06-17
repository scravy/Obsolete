package net.scravy.hydrogen.compiler.values;


public class PrefixOperator {

	private final String name;

	public PrefixOperator(String declaredValue) {
		this.name = declaredValue;
	}

	public String getName() {
		return name;
	}

}
