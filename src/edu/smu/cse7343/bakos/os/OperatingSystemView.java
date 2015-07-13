/*
    OperatingSystemView.java
    @author Yong Joseph Bakos

    TODO
*/

package edu.smu.cse7343.bakos.os;

import processing.core.*;

public class OperatingSystemView {

    private ProcessQueueView readyQueueView;
    private ProcessQueueView waitQueueView;

    public OperatingSystemView(int x, int y) {
        waitQueueView = new ProcessQueueView("Wait Queue", x, y - 50);
        readyQueueView = new ProcessQueueView("Ready Queue", x, y - 225);
    }

    public void draw(PApplet p, OperatingSystem os) {
        waitQueueView.draw(p, os.waitQueue);
        readyQueueView.draw(p, os.readyQueue);
    }

    public void createNewProcessView(PApplet p, ProcessControlBlock pcb) {
        readyQueueView.add(new ProcessView(p, pcb));
    }

    public void blockProcess(ProcessControlBlock pcb) {
        // TODO
    }

}