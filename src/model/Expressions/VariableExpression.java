package model.Expressions;

import model.HeapTable.MyIHeap;
import model.MyException;
import model.SymTable.MyIDictionary;
import model.Types.IType;
import model.Values.Value;

public class VariableExpression implements Exp {

    private String id;

    public VariableExpression(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table,  MyIHeap<Value> hpTable) throws MyException {
        if (!table.isDefined(this.id))
            throw new MyException("VariableExpressionError: The variable is not in the symbol table.");
        return table.lookup(id);
    }

    @Override
    public Exp copy() {
        String copyId = this.id;
        return new VariableExpression(copyId);
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        return typeEnvironment.lookup(this.id);
    }
}
