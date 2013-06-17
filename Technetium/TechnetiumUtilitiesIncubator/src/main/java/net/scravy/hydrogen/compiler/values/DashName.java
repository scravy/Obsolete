package net.scravy.hydrogen.compiler.values;


public class DashName {

	private final String name;
	private final String prefix;

	public DashName(String declaredValue) {
		this.prefix = null;
		this.name = declaredValue;
	}

	public DashName(String prefix, String declaredValue) {
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
