/*
    OperatingSystem.java
    @author Yong Joseph Bakos

    TODO
*/

package edu.smu.cse7343.bakos.os;

public class OperatingSystem {

    public static final int ROUND_ROBIN_CYCLE_LIMIT = 30;
    private static final int FAUX_INITIAL_USERSPACE_PID = 10;

    public int nextPid = FAUX_INITIAL_USERSPACE_PID;
    public ProcessQueue readyQueue;
    public ProcessQueue waitQueue;
    public ProcessControlBlock currentProcess;

    public OperatingSystem() {
        readyQueue = new ProcessQueue(ProcessState.READY);
        waitQueue = new ProcessQueue(ProcessState.WAITING);
        currentProcess = null; // TODO: necessary after new CPU integration?
    }

    public void createNewProcess() {

    }

    public void blockCurrentProcess() {

    }

}