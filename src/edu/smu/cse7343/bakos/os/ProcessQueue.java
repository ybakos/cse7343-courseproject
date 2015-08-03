/*
    ProcessQueue.java
    @author Yong Joseph Bakos

    This class represents a process queue, and is composed with an internal LinkedList,
    which Java implements as a doubly-linked list.
*/

package edu.smu.cse7343.bakos.os;

import java.util.*;
import processing.core.*;

public class ProcessQueue {
    
    public LinkedList<ProcessControlBlock> queue;
    private ProcessState managedState;

    public ProcessQueue(ProcessState managedState) {
        queue = new LinkedList<ProcessControlBlock>();
        this.managedState = managedState;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void add(ProcessControlBlock pcb) {
        pcb.state = managedState;
        queue.add(pcb);
    }

    public ProcessControlBlock remove() {
        return queue.remove();
    }

    public boolean remove(ProcessControlBlock pcb) {
        return queue.remove(pcb);
    }

    public ProcessControlBlock peek() {
        return queue.peek();
    }

    public ProcessControlBlock remove(int index) {
        return queue.remove(index);
    }

    public void add(int index, ProcessControlBlock pcb) {
        queue.add(index, pcb);
    }

}