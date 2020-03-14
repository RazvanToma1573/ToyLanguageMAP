package model.Statements;

import model.ExeStack.MyIStack;
import model.Expressions.Exp;
import model.HeapTable.MyIHeap;
import model.MyException;
import model.ProgramState;
import model.SymTable.MyDictionary;
import model.SymTable.MyIDictionary;
import model.Types.BoolType;
import model.Types.IType;
import model.Values.BoolValue;
import model.Values.Value;

public class IfStatement implements IStatement {
    private Exp expression;
    private IStatement thenStatement;
    private IStatement elseStatement;

    public IfStatement(Exp exp, IStatement thenS, IStatement elseS) {
        this.expression = exp;
        this.thenStatement = thenS;
        this.elseStatement = elseS;
    }


    @Override
    public String toString() {
        return "IF(" + this.expression.toString() + ") THEN(" + this.thenStatement.toString() + ")ELSE(" + this.elseStatement.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> exeStack = state.getStack();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIHeap<Value> heapTable = state.getHeapTable();
        Value conditionValue = expression.eval(symbolTable, heapTable);
        if (conditionValue instanceof BoolValue) {
            BoolValue boolConditionValue = (BoolValue)conditionValue;
            boolean boolValue = boolConditionValue.getValue();
            if (boolValue) {
                exeStack.push(thenStatement);
            } else {
                exeStack.push(elseStatement);
            }
        } else {
            throw new MyException("StatementError: The type of the condition is not boolean.");
        }
        return null;
    }

    @Override
    public IStatement copy() {
        return new IfStatement(expression.copy(),thenStatement.copy(),elseStatement.copy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeExpression = this.expression.typecheck(typeEnvironment);
        if (typeExpression.equals(new BoolType())){
            MyIDictionary<String, IType> newTypeEnvironment1 = new MyDictionary<>();
            MyIDictionary<String, IType> newTypeEnvironment2 = new MyDictionary<>();
            for (String id : typeEnvironment.getContent().keySet()) {
                newTypeEnvironment1.update(id,typeEnvironment.getValue(id).copy());
                newTypeEnvironment2.update(id,typeEnvironment.getValue(id).copy());
            }
            this.thenStatement.typecheck(newTypeEnvironment1);
            this.elseStatement.typecheck(newTypeEnvironment2);
            return typeEnvironment;
        } else {
            throw new MyException("TypeStatementError: The condition of IF has not the type bool");
        }
    }
}
