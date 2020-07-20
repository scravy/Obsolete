package de.scravy.tuple;

import java.io.Serializable;
import java.util.Objects;

public final class T4<V1, V2, V3, V4> implements Serializable {

    public final V1 _1;
    public final V2 _2;
    public final V3 _3;
    public final V4 _4;

    private T4(final V1 _1, final V2 _2, final V3 _3, final V4 _4) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
    }
    
    public static <U1, U2, U3, U4> T4<U1, U2, U3, U4> of(final U1 _1, final U2 _2, final U3 _3, final U4 _4) {
        return new T4<>(_1, _2, _3, _4);
    }

    public V1 fst() {
        return _1;
    }

    public V2 snd() {
        return _2;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof T4)) {
            return false;
        }
        if (other == this) {
            return true;
        }
        final T4 otherT2 = (T4) other;
        return Objects.equals(_1, otherT2._1)
                && Objects.equals(_2, otherT2._2)
                && Objects.equals(_3, otherT2._3)
                && Objects.equals(_4, otherT2._4);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_1, _2, _3, _4);
    }

    @Override
    public String toString() {
        return String.format("T4(%s, %s, %s, %s)", _1, _2, _3, _4);
    }

    public <V5> T5<V1, V2, V3, V4, V5> and(final V5 _5) {
        return T5.of(_1, _2, _3, _4, _5);
    }
}
