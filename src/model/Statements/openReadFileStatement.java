package model.Statements;

import model.ExeStack.MyIStack;
import model.Expressions.Exp;
import model.FileTable.MyIFileTable;
import model.HeapTable.MyIHeap;
import model.MyException;
import model.ProgramState;
import model.SymTable.MyIDictionary;
import model.Types.IType;
import model.Types.StringType;
import model.Values.StringValue;
import model.Values.Value;

import java.io.*;

public class openReadFileStatement implements IStatement{

    private Exp expression;

    public openReadFileStatement(Exp expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "openReadFile( " + this.expression.toString() + " )";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIFileTable<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Value> heapTable = state.getHeapTable();
        Value value = this.expression.eval(symbolTable, heapTable);
        if (value.getType().equals(new StringType())) {
            StringValue stringValue = (StringValue) value;
            if (!fileTable.isDefined(stringValue)) {
                try {
                    BufferedReader fileR = new BufferedReader(new FileReader(stringValue.getValue()));
                    fileTable.update(stringValue, fileR);
                } catch (IOException e) {
                    throw new MyException("StatementError: I/O error.");
                }
            }
            else{
                throw new MyException("StatementError: StringValue already a key in the file table.");
            }
        }
        else {
            throw new MyException("StatementError: Expression not of type StringType.");
        }
        return null;
    }

    @Override
    public IStatement copy() {
        return new openReadFileStatement(this.expression.copy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType type = this.expression.typecheck(typeEnvironment);
        if (type.equals(new StringType())){
            return typeEnvironment;
        } else {
            throw new MyException("TypeStatementError: operand does not have type string");
        }
    }
}
