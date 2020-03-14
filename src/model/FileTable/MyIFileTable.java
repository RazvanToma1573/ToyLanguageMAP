package model.FileTable;

import model.MyException;

import java.util.Map;

public interface MyIFileTable<P, F> {

    boolean isDefined(P id);
    F getValue(P id);
    void update(P id, F newValue);
    F lookup (P id) throws MyException;
    F remove (P id) throws MyException;
    int size();
    Map<P, F> getContent();

}
