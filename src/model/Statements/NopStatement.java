package model.Statements;

import model.MyException;
import model.ProgramState;
import model.SymTable.MyIDictionary;
import model.Types.IType;

public class NopStatement implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }

    @Override
    public IStatement copy() {
        return new NopStatement();
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        return typeEnvironment;
    }
}
