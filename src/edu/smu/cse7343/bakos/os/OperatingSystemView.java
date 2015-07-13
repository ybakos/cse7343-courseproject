/*
    OperatingSystemView.java
    @author Yong Joseph Bakos

    TODO
*/

package edu.smu.cse7343.bakos.os;

import processing.core.*;

public class OperatingSystemView {

    private PApplet p;
    private OperatingSystem os;
    private ProcessQueueView readyQueueView;
    private ProcessQueueView waitQueueView;

    public OperatingSystemView(OperatingSystem os, int x, int y, PApplet p) {
        this.os = os;
        waitQueueView = new ProcessQueueView("Wait Queue", x, y - 50);
        readyQueueView = new ProcessQueueView("Ready Queue", x, y - 225);
        this.p = p;
    }

    public void draw() {
        waitQueueView.draw(p, os.waitQueue);
        readyQueueView.draw(p, os.readyQueue);
    }

    public void createNewProcessView(ProcessControlBlock pcb) {
        readyQueueView.add(new ProcessView(p, pcb));
    }

    public void blockProcess(ProcessControlBlock pcb) {
        // TODO
    }

}