/*
    OperatingSystemView.java
    @author Yong Joseph Bakos

    This class is responsible for delegating the drawing of PCBs to each process queue view.
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
        this.p = p;
        waitQueueView = new ProcessQueueView(os.waitQueue, "Wait Queue", x, y - 50, p);
        readyQueueView = new ProcessQueueView(os.readyQueue, "Ready Queue", x, y - 225, p);
    }

    public void draw() {
        waitQueueView.draw();
        readyQueueView.draw();
    }

}