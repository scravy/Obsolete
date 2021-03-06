package de.scravy.tuple;

import java.io.Serializable;
import java.util.Objects;

public final class T6<V1, V2, V3, V4, V5, V6> implements Serializable {

    public final V1 _1;
    public final V2 _2;
    public final V3 _3;
    public final V4 _4;
    public final V5 _5;
    public final V6 _6;

    private T6(final V1 _1, final V2 _2, final V3 _3, final V4 _4, final V5 _5, final V6 _6) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
        this._6 = _6;
    }
    
    public static <U1, U2, U3, U4, U5, U6> T6<U1, U2, U3, U4, U5, U6>
            of(final U1 _1, final U2 _2, final U3 _3, final U4 _4, final U5 _5, final U6 _6) {
        return new T6<>(_1, _2, _3, _4, _5, _6);
    }

    public V1 fst() {
        return _1;
    }

    public V2 snd() {
        return _2;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof T6)) {
            return false;
        }
        if (other == this) {
            return true;
        }
        final T6 otherT2 = (T6) other;
        return Objects.equals(_1, otherT2._1)
                && Objects.equals(_2, otherT2._2)
                && Objects.equals(_3, otherT2._3)
                && Objects.equals(_4, otherT2._4)
                && Objects.equals(_5, otherT2._5)
                && Objects.equals(_6, otherT2._6);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_1, _2, _3, _4, _5, _6);
    }

    @Override
    public String toString() {
        return String.format("T6(%s, %s, %s, %s, %s, %s)", _1, _2, _3, _4, _5, _6);
    }

    public <V7> T7<V1, V2, V3, V4, V5, V6, V7> and(final V7 _7) {
        return T7.of(_1, _2, _3, _4, _5, _6, _7);
    }
}
