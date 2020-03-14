package model.Types;

import model.Values.Value;

public interface IType {

    IType copy();
    Value defaultValue();
}
