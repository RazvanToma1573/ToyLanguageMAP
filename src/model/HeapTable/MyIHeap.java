package model.HeapTable;

import model.MyException;

import java.util.Map;

public interface MyIHeap<F> {

    boolean isDefined(int id);
    F getValue(int id);
    void add(F newValue);
    F lookup (int id) throws MyException;
    F remove (int id) throws MyException;
    int size();
    Map<Integer, F> getContent();
    void setContent(Map<Integer, F> content);
    int getAddrees();
    void update(int id, F newValue) throws MyException;

}
