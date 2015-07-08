/*
    ProcessControlBlock.java
    @author Yong Joseph Bakos

    This class represents an operating system PCB.

    TODO
*/

package edu.smu.cse7343.bakos.os;

public class ProcessControlBlock {

    public int pid;
    public ProcessState state;

    public ProcessControlBlock(int pid) {
        this.pid = pid;
        state = ProcessState.NEW;
    }
    
}