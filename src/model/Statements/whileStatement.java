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

import java.util.List;

public class whileStatement implements IStatement {

    private Exp expression;
    private IStatement statement;

    public whileStatement(Exp expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public String toString() {
        return "(" + "while( " + this.expression + " )" +  this.statement.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> exeStack = state.getStack();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIHeap<Value> heapTable = state.getHeapTable();
        Value value = this.expression.eval(symbolTable, heapTable);
        if (value instanceof BoolValue) {
            BoolValue boolValue = (BoolValue) value;
            if (boolValue.getValue()) {
                exeStack.push(this);
                exeStack.push(this.statement);
            } else {

            }
            return null;
        } else {
            throw new MyException("StatementError: Condition not evaluated to boolean value.");
        }
    }

    @Override
    public IStatement copy() {
        return new whileStatement(this.expression.copy(), this.statement.copy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeExpression = this.expression.typecheck(typeEnvironment);
        if (typeExpression.equals(new BoolType())){
            MyIDictionary<String, IType> newTypeEnvironment = new MyDictionary<>();
            for (String id : typeEnvironment.getContent().keySet()) {
                newTypeEnvironment.update(id, typeEnvironment.getValue(id).copy());
            }
            this.statement.typecheck(newTypeEnvironment);
            return typeEnvironment;
        } else {
            throw new MyException("The condition of WHILE does not have the type bool");
        }
    }
}
