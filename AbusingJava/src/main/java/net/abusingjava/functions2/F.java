package net.abusingjava.functions2;

import java.util.Comparator;
import java.util.List;

import net.abusingjava.Author;
import net.abusingjava.Since;
import net.abusingjava.Version;

@Author("Julian Fleischer")
@Version("2012-01-09")
@Since("2012-01-09")
public class F {

	// lt :: ...
	
	public static <A extends Comparable<? super A>> Function2<A,A,Boolean> lt() {
		return null;
	}
	
	// ltBy :: ...

	public static <A, B extends Comparator<? super A>> Function3<B,A,A,Boolean> ltBy() {
		return null;
	}
	
	// gt :: ...
	
	public static <A extends Comparable<? super A>> Function2<A,A,Boolean> gt() {
		return null;
	}

	// gtBy :: ...
	
	public static <A, B extends Comparator<? super A>> Function3<B,A,A,Boolean> gtBy() {
		return null;
	}
	
	// ltOrEq :: ...
	
	public static <A extends Comparable<? super A>> Function2<A,A,Boolean> ltOrEq() {
		return null;
	}

	// ltOrEqBy :: ...
	
	public static <A, B extends Comparator<? super A>> Function3<B,A,A,Boolean> ltOrEqBy() {
		return null;
	}
	
	// gtOrEq :: ...
	
	public static <A extends Comparable<? super A>> Function2<A,A,Boolean> gtOrEq() {
		return null;
	}

	// gtOrEqBy :: ...
	
	public static <A extends Comparable<? super A>> Function2<A,A,Boolean> gtOrEqBy() {
		return null;
	}
	
	// compare :: ...
	
	public static <A extends Comparable<? super A>> Function2<A,A,Integer> compare() {
		return null;
	}
	
	// compareBy :: ...
	
	public static <A, B extends Comparator<? super A>> Function3<B,A,A,Integer> compareBy() {
		return null;
	}

	public static <A, B extends Comparator<? super A>> Function2<A,A,Integer> compareBy(final B $comparator) {
		return F.<A,B>compareBy().apply($comparator);
	}

	public static <A, B extends Comparator<? super A>> Function<A,Integer> compareBy(final B $comparator, final A $1) {
		return F.<A,B>compareBy().apply($comparator, $1);
	}

	public static <A, B extends Comparator<? super A>> Integer compareBy(final B $comparator, final A $1, final A $2) {
		return F.<A,B>compareBy().apply($comparator, $1, $2);
	}
	
	// instanceOf :: Class<?> -> ? -> Boolean
	
	public static Function2<Class<?>,?,Boolean> instanceOf() {
		return null;
	}

	public static Function<?,Boolean> instanceOf(final Class<?> $class) {
		return F.instanceOf().apply($class);
	}

	@SuppressWarnings("unused")
	public static Boolean instanceOf(final Class<?> $class, final Object $obj) {
		return null; // FIXME
	}
	
	// eq :: ? -> ? -> Boolean
	
	public static Function2<?,?,Boolean> eq() {
		return null;
	}
	
	public static Function<?,Boolean> eq(final Object $1) {
		return F.eq($1);
	}

	public static Boolean eq(final Object $1, final Object $2) {
		return F.eq($1, $2);
	}
	
	// not :: Boolean -> Boolean
	
	public static Function<Boolean,Boolean> not() {
		return null;
	}
	
	public static Boolean not(final Boolean $v) {
		return F.not($v);
	}
	
	// and :: Boolean -> Boolean -> Boolean
	
	public static Function2<Boolean,Boolean,Boolean> and() {
		return null;
	}
	
	// or :: Boolean -> Boolean -> Boolean
	
	public static Function2<Boolean,Boolean,Boolean> or() {
		return null;
	}
	
	// implies :: Boolean -> Boolean -> Boolean
	
	public static Function2<Boolean,Boolean,Boolean> implies() {
		return null;
	}
	
	// xor :: Boolean -> Boolean -> Boolean
	
	public static Function2<Boolean,Boolean,Boolean> xor() {
		return null;
	}
	
	// all<A,A> :: (A -> Boolean) -> [A] -> Boolean
	
	public static <A> Function2<Function<A,Boolean>,Iterable<A>,Boolean> all() {
		return null;
	}
	
	public static <A> Function<Iterable<A>,Boolean> all(final Function<A,Boolean> $f) {
		return F.<A>all().apply($f);
	}
	
	public static <A> Boolean all(final Function<A,Boolean> $f, final Iterable<A> $it) {
		return F.<A>all().apply($f, $it);
	}
	
	// map<A,B> :: (A -> B) -> [A] -> [B]
	
	public static <A,B> Function2<Function<A,B>,Iterable<A>,List<B>> map() {
		return null;
	}
	
	public static <A,B> Function<Iterable<A>,List<B>> map(final Function<A,B> $f) {
		return F.<A,B>map().apply($f);
	}

	public static <A,B> List<B> map(final Function<A,B> $f, final Iterable<A> $it) {
		return F.<A,B>map().apply($f, $it);
	}
	
	// foldl<A,B> :: (A -> B -> A) -> A -> [B] -> A
	
	public static <A,B> Function3<Function2<A,B,A>, A, Iterable<B>, A> foldl() {
		return null;
	}
	
	public static <A,B> Function2<A, Iterable<B>, A> foldl(final Function2<A,B,A> $f) {
		return F.<A,B>foldl().apply($f);
	}

	public static <A,B> Function<Iterable<B>, A> foldl(final Function2<A,B,A> $f, final A $init) {
		return F.<A,B>foldl().apply($f, $init);
	}

	public static <A,B> A foldl(final Function2<A,B,A> $f, final A $init, final Iterable<B> $it) {
		return F.<A,B>foldl().apply($f, $init, $it);
	}

	// foldr<A,B> :: (A -> B -> B) -> B -> [A] -> B
	
	public static <A,B> Function3<Function2<A,B,B>, B, Iterable<A>, B> foldr() {
		return null;
	}

	public static <A,B> Function2<A, Iterable<A>, B> foldr(final Function2<A,B,B> $f) {
		return F.<A,B>foldr($f);
	}

	public static <A,B> Function<Iterable<A>, B> foldr(final Function2<A,B,B> $f, final B $init) {
		return F.<A,B>foldr($f, $init);
	}

	public static <A,B> B foldr(final Function2<A,B,B> $f, final B $init, final Iterable<A> $it) {
		return F.<A,B>foldr($f, $init, $it);
	}
	
	// zipWith<A,B,C> :: (A -> B -> C) -> [A] -> [B] -> [C]
	
	public static <A,B,C> Function3<Function2<A,B,C>,Iterable<A>,Iterable<B>,List<C>> zipWith() {
		return null;
	}
	
	public static <A,B,C> Function2<Iterable<A>,Iterable<B>,List<C>> zipWith(final Function2<A,B,C> $f) {
		return F.<A,B,C>zipWith($f);
	}

	public static <A,B,C> Function<Iterable<B>,List<C>> zipWith(final Function2<A,B,C> $f, final Iterable<A> $it) {
		return F.<A,B,C>zipWith($f, $it);
	}

	public static <A,B,C> List<C> zipWith(final Function2<A,B,C> $f, final Iterable<A> $1, final Iterable<B> $2) {
		return F.<A,B,C>zipWith($f, $1, $2);
	}
}
