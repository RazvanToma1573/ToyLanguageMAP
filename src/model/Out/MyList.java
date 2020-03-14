package model.Out;

import model.Values.Value;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MyList<T> implements MyIList<T> {

    List<T> outList = new LinkedList<>();

    @Override
    public void add(T a) {
        outList.add(a);
    }

    @Override
    public String toString() {
        String result = "";

        Iterator<T> iter = this.outList.iterator();

        while(iter.hasNext()) {
            result += iter.next().toString();
            result += "\n";
        }

        return result;
    }

    @Override
    public List<T> getOutList() {
        return  this.outList;
    }

    @Override
    public int size() {
        return this.outList.size();
    }
}
