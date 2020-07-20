package de.scravy.tuple;

import java.io.Serializable;
import java.util.Objects;

public final class T5<V1, V2, V3, V4, V5> implements Serializable {

    public final V1 _1;
    public final V2 _2;
    public final V3 _3;
    public final V4 _4;
    public final V5 _5;

    private T5(final V1 _1, final V2 _2, final V3 _3, final V4 _4, final V5 _5) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
    }
    
    public static <U1, U2, U3, U4, U5> T5<U1, U2, U3, U4, U5>
            of(final U1 _1, final U2 _2, final U3 _3, final U4 _4, final U5 _5) {
        return new T5<>(_1, _2, _3, _4, _5);
    }

    public V1 fst() {
        return _1;
    }

    public V2 snd() {
        return _2;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof T5)) {
            return false;
        }
        if (other == this) {
            return true;
        }
        final T5 otherT2 = (T5) other;
        return Objects.equals(_1, otherT2._1)
                && Objects.equals(_2, otherT2._2)
                && Objects.equals(_3, otherT2._3)
                && Objects.equals(_4, otherT2._4)
                && Objects.equals(_5, otherT2._5);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_1, _2, _3, _4, _5);
    }

    @Override
    public String toString() {
        return String.format("T5(%s, %s, %s, %s, %s)", _1, _2, _3, _4, _5);
    }

    public <V6> T6<V1, V2, V3, V4, V5, V6> and(final V6 _6) {
        return T6.of(_1, _2, _3, _4, _5, _6);
    }
}
