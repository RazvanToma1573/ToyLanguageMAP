package model.Out;

import model.Values.Value;

import java.util.List;

public interface MyIList<T> {

    void add(T a);
    int size();
    List<T> getOutList();
}
