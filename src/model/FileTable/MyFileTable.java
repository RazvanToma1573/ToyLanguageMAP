package model.FileTable;

import model.MyException;

import java.util.HashMap;
import java.util.Map;

public class MyFileTable<P, F> implements MyIFileTable<P, F>{

    private Map<P, F> dictionary = new HashMap<P, F>();


    @Override
    public boolean isDefined(P id) {
        return this.dictionary.containsKey(id);
    }

    @Override
    public F getValue(P id) {
        if (isDefined(id)){
            return this.dictionary.get(id);
        }
        return null;
    }

    @Override
    public void update(P id, F newValue) {
        this.dictionary.put(id, newValue);
    }

    @Override
    public F lookup(P id) throws MyException {
        if (this.dictionary.isEmpty()) {
            throw new MyException("CollectionError: Reading from empty map.");
        }
        if (isDefined(id)){
            return this.dictionary.get(id);
        } else {
            return null;
        }
    }

    @Override
    public F remove(P id) throws MyException {
        if (this.dictionary.isEmpty()){
            throw new MyException("CollectionError: Reading from empty map.");
        }
        if (isDefined(id)) {
            return this.dictionary.remove(id);
        }
        return null;
    }

    @Override
    public int size() {
        return this.dictionary.size();
    }

    @Override
    public String toString() {
        String result = "";

        for (P key: this.dictionary.keySet()) {
            result += key + " : " + this.dictionary.get(key).toString();
            result += "\n";
        }

        return result;

    }

    @Override
    public Map<P, F> getContent() {
        return this.dictionary;
    }
}
