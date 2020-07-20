package de.scravy.tuple;

import java.io.Serializable;
import java.util.Objects;

public final class T2<V1, V2> implements Serializable {

    public final V1 _1;
    public final V2 _2;
    
    private T2(final V1 _1, final V2 _2) {
        this._1 = _1;
        this._2 = _2;
    }
    
    public static <U1, U2> T2<U1, U2> of(final U1 _1, final U2 _2) {
        return new T2<>(_1, _2);
    }

    public V1 fst() {
        return _1;
    }

    public V2 snd() {
        return _2;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof T2)) {
            return false;
        }
        if (other == this) {
            return true;
        }
        final T2 otherT2 = (T2) other;
        return Objects.equals(_1, otherT2._1) && Objects.equals(_2, otherT2._2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_1, _2);
    }

    @Override
    public String toString() {
        return String.format("T2(%s, %s)", _1, _2);
    }

    public <V3> T3<V1, V2, V3> and(final V3 _3) {
        return T3.of(_1, _2, _3);
    }

    public <V3, V4> T4<V1, V2, V3, V4> and(final V3 _3, final V4 _4) {
        return T4.of(_1, _2, _3, _4);
    }

    public <V3, V4, V5> T5<V1, V2, V3, V4, V5> and(final V3 _3, final V4 _4, final V5 _5) {
        return T5.of(_1, _2, _3, _4, _5);
    }

    public <V3, V4, V5, V6> T6<V1, V2, V3, V4, V5, V6> and(final V3 _3, final V4 _4, final V5 _5, final V6 _6) {
        return T6.of(_1, _2, _3, _4, _5, _6);
    }

    public <V3, V4, V5, V6, V7> T7<V1, V2, V3, V4, V5, V6, V7> and(
            final V3 _3, final V4 _4, final V5 _5, final V6 _6, final V7 _7) {
        return T7.of(_1, _2, _3, _4, _5, _6, _7);
    }

    public <V3, V4, V5, V6, V7, V8> T8<V1, V2, V3, V4, V5, V6, V7, V8> and(
            final V3 _3, final V4 _4, final V5 _5, final V6 _6, final V7 _7, final V8 _8) {
        return T8.of(_1, _2, _3, _4, _5, _6, _7, _8);
    }

    public <V3, V4, V5, V6, V7, V8, V9> T9<V1, V2, V3, V4, V5, V6, V7, V8, V9> and(
            final V3 _3, final V4 _4, final V5 _5, final V6 _6, final V7 _7, final V8 _8, final V9 _9) {
        return T9.of(_1, _2, _3, _4, _5, _6, _7, _8, _9);
    }
}
