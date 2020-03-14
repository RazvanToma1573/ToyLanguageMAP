package model.Statements;

import model.Expressions.Exp;
import model.FileTable.MyIFileTable;
import model.HeapTable.MyIHeap;
import model.MyException;
import model.ProgramState;
import model.SymTable.MyIDictionary;
import model.Types.IType;
import model.Types.StringType;
import model.Values.IntValue;
import model.Values.StringValue;
import model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class readFileStatement implements IStatement {

    private Exp expression;
    private String variable_name;

    public readFileStatement(Exp expression, String variable_name) {
        this.expression = expression;
        this.variable_name = variable_name;
    }

    @Override
    public String toString() {
        return "readFile( " + this.expression.toString() + ", " + this.variable_name + " )";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIFileTable<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Value> heapTable = state.getHeapTable();
        Value value = symbolTable.lookup(this.variable_name);
        if (value instanceof IntValue) {
            Value value1 = this.expression.eval(symbolTable, heapTable);
            if (value1 instanceof StringValue) {
                StringValue fileName = (StringValue) value1;
                BufferedReader bufferedReader = fileTable.lookup(fileName);
                if (bufferedReader != null) {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null){
                            IntValue intValue = new IntValue(0);
                            symbolTable.update(this.variable_name, intValue);
                        } else {
                            IntValue intValue = new IntValue(Integer.parseInt(line));
                            symbolTable.update(this.variable_name, intValue);
                        }
                    } catch (IOException e) {
                        throw new MyException("StatementError: I/O error.");
                    }
                }
                else {
                    throw new MyException("StatementError: No file found.");
                }
            } else {
                throw new MyException("StatementError: Expression is not a string value.");
            }
        } else {
            throw new MyException("StatementError: Variable name not found or is of different type.");
        }
        return null;
    }

    @Override
    public IStatement copy() {
        return new readFileStatement(this.expression.copy(), this.variable_name);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeExpression = this.expression.typecheck(typeEnvironment);
        if (typeExpression.equals(new StringType())) {
            return typeEnvironment;
        } else {
            throw new MyException("TypeStatementError: READFILE/ first operand must be a string ( the name of the file )");
        }
    }
}
