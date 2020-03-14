package model.HeapTable;

import model.MyException;

import javax.print.attribute.HashDocAttributeSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MyHeap<F> implements MyIHeap<F> {

    private Map<Integer, F> heap = new HashMap<Integer, F>();
    private static AtomicInteger firstFree = new AtomicInteger(1);

    @Override
    public boolean isDefined(int id) {
        return this.heap.containsKey(id);
    }

    @Override
    public F getValue(int id) {
        if (this.isDefined(id))
            return this.heap.get(id);
        else
            return null;
    }

    @Override
    public void add(F newValue) {
        this.heap.put(firstFree.get(), newValue);
        firstFree = new AtomicInteger(firstFree.get()+1);
    }

    @Override
    public void update(int id, F newValue) throws MyException {
        if (!isDefined(id))
            throw new MyException("CollectionError: Id not found!");
        this.heap.put(id, newValue);
    }

    @Override
    public F lookup(int id) throws MyException {
        if(this.heap.isEmpty())
            throw new MyException("CollectionError: Reading from empty collection.");

        if (isDefined(id))
            return this.heap.get(id);
        return null;
    }

    @Override
    public F remove(int id) throws MyException {
        if(this.heap.isEmpty())
            throw new MyException("CollectionError: Reading from empty collection.");

        if (isDefined(id)) {
            return this.heap.remove(id);
        }
        return null;
    }

    @Override
    public int size() {
        return this.heap.size();
    }

    @Override
    public String toString() {
        String result = "";

        for (int key : this.heap.keySet()) {
            result += Integer.toString(key) + "->" + this.heap.get(key).toString();
            result += "\n";
        }

        return result;
    }

    @Override
    public Map<Integer, F> getContent() {
        return this.heap;
    }

    @Override
    public void setContent(Map<Integer, F> content) {
        this.heap = content;
    }

    @Override
    public int getAddrees() {
        return firstFree.get();
    }
}
