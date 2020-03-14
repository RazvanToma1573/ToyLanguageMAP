package model.Statements;


import model.ExeStack.MyIStack;
import model.Expressions.Exp;
import model.HeapTable.MyIHeap;
import model.MyException;
import model.ProgramState;
import model.SymTable.MyIDictionary;
import model.Types.IType;
import model.Values.Value;

public class AssignmentStatement implements  IStatement {

    private String id;
    private Exp expression;

    public AssignmentStatement(String id,Exp exp) {
        this.id = id;
        this.expression = exp;
    }


    @Override
    public String toString() {
        return this.id + "=" + this.expression.toString();
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getStack();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIHeap<Value> heapTable = state.getHeapTable();
        Value value = this.expression.eval(symbolTable, heapTable);
        if(symbolTable.isDefined(this.id)) {
            IType typeId = (symbolTable.getValue(this.id)).getType();
            if ((value.getType()).equals(typeId))
                symbolTable.update(this.id, value);
            else
                throw new MyException("StatementError: Declared type of variable " + this.id + " and type pf the assigned expression do not match.");
        }
        else
            throw new MyException("StatementError: The used variable " + id + " was not declared before.");

        return null;
    }

    @Override
    public IStatement copy() {
        String copyId = this.id;
        return new AssignmentStatement(copyId,this.expression.copy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVariable = typeEnvironment.lookup(this.id);
        IType typeExpression = this.expression.typecheck(typeEnvironment);
        if (typeVariable.equals(typeExpression)){
            return typeEnvironment;
        } else {
            throw new MyException("TypeStatementError: Right hand side and left hand side have different types");
        }
    }
}
