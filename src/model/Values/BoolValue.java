package model.Values;

import model.Types.BoolType;
import model.Types.IType;

public class BoolValue implements Value {

    private boolean value;

    public BoolValue(boolean v) {
        this.value = v;
    }

    public boolean getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BoolValue){
            BoolValue bv = (BoolValue) obj;
            if (this.value == bv.getValue())
                return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return Boolean.toString(this.value);
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public Value copy() {
        return new BoolValue(this.value);
    }
}
