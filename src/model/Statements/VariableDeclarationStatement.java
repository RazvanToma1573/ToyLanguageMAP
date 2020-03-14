package model.Statements;

import model.MyException;
import model.ProgramState;
import model.SymTable.MyIDictionary;
import model.Types.BoolType;
import model.Types.IType;
import model.Types.IntType;
import model.Values.BoolValue;
import model.Values.IntValue;
import model.Values.Value;

public class VariableDeclarationStatement implements IStatement {

    private String name;
    private IType type;

    public VariableDeclarationStatement(String name, IType type) {
        this.name = name;
        this.type = type;
    }


    @Override
    public String toString() {
        return this.type.toString() + " " + this.name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        symbolTable.update(this.name, this.type.defaultValue());
        return null;
    }

    @Override
    public IStatement copy() {
        String copyName = this.name;
        return new VariableDeclarationStatement(copyName,this.type.copy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        typeEnvironment.update(this.name, this.type);
        return typeEnvironment;
    }
}
