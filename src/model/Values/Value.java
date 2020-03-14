package model.Values;

import model.Types.IType;

public interface Value {

    IType getType();
    Value copy();
}
