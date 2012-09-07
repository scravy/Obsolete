package net.abusingjava.jazz.widgets;


public class Test {

	public static class A {
		
	}
	
	interface I {
		
	}
	
	private static class B extends A implements I {
		
	}
	
	public static <T extends A & I> T factoryMethod() {
		return (T) new B();
	}
	
	public static void main(String... _) {
		A $a = Test.<B>factoryMethod();
		I $i = Test.<B>factoryMethod();
	}
	
}
