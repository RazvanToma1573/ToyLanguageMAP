package model.Expressions;

import model.HeapTable.MyIHeap;
import model.MyException;
import model.SymTable.MyIDictionary;
import model.Types.IType;
import model.Values.Value;

public class ValueExpression implements Exp {

    private Value e;

    public ValueExpression(Value e) {
        this.e = e;
    }

    @Override
    public String toString() {
        return e.toString();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table,  MyIHeap<Value> hpTable) throws MyException {
        return e;
    }

    @Override
    public Exp copy() {
        return new ValueExpression(this.e.copy());
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        return e.getType();
    }
}
