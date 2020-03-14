package model.Statements;

import model.Expressions.Exp;
import model.HeapTable.MyIHeap;
import model.MyException;
import model.ProgramState;
import model.SymTable.MyIDictionary;
import model.Types.IType;
import model.Types.RefType;
import model.Values.RefValue;
import model.Values.Value;

public class wHStatement implements IStatement {

    private String variableName;
    private Exp expression;

    public wHStatement(String variableName, Exp expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "wH(" + this.variableName + " , " + this.expression + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIHeap<Value> heapTable = state.getHeapTable();
        if (symbolTable.isDefined(this.variableName)) {
            Value value = symbolTable.getValue(this.variableName);
            IType type = value.getType();
            if (type instanceof RefType) {
                RefValue refValue = (RefValue) value;
                int address = refValue.getAddress();
                if (heapTable.isDefined(address)) {
                    Value valueExpression = this.expression.eval(symbolTable, heapTable);
                    IType typeExpression = valueExpression.getType();
                    IType typeRef = refValue.getType();
                    IType typeLocation = ((RefType) typeRef).getInner();
                    if (typeLocation.equals(typeExpression)) {
                        heapTable.update(address, valueExpression);
                        return null;
                    } else {
                        throw new MyException("StatementError: Types of the location and the this.expression's value do not correspond.");
                    }
                } else {
                    throw new MyException("StatementError: Address not defined in the heap table.");
                }
            } else {
                throw new MyException("StatementError: Type of value from symbol table is not RefType");
            }
        } else {
            throw new MyException("StatementError: Value not in symbol table.");
        }
    }

    @Override
    public IStatement copy() {
        return new wHStatement(this.variableName, this.expression.copy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVariable = typeEnvironment.lookup(this.variableName);
        IType typeExpression = this.expression.typecheck(typeEnvironment);
        if (typeVariable instanceof RefType) {
            RefType type = (RefType) typeVariable;
            if (typeExpression.equals(type.getInner())) {
                return typeEnvironment;
            } else {
                throw new MyException("TypeStatementError: WH/ right hand side does not have the same type as left hand side");
            }
        } else {
            throw new MyException("TypeStatementError: WH/ not reference type");
        }
    }
}
