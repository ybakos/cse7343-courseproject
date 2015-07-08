/*
    Simulator.java
    @author Yong Joseph Bakos

    This class represents an abstract main entry point of the simulation, and
    prepares all of the necessary structures and instances. Processing's `PApplet.main`
    function will call `setup`, and then call `draw` over and over again.

*/

package edu.smu.cse7343.bakos.os;

import java.util.*;

public class ProcessQueue {
    
    private LinkedList<ProcessControlBlock> queue = new LinkedList<ProcessControlBlock>();

    public ProcessQueue() {
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void add(ProcessControlBlock pcb) {
        pcb.state = ProcessState.READY;
        queue.add(pcb);
    }

    public ProcessControlBlock remove() {
        return queue.remove();
    }

}