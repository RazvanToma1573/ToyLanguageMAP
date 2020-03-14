package model.SymTable;

import model.MyException;

import java.util.Map;

public interface MyIDictionary<S,V>  {

    boolean isDefined(S id);
    V getValue(S id);
    void update(S id, V newValue);
    V lookup (S id) throws MyException;
    int size();
    Map<S,V> getContent();
}
