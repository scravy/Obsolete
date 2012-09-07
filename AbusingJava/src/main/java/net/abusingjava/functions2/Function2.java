package net.abusingjava.functions2;

public interface Function2<A, B, R> {

	R apply(A $a, B $b);
	
	Function<B,R> apply(A $a);
	
}
