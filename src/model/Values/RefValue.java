package model.Values;

import model.Types.IType;
import model.Types.RefType;

public class RefValue implements Value {

    private int address;
    private IType locationType;

    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return this.address;
    }

    @Override
    public IType getType() {
        return new RefType(this.locationType);
    }

    @Override
    public Value copy() {
        return new RefValue(this.address, this.locationType.copy());
    }

    @Override
    public String toString() {
        return "(" + Integer.toString(this.address) + " , " + this.locationType.toString() + ")";
    }
}
