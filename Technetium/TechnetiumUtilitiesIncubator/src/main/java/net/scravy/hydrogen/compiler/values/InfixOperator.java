package net.scravy.hydrogen.compiler.values;


public class InfixOperator {

	private final String name;

	public InfixOperator(String declaredValue) {
		this.name = declaredValue;
	}

	public String getName() {
		return name;
	}

}
