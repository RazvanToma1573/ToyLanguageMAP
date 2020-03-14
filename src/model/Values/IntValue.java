package model.Values;

import model.Types.IType;
import model.Types.IntType;

public class IntValue implements Value {

    private int value;

    public IntValue(int v) {
        this.value = v;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IntValue) {
            IntValue iv = (IntValue) obj;
            if (this.value == iv.getValue())
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public Value copy() {
        return new IntValue(this.value);
    }
}
