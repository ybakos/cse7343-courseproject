/*
    ProcessControlBlock.java
    @author Yong Joseph Bakos

    This class represents an operating system PCB.
    TODO
*/

package edu.smu.cse7343.bakos.os;

import processing.core.*;

public class ProcessControlBlock {

    private int parentPid; // Ignoring this in simulator,
    private int userId;    // just providing for consistency
    private int groupId;   // and understanding.

    private int priority;    // TODO
    private int lastCycle;   // The last time this pcb was executed
    private int waitEventId; // Resource of id waiting for when in WAIT
    private int cycleCount;  // Amount of CPU time accumulated
    
    public int pid;
    public ProcessState state;
    public int programCounter;
    public int[] registers;

    public ProcessControlBlock(int pid) {
        this.pid = pid;
        state = ProcessState.NEW;
        programCounter = 0;
        registers = new int[CPU.NUMBER_OF_REGISTERS];
    }

}