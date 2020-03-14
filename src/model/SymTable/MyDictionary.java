package model.SymTable;

import model.MyException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyDictionary<S,V> implements MyIDictionary<S,V> {

    private Map<S,V> dictionary = new HashMap<>();

    @Override
    public boolean isDefined(S id) {
        return dictionary.containsKey(id);
    }

    @Override
    public V getValue(S id) {
        return this.dictionary.get(id);
    }

    @Override
    public void update(S id, V newValue) {
        dictionary.put(id, newValue);
    }

    @Override
    public V lookup(S id) throws MyException {
        if(this.dictionary.isEmpty())
            throw new MyException("CollectionError: Reading from empty map.");
        if (isDefined(id))
            return getValue(id);
        else
            return null;
    }

    @Override
    public String toString() {
        String result = "";

        for (S key: this.dictionary.keySet()) {
            result += key + " : " + this.dictionary.get(key).toString();
            result += "\n";
        }

        return result;

    }

    @Override
    public int size() {
        return this.dictionary.size();
    }

    @Override
    public Map<S, V> getContent() {
        return this.dictionary;
    }
}
