package model.Types;

import model.Values.StringValue;
import model.Values.Value;

public class StringType implements IType {

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StringType)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public IType copy() {
        return new StringType();
    }

    @Override
    public Value defaultValue() {
        return new StringValue("not initialized");
    }

}
