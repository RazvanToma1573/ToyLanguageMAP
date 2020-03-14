package model.Types;

import model.Values.RefValue;
import model.Values.Value;

public class RefType implements IType {

    private IType inner;

    public RefType(IType inner) { this.inner = inner; }

    public IType getInner() { return this.inner; }

    public boolean equals(Object another) {
        if (another instanceof RefType) {
            RefType anotherRef = (RefType) another;
            return this.inner.equals(anotherRef.getInner());
        } else {
            return false;
        }
    }

    @Override
    public IType copy() {
        return new RefType(this.inner.copy());
    }

    @Override
    public Value defaultValue() {
        return new RefValue(0, inner);
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }
}
