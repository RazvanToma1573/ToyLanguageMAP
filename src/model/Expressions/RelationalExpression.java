package model.Expressions;

import model.HeapTable.MyIHeap;
import model.MyException;
import model.SymTable.MyIDictionary;
import model.Types.BoolType;
import model.Types.IType;
import model.Types.IntType;
import model.Values.BoolValue;
import model.Values.IntValue;
import model.Values.Value;

public class RelationalExpression implements Exp {

    private Exp expression1;
    private Exp expression2;
    private String operator;

    public RelationalExpression(Exp expression1, Exp expressino2, String operator) {
        this.expression1 = expression1;
        this.expression2 = expressino2;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return expression1.toString() + " " + operator + " " + expression2.toString();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table,  MyIHeap<Value> hpTable) throws MyException {
        Value value1 = this.expression1.eval(table, hpTable);
        if (value1.getType().equals(new IntType())) {
            Value value2 = this.expression2.eval(table, hpTable);
            if (value2.getType().equals(new IntType())){
                IntValue int1 = (IntValue) value1;
                IntValue int2 = (IntValue) value2;
                int int1Value = int1.getValue();
                int int2Value = int2.getValue();
                if (this.operator.equals("<")){

                    if (int1Value < int2Value)
                        return new BoolValue(true);
                    else
                        return new BoolValue(false);

                } else if (this.operator.equals("<=")){

                    if (int1Value <= int2Value)
                        return new BoolValue(true);
                    else
                        return new BoolValue(false);

                } else if (this.operator.equals("==")) {

                    if (int1Value == int2Value)
                        return new BoolValue(true);
                    else
                        return new BoolValue(false);

                } else if (this.operator.equals("!=")) {

                    if (int1Value != int2Value)
                        return new BoolValue(true);
                    else
                        return new BoolValue(false);

                } else if (this.operator.equals(">")) {

                    if (int1Value > int2Value)
                        return new BoolValue(true);
                    else
                        return new BoolValue(false);

                } else if (this.operator.equals(">=")) {

                    if (int1Value >= int2Value)
                        return new BoolValue(true);
                    else
                        return new BoolValue(false);

                } else {
                    throw new MyException("RelationalExpressionError: Bad operator.");
                }
            } else {
                throw new MyException("RelationalExpressionError: Expression two not of type int.");
            }
        } else {
            throw new MyException("RelationalExpressionError: Expression one not of type int.");
        }
    }

    @Override
    public Exp copy() {
        return new RelationalExpression(this.expression1.copy(), this.expression2.copy(), operator);
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType type1, type2;
        type1 = this.expression1.typecheck(typeEnvironment);
        type2 = this.expression2.typecheck(typeEnvironment);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())){
                return new BoolType();
            } else {
                throw new MyException("TypeExpressionError: Second operand is not an integer");
            }
        } else {
            throw new MyException("TypeExpressionError: First operand is not an integer");
        }
    }
}
