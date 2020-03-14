package model.Statements;

import model.ExeStack.MyIStack;
import model.MyException;
import model.ProgramState;
import model.SymTable.MyIDictionary;
import model.Types.IType;

public class CompoundStatement implements IStatement {

    private IStatement first;
    private IStatement second;

    public CompoundStatement(IStatement firstStatement, IStatement secondStatement) {
        this.first = firstStatement;
        this.second = secondStatement;
    }


    @Override
    public String toString() {
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public IStatement copy() {
        return new CompoundStatement(first.copy(),second.copy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        //MyIDictionary<String, IType> typeEnvironment1 = this.first.typecheck(typeEnvironment);
        //MyIDictionary<String, IType> typeEnvironment2 = this.second.typecheck(typeEnvironment1);
        //return typeEnvironment2
        return this.second.typecheck(this.first.typecheck(typeEnvironment));
    }
}
