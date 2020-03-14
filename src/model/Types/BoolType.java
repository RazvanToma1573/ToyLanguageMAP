package model.Types;

import model.Values.BoolValue;
import model.Values.Value;

public class BoolType implements IType {

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BoolType)
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public IType copy() {
        return new BoolType();
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }
}
