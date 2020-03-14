package model.ExeStack;


import model.MyException;
import model.Statements.IStatement;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class MyStack<T> implements MyIStack<T> {

    Stack<T> stack = new Stack<>();

    @Override
    public T pop() throws MyException {
        if (isEmpty())
            throw new MyException("CollectionError: Reading from an empty stack.");
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        /*String result = "";

        Iterator<T> iter = this.stack.iterator();

        while (iter.hasNext()){
            result += iter.next().toString();
            result += "\n";
        }*/

        List<String> elements = this.stack.stream().map(element -> element.toString()).collect(Collectors.toList());

        Collections.reverse(elements);

        String result = "";

        for (String element : elements) {
            result += element;
            result += "\n";
        }

        return result;
    }

    @Override
    public int size() {
        return this.stack.size();
    }

    @Override
    public Stack<T> getContent() {
        return this.stack;
    }
}
