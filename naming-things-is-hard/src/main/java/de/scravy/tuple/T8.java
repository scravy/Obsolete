package de.scravy.tuple;

import java.io.Serializable;
import java.util.Objects;

public final class T8<V1, V2, V3, V4, V5, V6, V7, V8> implements Serializable {

    public final V1 _1;
    public final V2 _2;
    public final V3 _3;
    public final V4 _4;
    public final V5 _5;
    public final V6 _6;
    public final V7 _7;
    public final V8 _8;

    private T8(final V1 _1, final V2 _2, final V3 _3, final V4 _4, final V5 _5, final V6 _6, final V7 _7, final V8 _8) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
        this._6 = _6;
        this._7 = _7;
        this._8 = _8;
    }
    
    public static <U1, U2, U3, U4, U5, U6, U7, U8> T8<U1, U2, U3, U4, U5, U6, U7, U8>
            of(final U1 _1, final U2 _2, final U3 _3, final U4 _4, final U5 _5, final U6 _6, final U7 _7, final U8 _8) {
        return new T8<>(_1, _2, _3, _4, _5, _6, _7, _8);
    }

    public V1 fst() {
        return _1;
    }

    public V2 snd() {
        return _2;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof T8)) {
            return false;
        }
        if (other == this) {
            return true;
        }
        final T8 otherT2 = (T8) other;
        return Objects.equals(_1, otherT2._1)
                && Objects.equals(_2, otherT2._2)
                && Objects.equals(_3, otherT2._3)
                && Objects.equals(_4, otherT2._4)
                && Objects.equals(_5, otherT2._5)
                && Objects.equals(_6, otherT2._6)
                && Objects.equals(_7, otherT2._7)
                && Objects.equals(_8, otherT2._8);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_1, _2, _3, _4, _5, _6, _7, _8);
    }

    @Override
    public String toString() {
        return String.format("T8(%s, %s, %s, %s, %s, %s, %s, %s)", _1, _2, _3, _4, _5, _6, _7, _8);
    }

    public <V9> T9<V1, V2, V3, V4, V5, V6, V7, V8, V9> and(final V9 _9) {
        return T9.of(_1, _2, _3, _4, _5, _6, _7, _8, _9);
    }
}
