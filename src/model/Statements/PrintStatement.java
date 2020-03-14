package model.Statements;

import model.Expressions.Exp;
import model.HeapTable.MyIHeap;
import model.MyException;
import model.Out.MyIList;
import model.ProgramState;
import model.SymTable.MyIDictionary;
import model.Types.IType;
import model.Values.Value;

public class PrintStatement implements IStatement {

    private Exp expression;

    public PrintStatement(Exp exp) {
        this.expression = exp;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIHeap<Value> heapTable = state.getHeapTable();
        MyIList<Value> outList = state.getOut();
        Value value = this.expression.eval(symbolTable, heapTable);
        outList.add(value);
        return null;
    }

    @Override
    public IStatement copy() {
        return new PrintStatement(this.expression.copy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        this.expression.typecheck(typeEnvironment);
        return typeEnvironment;
    }
}
