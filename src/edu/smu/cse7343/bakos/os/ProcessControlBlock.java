/*
    ProcessControlBlock.java
    @author Yong Joseph Bakos

    This class represents an operating system PCB.

    For now, the important attributes are pid and state.

    The programCounter and registers are declared here to reflect parts of a real
    PCB, but are not yet used (TODO: Phase 2).
*/

package edu.smu.cse7343.bakos.os;

public class ProcessControlBlock {

    private static final int NUMBER_OF_REGISTERS = 10;

    public int pid;
    public ProcessState state;
    public int programCounter;
    public int[] registers;

    public ProcessControlBlock(int pid) {
        this.pid = pid;
        state = ProcessState.NEW;
        programCounter = 0;
        registers = new int[NUMBER_OF_REGISTERS];
    }
    
}