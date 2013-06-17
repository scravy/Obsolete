package net.scravy.hydrogen.compiler.values;


public class MemberName {

	private final String prefix;
	private final String name;

	public MemberName(String declaredValue) {
		this.prefix = null;
		this.name = declaredValue;
	}

	public MemberName(String prefix, String declaredValue) {
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
