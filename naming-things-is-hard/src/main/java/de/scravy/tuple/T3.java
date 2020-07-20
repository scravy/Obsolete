package de.scravy.tuple;

import java.io.Serializable;
import java.util.Objects;

public final class T3<V1, V2, V3> implements Serializable {

    public final V1 _1;
    public final V2 _2;
    public final V3 _3;

    private T3(final V1 _1, final V2 _2, final V3 _3) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
    }
    
    public static <U1, U2, U3> T3<U1, U2, U3> of(final U1 _1, final U2 _2, final U3 _3) {
        return new T3<>(_1, _2, _3);
    }

    public V1 fst() {
        return _1;
    }

    public V2 snd() {
        return _2;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof T3)) {
            return false;
        }
        if (other == this) {
            return true;
        }
        final T3 otherT2 = (T3) other;
        return Objects.equals(_1, otherT2._1)
                && Objects.equals(_2, otherT2._2)
                && Objects.equals(_3, otherT2._3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_1, _2, _3);
    }

    @Override
    public String toString() {
        return String.format("T3(%s, %s, %s)", _1, _2, _3);
    }

    public <V4> T4<V1, V2, V3, V4> and(final V4 _4) {
        return T4.of(_1, _2, _3, _4);
    }
}
