import VM.VirtualMachine;
import static VM.VirtualMachine.*;

public class Main {
    public static void main(String[] args){
        VirtualMachine vm = new VirtualMachine(new int[]
                {
                        PUSH,  6,
                        PUSH,  4,
                        CALL, 7,
                        HALT,

                        STORE, 1,
                        STORE, 0,
                        LOAD, 0,
                        LOAD, 1,
                        GEQ,
                        JIF, 21,
                        LOAD, 1,
                        RET,

                        LOAD, 0,
                        RET
                }
        );
        vm.run();
    }
}