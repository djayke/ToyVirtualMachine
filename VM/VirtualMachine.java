package VM;

import java.util.ArrayList;
public class VirtualMachine {

    // Operators
    public static final int HALT = 0x00;
    public static final int PUSH = 0x01;
    public static final int POP = 0x02;
    public static final int ADD = 0x03;
    public static final int SUB = 0x04;
    public static final int MUL = 0x05;
    public static final int DIV = 0x06;
    public static final int NOT = 0x07;
    public static final int AND = 0x08;
    public static final int OR = 0x09;
    public static final int DUP = 0x0A;
    public static final int JMP = 0x0B;
    public static final int JIF = 0x0C;
    public static final int LOAD = 0x0D;
    public static final int STORE = 0x0E;
    public static final int EQ = 0x0F;
    public static final int NEQ = 0x10;
    public static final int GEQ = 0x11;
    public static final int LEQ = 0x12;
    public static final int GT = 0x13;
    public static final int LT = 0x14;
    public static final int CALL = 0x15;
    public static final int RET = 0x16;

    // Stack register, state and program
    private final int[] program;
    private int pc;
    private boolean halted;
    private final ArrayList<Integer> stack = new ArrayList<>();
    private final ArrayList<Frame> frames = new ArrayList<>();

    // Constructor
    public VirtualMachine(int[] instr)
    {
        this.program = instr;
        this.pc = 0;
        this.halted = false;
        this.frames.add(new Frame(0));
    }

    public void run() {
        while(!halted)
            step();
    }

    private void step() {
        decode(getNextInstruction());
    }

    private int getNextInstruction() {
        return program[pc++];
    }

    private void decode(int instruction) {
        switch(instruction)
        {
            case HALT : halt(); break;
            case PUSH : push(); break;
            case POP  : pop();  break;
            case ADD  : add();  break;
            case SUB  : sub();  break;
            case MUL  : mul();  break;
            case DIV  : div();  break;
            case NOT  : not();  break;
            case AND  : //and();  break;
            case OR   : //or();   break;
            case DUP  : dup();   break;
            case JMP  : jmp();   break;
            case JIF  : jif();   break;
            case LOAD : load();  break;
            case STORE: store(); break;
            case EQ   : equal(); break;
            case NEQ  : notequal(); break;
            case GEQ  : greatereq(); break;
            case LEQ  : lowereq(); break;
            case LT   : lower(); break;
            case GT   : greater(); break;
            case CALL : call(); break;
            case RET  : ret(); break;
        }
    }

    private void ret(){
        int returnAddress = frames.getLast().getReturnAddress();
        this.frames.removeLast();
        pc = returnAddress;
    }

    private void call() {
        int address = program[pc++];
        this.frames.add(new Frame(pc));
        pc = address;
    }

    private void not() {
        int op = pop();
        if(op==0)
            stack.add(1);
        else
            stack.add(0);
    }

    private void equal() {
        int right = pop();
        int left = pop();
        if(left==right)
            stack.add(1);
        else
            stack.add(0);
    }
    private void notequal() {
        int right = pop();
        int left = pop();
        if(left!=right)
            stack.add(1);
        else
            stack.add(0);
    }
    private void greatereq() {
        int right = pop();
        int left = pop();
        if(left>=right)
            stack.add(1);
        else
            stack.add(0);
    }
    private void lowereq() {
        int right = pop();
        int left = pop();
        if(left<=right)
            stack.add(1);
        else
            stack.add(0);
    }
    private void lower() {
        int right = pop();
        int left = pop();
        if(left<right)
            stack.add(1);
        else
            stack.add(0);
    }
    private void greater() {
        int right = pop();
        int left = pop();
        if(left>right)
            stack.add(1);
        else
            stack.add(0);
    }

    private void store() {
        int varnumber = program[pc++];
        frames.getLast().setVariable(varnumber, pop());
    }

    private void load() {
        int varnumber = program[pc++];
        stack.add(frames.getLast().getVariable(varnumber));
    }

    private void jif() {
        int peek = pop();
        if (peek != 0)
            pc = program[pc];
    }

    private void jmp() {
        pc = program[pc];
    }

    private void halt() {
        halted = true;
        pc = program.length;
    }

    private void push() {
        int operand = program[pc++];
        stack.add(operand);
    }

    private int pop() {
        int operand = stack.getLast();
        stack.removeLast();
        return operand;
    }

    private void add() {
        int op1 = pop();
        int op2 = pop();
        int res = op2 + op1;
        stack.add(res);
    }


    private void div() {
        int op1 = pop();
        int op2 = pop();
        int res = op2 / op1;
        stack.add(res);
    }

    private void mul() {
        int op1 = pop();
        int op2 = pop();
        int res = op2 * op1;
        stack.add(res);
    }

    private void sub() {
        int op1 = pop();
        int op2 = pop();
        int res = op2 - op1;
        stack.add(res);
    }

    private void dup() {
        int peek = stack.getLast();
        stack.add(peek);
    }


}
