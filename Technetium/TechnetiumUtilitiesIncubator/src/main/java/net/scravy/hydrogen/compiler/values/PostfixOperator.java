package net.scravy.hydrogen.compiler.values;


public class PostfixOperator {

	private final String name;

	public PostfixOperator(String declaredValue) {
		this.name = declaredValue;
	}

	public String getName() {
		return name;
	}

}
