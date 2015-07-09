/*
    Simulator.java
    @author Yong Joseph Bakos

    This class represents an abstract main entry point of the simulation, and
    prepares all of the necessary structures and instances. Processing's `PApplet.main`
    function will call `setup`, and then call `draw` over and over again.

*/

package edu.smu.cse7343.bakos.os;

import java.util.*;
import processing.core.*;

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

    public void draw(HashMap<Integer, ProcessView> processViews, PApplet p) {
        p.pushStyle();
        p.pushMatrix();
        p.stroke(150);
        p.translate(200, p.height - 200);
        p.textSize(32);
        p.text("Ready Queue", 250, -100);
        p.textSize(18);
        p.text("Head", -50, 5);
        p.line(0, 0, 500, 0);
        int sizeOffset = 0;
        for (ProcessControlBlock pcb : queue) {
            ProcessView view = processViews.get(new Integer(pcb.pid));
            p.fill(view.color);
            p.ellipse(sizeOffset, 0, view.size, view.size);
            sizeOffset += view.size;
        }
        p.popMatrix();
        p.popStyle();
    }

}