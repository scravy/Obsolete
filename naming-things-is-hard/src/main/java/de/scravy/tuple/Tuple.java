package de.scravy.tuple;

public final class Tuple {

    private Tuple() {
        throw new UnsupportedOperationException();
    }

    public static <V1, V2> T2<V1, V2> of(final V1 _1, final V2 _2) {
        return T2.of(_1, _2);
    }

    public static <V1, V2, V3> T3<V1, V2, V3> of(
            final V1 _1, final V2 _2, final V3 _3) {
        return T3.of(_1, _2, _3);
    }

    public static <V1, V2, V3, V4> T4<V1, V2, V3, V4> of(
            final V1 _1, final V2 _2, final V3 _3, final V4 _4) {
        return T4.of(_1, _2, _3, _4);
    }

    public static <V1, V2, V3, V4, V5> T5<V1, V2, V3, V4, V5> of(
            final V1 _1, final V2 _2, final V3 _3, final V4 _4, final V5 _5) {
        return T5.of(_1, _2, _3, _4, _5);
    }

    public static <V1, V2, V3, V4, V5, V6> T6<V1, V2, V3, V4, V5, V6> of(
            final V1 _1, final V2 _2, final V3 _3, final V4 _4, final V5 _5, final V6 _6) {
        return T6.of(_1, _2, _3, _4, _5, _6);
    }

    public static <V1, V2, V3, V4, V5, V6, V7> T7<V1, V2, V3, V4, V5, V6, V7> of(
            final V1 _1, final V2 _2, final V3 _3, final V4 _4, final V5 _5, final V6 _6, final V7 _7) {
        return T7.of(_1, _2, _3, _4, _5, _6, _7);
    }

    public static <V1, V2, V3, V4, V5, V6, V7, V8> T8<V1, V2, V3, V4, V5, V6, V7, V8> of(
            final V1 _1, final V2 _2, final V3 _3, final V4 _4, final V5 _5, final V6 _6, final V7 _7, final V8 _8) {
        return T8.of(_1, _2, _3, _4, _5, _6, _7, _8);
    }

    public static <V1, V2, V3, V4, V5, V6, V7, V8, V9> T9<V1, V2, V3, V4, V5, V6, V7, V8, V9> of(
            final V1 _1, final V2 _2, final V3 _3, final V4 _4, final V5 _5,
            final V6 _6, final V7 _7, final V8 _8, final V9 _9) {
        return T9.of(_1, _2, _3, _4, _5, _6, _7, _8, _9);
    }


    public static <V1, V2> V1 _1(final T2<V1, V2> t2) {
        return t2._1;
    }

    public static <V1, V2, V3> V1 _1(final T3<V1, V2, V3> t3) {
        return t3._1;
    }

    public static <V1, V2, V3, V4> V1 _1(final T4<V1, V2, V3, V4> t4) {
        return t4._1;
    }

    public static <V1, V2, V3, V4, V5> V1 _1(final T5<V1, V2, V3, V4, V5> t5) {
        return t5._1;
    }

    public static <V1, V2, V3, V4, V5, V6> V1 _1(final T6<V1, V2, V3, V4, V5, V6> t6) {
        return t6._1;
    }

    public static <V1, V2, V3, V4, V5, V6, V7> V1 _1(final T7<V1, V2, V3, V4, V5, V6, V7> t7) {
        return t7._1;
    }

    public static <V1, V2, V3, V4, V5, V6, V7, V8> V1 _1(final T8<V1, V2, V3, V4, V5, V6, V7, V8> t8) {
        return t8._1;
    }

    public static <V1, V2, V3, V4, V5, V6, V7, V8, V9> V1 _1(final T9<V1, V2, V3, V4, V5, V6, V7, V8, V9> t9) {
        return t9._1;
    }


    public static <V1, V2> V2 _2(final T2<V1, V2> t2) {
        return t2._2;
    }

    public static <V1, V2, V3> V2 _2(final T3<V1, V2, V3> t3) {
        return t3._2;
    }

    public static <V1, V2, V3, V4> V2 _2(final T4<V1, V2, V3, V4> t4) {
        return t4._2;
    }

    public static <V1, V2, V3, V4, V5> V2 _2(final T5<V1, V2, V3, V4, V5> t5) {
        return t5._2;
    }

    public static <V1, V2, V3, V4, V5, V6> V2 _2(final T6<V1, V2, V3, V4, V5, V6> t6) {
        return t6._2;
    }

    public static <V1, V2, V3, V4, V5, V6, V7> V2 _2(final T7<V1, V2, V3, V4, V5, V6, V7> t7) {
        return t7._2;
    }

    public static <V1, V2, V3, V4, V5, V6, V7, V8> V2 _2(final T8<V1, V2, V3, V4, V5, V6, V7, V8> t8) {
        return t8._2;
    }

    public static <V1, V2, V3, V4, V5, V6, V7, V8, V9> V2 _2(final T9<V1, V2, V3, V4, V5, V6, V7, V8, V9> t9) {
        return t9._2;
    }


    public static <V1, V2, V3> V3 _3(final T3<V1, V2, V3> t3) {
        return t3._3;
    }

    public static <V1, V2, V3, V4> V3 _3(final T4<V1, V2, V3, V4> t4) {
        return t4._3;
    }

    public static <V1, V2, V3, V4, V5> V3 _3(final T5<V1, V2, V3, V4, V5> t5) {
        return t5._3;
    }

    public static <V1, V2, V3, V4, V5, V6> V3 _3(final T6<V1, V2, V3, V4, V5, V6> t6) {
        return t6._3;
    }

    public static <V1, V2, V3, V4, V5, V6, V7> V3 _3(final T7<V1, V2, V3, V4, V5, V6, V7> t7) {
        return t7._3;
    }

    public static <V1, V2, V3, V4, V5, V6, V7, V8> V3 _3(final T8<V1, V2, V3, V4, V5, V6, V7, V8> t8) {
        return t8._3;
    }

    public static <V1, V2, V3, V4, V5, V6, V7, V8, V9> V3 _3(final T9<V1, V2, V3, V4, V5, V6, V7, V8, V9> t9) {
        return t9._3;
    }


    public static <V1, V2, V3, V4> V4 _4(final T4<V1, V2, V3, V4> t4) {
        return t4._4;
    }

    public static <V1, V2, V3, V4, V5> V4 _4(final T5<V1, V2, V3, V4, V5> t5) {
        return t5._4;
    }

    public static <V1, V2, V3, V4, V5, V6> V4 _4(final T6<V1, V2, V3, V4, V5, V6> t6) {
        return t6._4;
    }

    public static <V1, V2, V3, V4, V5, V6, V7> V4 _4(final T7<V1, V2, V3, V4, V5, V6, V7> t7) {
        return t7._4;
    }

    public static <V1, V2, V3, V4, V5, V6, V7, V8> V4 _4(final T8<V1, V2, V3, V4, V5, V6, V7, V8> t8) {
        return t8._4;
    }

    public static <V1, V2, V3, V4, V5, V6, V7, V8, V9> V4 _4(final T9<V1, V2, V3, V4, V5, V6, V7, V8, V9> t9) {
        return t9._4;
    }


    public static <V1, V2, V3, V4, V5> V5 _5(final T5<V1, V2, V3, V4, V5> t5) {
        return t5._5;
    }

    public static <V1, V2, V3, V4, V5, V6> V5 _5(final T6<V1, V2, V3, V4, V5, V6> t6) {
        return t6._5;
    }

    public static <V1, V2, V3, V4, V5, V6, V7> V5 _5(final T7<V1, V2, V3, V4, V5, V6, V7> t7) {
        return t7._5;
    }

    public static <V1, V2, V3, V4, V5, V6, V7, V8> V5 _5(final T8<V1, V2, V3, V4, V5, V6, V7, V8> t8) {
        return t8._5;
    }

    public static <V1, V2, V3, V4, V5, V6, V7, V8, V9> V5 _5(final T9<V1, V2, V3, V4, V5, V6, V7, V8, V9> t9) {
        return t9._5;
    }


    public static <V1, V2, V3, V4, V5, V6> V6 _6(final T6<V1, V2, V3, V4, V5, V6> t6) {
        return t6._6;
    }

    public static <V1, V2, V3, V4, V5, V6, V7> V6 _6(final T7<V1, V2, V3, V4, V5, V6, V7> t7) {
        return t7._6;
    }

    public static <V1, V2, V3, V4, V5, V6, V7, V8> V6 _6(final T8<V1, V2, V3, V4, V5, V6, V7, V8> t8) {
        return t8._6;
    }

    public static <V1, V2, V3, V4, V5, V6, V7, V8, V9> V6 _6(final T9<V1, V2, V3, V4, V5, V6, V7, V8, V9> t9) {
        return t9._6;
    }


    public static <V1, V2, V3, V4, V5, V6, V7> V7 _7(final T7<V1, V2, V3, V4, V5, V6, V7> t7) {
        return t7._7;
    }

    public static <V1, V2, V3, V4, V5, V6, V7, V8> V7 _7(final T8<V1, V2, V3, V4, V5, V6, V7, V8> t8) {
        return t8._7;
    }

    public static <V1, V2, V3, V4, V5, V6, V7, V8, V9> V7 _7(final T9<V1, V2, V3, V4, V5, V6, V7, V8, V9> t9) {
        return t9._7;
    }


    public static <V1, V2, V3, V4, V5, V6, V7, V8> V8 _8(final T8<V1, V2, V3, V4, V5, V6, V7, V8> t8) {
        return t8._8;
    }

    public static <V1, V2, V3, V4, V5, V6, V7, V8, V9> V8 _8(final T9<V1, V2, V3, V4, V5, V6, V7, V8, V9> t9) {
        return t9._8;
    }


    public static <V1, V2, V3, V4, V5, V6, V7, V8, V9> V9 _9(final T9<V1, V2, V3, V4, V5, V6, V7, V8, V9> t9) {
        return t9._9;
    }
}
