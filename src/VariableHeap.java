import model.Values.Value;

public class VariableHeap {

    private Integer address;
    private Value value;

    public VariableHeap(Integer address, Value value) {
        this.address = address;
        this.value = value;
    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
