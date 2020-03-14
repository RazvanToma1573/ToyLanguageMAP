package model.ExeStack;

import model.MyException;

import java.util.Stack;

public interface MyIStack<T> {

    T pop() throws MyException;
    void push(T v);

    boolean isEmpty();
    int size();
    Stack<T> getContent();
}
