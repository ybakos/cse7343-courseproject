/*
    ProcessControlBlock.java
    @author Yong Joseph Bakos

    This class represents an operating system PCB.

    TODO
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