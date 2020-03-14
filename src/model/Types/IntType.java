package model.Types;

import model.Values.IntValue;
import model.Values.Value;

public class IntType implements IType {

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof IntType)
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public IType copy() {
        return new IntType();
    }

    @Override
    public Value defaultValue() {
        return new IntValue(-1);
    }
}
