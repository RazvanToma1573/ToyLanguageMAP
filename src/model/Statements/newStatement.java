package model.Statements;

import model.Expressions.Exp;
import model.HeapTable.MyHeap;
import model.HeapTable.MyIHeap;
import model.MyException;
import model.ProgramState;
import model.SymTable.MyDictionary;
import model.SymTable.MyIDictionary;
import model.Types.IType;
import model.Types.RefType;
import model.Values.RefValue;
import model.Values.Value;

public class newStatement implements IStatement {

    private String variableName;
    private Exp expression;

    public newStatement(String variableName, Exp expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "new( " + this.variableName + " , " + this.expression.toString() + " )";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIHeap<Value> heapTable = state.getHeapTable();
        if (symbolTable.isDefined(this.variableName)) {
            Value value = symbolTable.getValue(this.variableName);
            IType type  = value.getType();
            if (type instanceof RefType){
                Value valueExpression = this.expression.eval(symbolTable, heapTable);
                RefType refType = (RefType) type;
                IType typeInner = refType.getInner();
                IType typeExpression = valueExpression.getType();
                if (typeExpression.equals(typeInner)) {
                    int freeAddress = heapTable.getAddrees();
                    heapTable.add(valueExpression);
                    symbolTable.update(this.variableName, new RefValue(freeAddress, typeExpression)); //or typeInner
                    return null;
                } else {
                    throw new MyException("StatementError: Types do not correspond.");
                }
            } else {
                throw new MyException("StatementError: Type of the value assigned to the variable name is not RefType.");
            }
        } else {
            throw new MyException("StatementError: Cannot find the variable name in the symbol table.");
        }
    }

    @Override
    public IStatement copy() {
        return new newStatement(this.variableName, this.expression.copy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVariable = typeEnvironment.lookup(this.variableName);
        IType typeExpression = this.expression.typecheck(typeEnvironment);
        if (typeVariable.equals(new RefType(typeExpression))){
            return typeEnvironment;
        } else {
            throw new MyException("TypeStatementError: NEW statement/ right hand side and left hand side have different types");
        }
    }
}
