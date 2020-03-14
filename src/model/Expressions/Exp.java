package model.Expressions;


import model.HeapTable.MyIHeap;
import model.MyException;
import model.SymTable.MyIDictionary;
import model.Types.IType;
import model.Values.Value;

public interface Exp {

    Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> hpTable) throws MyException;
    Exp copy();
    IType typecheck(MyIDictionary<String, IType> typeEnvironment) throws MyException;
}
