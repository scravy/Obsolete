package net.scravy.hydrogen.compiler.values;


public class BindingInfixOperator {

	private String name;

	public BindingInfixOperator(String declaredValue) {
		this.setName(declaredValue);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
