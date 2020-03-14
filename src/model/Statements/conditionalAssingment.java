package model.Statements;

import model.ExeStack.MyIStack;
import model.Expressions.Exp;
import model.HeapTable.MyIHeap;
import model.MyException;
import model.ProgramState;
import model.SymTable.MyIDictionary;
import model.Types.BoolType;
import model.Types.IType;
import model.Values.Value;

public class conditionalAssingment implements IStatement {

    private String var;
    private Exp expression1;
    private Exp expression2;
    private Exp expression3;

    public conditionalAssingment(String var, Exp expression1, Exp expression2, Exp expression3) {
        this.var = var;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
    }

    @Override
    public String toString() {
        return var + "=" + expression1.toString() + "?" + expression2.toString() + ":" + expression3.toString();
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> exeStack = state.getStack();
        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        MyIHeap<Value> heapTable = state.getHeapTable();
        Value v = this.expression1.eval(symTable, heapTable);
        IType v1 = v.getType();
        if (v1 instanceof BoolType) {
            Value a = this.expression2.eval(symTable,heapTable);
            IType a1 = a.getType();
            Value b = this.expression3.eval(symTable,heapTable);
            IType b1 = b.getType();
            Value c = symTable.lookup(this.var);
            IType c1 = c.getType();
            if (a1.equals(c1) && b1.equals(c1)) {
                exeStack.push(new IfStatement(
                        this.expression1,
                        new AssignmentStatement("v", this.expression2),
                        new AssignmentStatement("v", this.expression3)
                ));
            }
        }
        return null;
    }

    @Override
    public IStatement copy() {
        return new conditionalAssingment(var, this.expression1.copy(), this.expression2.copy(), this.expression3.copy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType type1 = this.expression1.typecheck(typeEnvironment);
        if (type1 instanceof BoolType) {
            IType typeVar = typeEnvironment.lookup("v");
            IType type2 = this.expression2.typecheck(typeEnvironment);
            IType type3 = this.expression3.typecheck(typeEnvironment);
            if (typeVar.equals(type2) && typeVar.equals(type3)) {
                return typeEnvironment;
            } else {
                throw new MyException("Types of the variable and the expressions do not Match");
            }
        } else {
            throw new MyException("Expression one does not have type bool.");
        }
    }
}
