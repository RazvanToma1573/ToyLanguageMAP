package model.Values;

import model.Types.IType;
import model.Types.StringType;

public class StringValue implements Value {

    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StringValue) {
            StringValue sv = (StringValue) obj;
            if (this.value == sv.getValue())
                return true;
        }
        return false;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }



    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public Value copy() {
        return new StringValue(this.value);
    }
}
