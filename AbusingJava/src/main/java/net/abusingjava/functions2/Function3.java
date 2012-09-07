package net.abusingjava.functions2;

public interface Function3<A,B,C,R> {

	R apply(A $a, B $b, C $c);
	
	Function<C,R> apply(A $a, B $b);

	Function2<B,C,R> apply(A $a);
}
