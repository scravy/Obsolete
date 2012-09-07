package net.abusingjava.functions2;

public interface Function4<A,B,C,D,R> {

	R apply(A $a, B $b, C $c, D $d);
	
	Function<D,R> apply(A $a, B $b, C $c);

	Function2<C,D,R> apply(A $a, B $b);

	Function3<B,C,D,R> apply(A $a);
}
