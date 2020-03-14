package model.Expressions;

import model.HeapTable.MyIHeap;
import model.MyException;
import model.SymTable.MyIDictionary;
import model.Types.IType;
import model.Types.RefType;
import model.Values.RefValue;
import model.Values.Value;

public class rHExpression implements Exp {

    private Exp expression;

    public rHExpression(Exp expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "rH( " + this.expression.toString() + " )";
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> hpTable) throws MyException {
        Value value = this.expression.eval(table, hpTable);
        IType type = value.getType();
        if(type instanceof RefType) {
            RefValue refValue = (RefValue) value;
            int address = refValue.getAddress();
            if (hpTable.isDefined(address))
                return hpTable.getValue(address);
            else
                throw new MyException("ExpressionError: Value not in heap table.");
        } else {
            throw new MyException("ExpressionError: Expression not evaluated to a RefValue.");
        }
    }

    @Override
    public Exp copy() {
        return new rHExpression(this.expression.copy());
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType type = this.expression.typecheck(typeEnvironment);
        if (type instanceof  RefType) {
             RefType reft = (RefType) type;
             return reft.getInner();
        } else {
            throw new MyException("TypeExpressionError: The rH argument is not a RefType");
        }
    }
}
