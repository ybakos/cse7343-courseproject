/*
    ProcessQueueView.java
    @author Yong Joseph Bakos

    This class encapsulates the visual representation of a process queue.

*/

package edu.smu.cse7343.bakos.os;

import java.util.*;
import processing.core.*;

public class ProcessQueueView {
    
    private ProcessQueue queue;
    private PApplet p;
    private String title;
    private int x;
    private int y;

    // Visual representations of each running process, each corresponding to a PCB in a queue.
//    HashMap<Integer, ProcessView> processViews;

    public ProcessQueueView(ProcessQueue queue, String title, int x, int y, PApplet p) {
        this.queue = queue;
        this.p = p;
        this.title = title;
        this.x = x;
        this.y = y;
//        processViews = new HashMap<Integer, ProcessView>();
    }

    public void add(ProcessView view) {
//        processViews.put(new Integer(view.pid), view);
    }

    public void draw() {
        p.pushStyle();
        p.pushMatrix();
        p.translate(x, y);
        p.stroke(150);
        p.textSize(32);
        p.text(title, 250, -50);
        p.textSize(18);
        p.text("Head", -50, 5);
        p.line(0, 0, 500, 0);
        float sizeOffset = 0.0f;
        for (ProcessControlBlock pcb : queue.queue) {
            p.fill(Float.floatToRawIntBits(pcb.registers[0]));
            p.ellipse(sizeOffset + (pcb.size() / 2.0f), 0, pcb.size(), pcb.size());
            sizeOffset += pcb.size();
        }
        p.popMatrix();
        p.popStyle();
        for (ProcessControlBlock pcb : queue.queue) {
            p.pushStyle();
            p.pushMatrix();
            p.translate(pcb.registers[5], pcb.registers[6]);
            p.fill(Float.floatToRawIntBits(pcb.registers[0]));
            p.ellipse(0, 0, pcb.size(), pcb.size());
            p.popMatrix();
            p.popStyle();
        }
    }

}