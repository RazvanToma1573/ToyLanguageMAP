package model.Statements;

import model.ExeStack.MyIStack;
import model.ExeStack.MyStack;
import model.FileTable.MyFileTable;
import model.FileTable.MyIFileTable;
import model.HeapTable.MyHeap;
import model.HeapTable.MyIHeap;
import model.MyException;
import model.Out.MyIList;
import model.Out.MyList;
import model.ProgramState;
import model.SymTable.MyDictionary;
import model.SymTable.MyIDictionary;
import model.Types.IType;
import model.Values.StringValue;
import model.Values.Value;

import java.io.BufferedReader;

public class forkStatement implements IStatement {

    private IStatement statement;

    public forkStatement(IStatement stmt) {
        this.statement = stmt;
    }

    @Override
    public String toString() {
        return "fork(" + this.statement.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> newStack = new MyStack<>();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIDictionary<String, Value> newSymbolTable = new MyDictionary<>();
        //clone symbolTable
        for (String key : symbolTable.getContent().keySet()) {
            newSymbolTable.update(key, symbolTable.getValue(key).copy());
        }
        MyIList<Value> newOutList = state.getOut();
        MyIFileTable<StringValue, BufferedReader> newFileTable =  state.getFileTable();
        MyIHeap<Value> newHeap = state.getHeapTable();
        return new ProgramState(newStack, newSymbolTable, newOutList, newFileTable, newHeap, this.statement);
    }

    @Override
    public IStatement copy() {
        return new forkStatement(this.statement.copy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        MyIDictionary<String, IType> newTypeEnvironment = new MyDictionary<>();
        for (String id : typeEnvironment.getContent().keySet()){
            newTypeEnvironment.update(id,typeEnvironment.getValue(id).copy());
        }
        this.statement.typecheck(newTypeEnvironment);
        return typeEnvironment;
    }
}
