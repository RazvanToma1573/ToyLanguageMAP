package model.Expressions;

import model.HeapTable.MyIHeap;
import model.MyException;
import model.SymTable.MyIDictionary;
import model.Types.IType;
import model.Types.IntType;
import model.Values.IntValue;
import model.Values.Value;

public class ArithmeticExpression implements Exp {

    private Exp expression1;
    private Exp expression2;
    private char operator; //+ plus, - minus, * star, / divide

    public ArithmeticExpression(char op, Exp e1, Exp e2) {
        this.expression1 = e1;
        this.expression2 = e2;
        this.operator = op;
    }

    @Override
    public String toString() {
        return this.expression1.toString() +" "+ this.operator + " " + this.expression2.toString();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> hpTable) throws MyException {
        Value value1,value2;
        value1 = this.expression1.eval(table, hpTable);
        if(value1.getType().equals(new IntType())) {
            value2 = this.expression2.eval(table, hpTable);
            if(value2.getType().equals(new IntType())) {
                IntValue int1 = (IntValue)value1;
                IntValue int2 = (IntValue)value2;
                int n1,n2;
                n1 = int1.getValue();
                n2 = int2.getValue();
                if(this.operator == '+') return new IntValue(n1+n2);
                if(this.operator == '-') return new IntValue(n1-n2);
                if(this.operator == '*') return new IntValue(n1*n2);
                if(this.operator == '/')
                    if (n2 == 0)
                        throw new MyException("ArithmeticExpressionError: Division by zero.");
                    else
                        return new IntValue(n1/n2);
            }
            else
                throw new MyException("ArithmeticExpressionError: Second operand is not an integer.");
        }
        else
            throw new MyException("ArithmeticExpressionError: First operand is not an integer.");

        return new IntValue(0);
    }

    @Override
    public Exp copy() {
        char copyOperator = this.operator;
        return new ArithmeticExpression(copyOperator,this.expression1.copy(),this.expression2.copy());
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType type1, type2;
        type1 = this.expression1.typecheck(typeEnvironment);
        type2 = this.expression2.typecheck(typeEnvironment);
        if (type1.equals(new IntType())){
            if (type2.equals(new IntType())) {
                return new IntType();
            } else {
                throw new MyException("TypeExpressionError: Second operand is not an integer");
            }
        } else {
            throw new MyException("TypeExpressionError: First operand is not an integer");
        }
    }
}
