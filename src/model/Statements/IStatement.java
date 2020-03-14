package model.Statements;

import model.MyException;
import model.ProgramState;
import model.SymTable.MyIDictionary;
import model.Types.IType;

public interface IStatement {

    ProgramState execute(ProgramState state) throws MyException;
    IStatement copy();
    MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnvironment) throws MyException;
}
