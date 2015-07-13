/*
    ProcessQueueView.java
    @author Yong Joseph Bakos

    This class encapsulates the visual representation of a process queue.

*/

package edu.smu.cse7343.bakos.os;

import java.util.*;
import processing.core.*;

public class ProcessQueueView {
    
    private String title;
    private int x;
    private int y;

    // Visual representations of each running process, each corresponding to a PCB in a queue.
    HashMap<Integer, ProcessView> processViews;

    public ProcessQueueView(String title, int x, int y) {
        this.title = title;
        this.x = x;
        this.y = y;
        processViews = new HashMap<Integer, ProcessView>();
    }

    public void add(ProcessView view) {
        processViews.put(new Integer(view.pid), view);
    }

    public void draw(PApplet p, ProcessQueue queue) {
        for (ProcessView view : processViews.values()) {
            view.draw();
        }
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
            ProcessView view = processViews.get(new Integer(pcb.pid));
            view.draw();
            p.fill(view.color);
            p.ellipse(sizeOffset + (view.size / 2.0f), 0, view.size, view.size);
            sizeOffset += (view.size / 2.0f);
        }
        p.popMatrix();
        p.popStyle();
    }

}