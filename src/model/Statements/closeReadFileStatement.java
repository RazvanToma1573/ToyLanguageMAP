package model.Statements;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.management.BufferPoolMXBean;

public class closeReadFileStatement implements IStatement {

    private Exp expression;

    public closeReadFileStatement(Exp expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "closeReadFileStatement( " + this.expression.toString() + " )";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIFileTable<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Value> heapTable = state.getHeapTable();
        Value value = this.expression.eval(symbolTable, heapTable);
        if (value instanceof StringValue) {
            StringValue stringValue = (StringValue) value;
            BufferedReader bufferedReader = fileTable.lookup(stringValue);
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                    fileTable.remove(stringValue);
                } catch (IOException e) {
                    throw new MyException("StatementError: I/O error.");
                }
            } else {
                throw new MyException("StatementError: No file found.");
            }
        } else {
            throw new MyException("StatementError: Expression type is not StringValue.");
        }
        return null;
    }

    @Override
    public IStatement copy() {
        return new closeReadFileStatement(this.expression.copy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType type = this.expression.typecheck(typeEnvironment);
        if (type.equals(new StringType())){
            return typeEnvironment;
        } else {
            throw new MyException("TypeStatementError: operand does not have the type string");
        }
    }
}
