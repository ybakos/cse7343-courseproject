/*
    OperatingSystem.java
    @author Yong Joseph Bakos

    TODO
*/

package edu.smu.cse7343.bakos.os;

public class OperatingSystem {

    public final static int ROUND_ROBIN_CYCLE_LIMIT = 30;

    public int nextPid = 10;
    public ProcessQueue readyQueue;
    public ProcessQueue waitQueue;
    public ProcessControlBlock currentProcess;

    public OperatingSystem(int x, int y) {
        readyQueue = new ProcessQueue(ProcessState.READY, "Ready Queue", x, y - 225);
        waitQueue = new ProcessQueue(ProcessState.WAITING, "Wait Queue", x, y - 50);
    }

}