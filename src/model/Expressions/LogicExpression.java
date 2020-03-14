package model.Expressions;

import model.HeapTable.MyIHeap;
import model.MyException;
import model.SymTable.MyIDictionary;
import model.Types.BoolType;
import model.Types.IType;
import model.Values.BoolValue;
import model.Values.Value;

public class LogicExpression implements Exp {

    private Exp expression1;
    private Exp expression2;
    char operator; //& and, | or

    public LogicExpression(Exp e1, Exp e2, char op) {
        this.expression1 = e1;
        this.expression2 = e2;
        this.operator = op;
    }

    @Override
    public String toString() {
        return this.expression1.toString() + " " + this.operator + " " + this.expression2.toString();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table,  MyIHeap<Value> hpTable) throws MyException {
        Value value1,value2;
        value1 = this.expression1.eval(table, hpTable);
        if(value1.getType().equals(new BoolType())){
            value2 = this.expression2.eval(table, hpTable);
            if (value2.getType().equals(new BoolType())){
                BoolValue bool1 = (BoolValue)value1;
                BoolValue bool2 = (BoolValue)value2;
                boolean b1 = bool1.getValue();
                boolean b2 = bool2.getValue();
                if (this.operator == '&') return new BoolValue(b1 && b2);
                if (this.operator == '|') return new BoolValue(b1 || b2);
            }
            else
                throw new MyException("LogicExpressionError: Second operand is not a boolean.");
        }
        else
            throw new MyException("LogicExpressionError: First operand is not a boolean.");
        return new BoolValue(false);
    }

    @Override
    public Exp copy() {
        char copyOperator = this.operator;
        return new LogicExpression(this.expression1.copy(),this.expression2.copy(),copyOperator);
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType type1, type2;
        type1 = this.expression1.typecheck(typeEnvironment);
        type2 = this.expression2.typecheck(typeEnvironment);

        if (type1.equals(new BoolType())){
            if (type2.equals(new BoolType())){
                return new BoolType();
            } else {
                throw new MyException("TypeExpressionError: Second operand is not a boolean");
            }
        } else {
            throw new MyException("TypeExpressionError: First operand is not a boolean");
        }
    }
}
