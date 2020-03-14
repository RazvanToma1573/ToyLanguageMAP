package model;

import model.ExeStack.MyIStack;
import model.FileTable.MyIFileTable;
import model.HeapTable.MyIHeap;
import model.Out.MyIList;
import model.Statements.*;
import model.SymTable.MyIDictionary;
import model.Values.StringValue;
import model.Values.Value;

import java.io.BufferedReader;
import java.io.FileDescriptor;

public class ProgramState {

    private static int id;
    private int programId;
    private MyIStack<IStatement> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private MyIFileTable<StringValue, BufferedReader> fileTable;
    private MyIHeap<Value> heapTable;

    private IStatement originalProgram; //optional field

    public ProgramState(MyIStack<IStatement> stk, MyIDictionary<String, Value> symTbl, MyIList<Value> ot, MyIFileTable<StringValue, BufferedReader> fileTbl, MyIHeap<Value> heapTbl, IStatement prg) {
        this.exeStack = stk;
        this.symTable = symTbl;
        this.out = ot;
        this.fileTable = fileTbl;
        this.heapTable = heapTbl;
        this.originalProgram = prg.copy();
        this.exeStack.push(prg);
        ProgramState.id++;
        this.programId = ProgramState.id;
    }

    public static void initializeId() {
        ProgramState.id = 0;
    }

    public int getProgramId() {
        return this.programId;
    }

    public boolean isNotCompleted() {
        if (!this.exeStack.isEmpty())
            return true;
        return false;
    }

    public ProgramState oneStep() throws MyException{
        if (this.exeStack.isEmpty())
            throw new MyException("CollectionError:Program state stack is empty.");
        IStatement statement = this.exeStack.pop();
        return statement.execute(this);
    }


    public MyIStack<IStatement> getStack() {
        return exeStack;
    }

    public MyIDictionary<String, Value> getSymbolTable() {
        return symTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public MyIFileTable<StringValue, BufferedReader> getFileTable() { return fileTable; }

    public MyIHeap<Value> getHeapTable() { return heapTable; }

    @Override
    public String toString() {
        return  originalProgram.toString();
    }
}
