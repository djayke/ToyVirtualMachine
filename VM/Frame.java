package VM;

import java.util.HashMap;
import java.util.Map;

public class Frame {
    private final Map<Integer, Integer> variables = new HashMap<>();
    private final int returnAddress;

    public Frame(){
        this.returnAddress = 0;
    }
    public Frame(int returnAddress){
        this.returnAddress = returnAddress;
    }

    public int getVariable(int varNumber) {
        return variables.getOrDefault(varNumber, 0);
    }
    public void setVariable(int varNumber, int value) {
        variables.put(varNumber, value);
    }
    public int getReturnAddress() {
        return returnAddress;
    }
}
