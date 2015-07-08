/*
    Simulator.java
    @author Yong Joseph Bakos

    This class represents an abstract main entry point of the simulation, and
    prepares all of the necessary structures and instances. Processing's `PApplet.main`
    function will call `setup`, and then call `draw` over and over again.
*/

package edu.smu.cse7343.bakos.os;

import processing.core.*;
import java.util.*;

public class Simulator extends PApplet {

    private final static int ROUND_ROBIN_CYCLE_LIMIT = 30;

    private ProcessQueue readyQueue = new ProcessQueue();
    private ProcessQueue waitQueue = new ProcessQueue();
    private ProcessControlBlock currentProcess;
    private int nextPid = 0;

    private HashMap<Integer, ProcessView> processViews = new HashMap<Integer, ProcessView>();

    public void setup() {
        size(displayWidth, displayHeight);
    }

    public void draw() {
        background(0);
        drawTitle();
        if (!readyQueue.isEmpty() && frameCount % ROUND_ROBIN_CYCLE_LIMIT == 0) {
            switchContext();
        } else if (currentProcess != null) {
            updateProcessView(new Integer(currentProcess.pid));
        }
        for (ProcessView v : processViews.values()) {
            v.draw();
        }
    }

    private void drawTitle() {
        fill(50);
        textSize(72);
        textAlign(CENTER);
        text("Press space to create a process.", width / 2, 100);
        textSize(32);
        textAlign(CENTER);
        text("A blocked process will stop moving and wait until you click on it.", width / 2, 150);
    }

    public void keyPressed() {
        if (key == ' ') {
            createNewProcess(nextPid++);
        }
    }

    private void createNewProcess(int pid) {
        readyQueue.add(new ProcessControlBlock(pid));
        processViews.put(new Integer(pid), new ProcessView(this));
    }

    private void switchContext() {
        if (currentProcess != null) {
            println("Storing currentProcess at end of ready queue.");
            readyQueue.add(currentProcess);
            currentProcess.state = ProcessState.READY;
        }
        println("Queue head is popped to current.");
        currentProcess = readyQueue.remove();
        currentProcess.state = ProcessState.RUNNING;
    }

    private void updateProcessView(Integer key) {
        ProcessView view = processViews.get(key);
        if (view != null) view.update();
    }

}