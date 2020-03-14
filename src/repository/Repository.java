package repository;

import model.ExeStack.MyIStack;
import model.FileTable.MyIFileTable;
import model.HeapTable.MyIHeap;
import model.MyException;
import model.Out.MyIList;
import model.ProgramState;
import model.Statements.IStatement;
import model.SymTable.MyIDictionary;
import model.Values.StringValue;
import model.Values.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Repository implements IRepository {

    private List<ProgramState> programs = new LinkedList<>();
    private String logFilePath;

    public Repository(ProgramState program, String logFilePath) {
        this.programs.add(program);
        this.logFilePath = logFilePath;
    }

    public void addProgram(ProgramState program) {
        this.programs.add(program);
    }

    @Override
    public List<ProgramState> getProgramList() {
        return this.programs;
    }

    @Override
    public void setProgramList(List<ProgramState> pr) {
        this.programs = pr;
    }


    @Override
    public void logProgramStateExecution(ProgramState programState) {
        ProgramState currentProgram = programState;
        MyIStack<IStatement> ExeStack = currentProgram.getStack();
        MyIDictionary<String, Value> SymTable = currentProgram.getSymbolTable();
        MyIList<Value> OutList = currentProgram.getOut();
        MyIFileTable<StringValue, BufferedReader> FileTable = currentProgram.getFileTable();
        MyIHeap<Value> HeapTable = currentProgram.getHeapTable();
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
            logFile.println("ExeStack:");
            logFile.println(ExeStack.toString());
            logFile.println("SymTable:");
            logFile.println(SymTable.toString());
            logFile.println("Out:");
            logFile.println(OutList.toString());
            logFile.println("FileTable:");
            logFile.println(FileTable.toString());
            logFile.println("Heap:");
            logFile.println(HeapTable.toString());
            logFile.println("Id:");
            logFile.println(Integer.toString(currentProgram.getProgramId()));
            logFile.println("------------------");
            logFile.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
