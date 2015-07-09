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

    private ProcessQueue readyQueue;
    private ProcessQueue waitQueue;
    private ProcessControlBlock currentProcess;
    private int nextPid = 0;

    private HashMap<Integer, ProcessView> processViews = new HashMap<Integer, ProcessView>();

    public void setup() {
        size(displayWidth, displayHeight);
        readyQueue = new ProcessQueue(ProcessState.READY, "Ready Queue", 200, height - 300);
        waitQueue = new ProcessQueue(ProcessState.WAITING, "Wait Queue", 200, height - 100);
    }

    public void draw() {
        background(0);
        drawTitle();
        tickTock();
        drawProcessViews();
        drawQueues();
    }

    public void keyPressed() {
        if (key == ' ') {
            createNewProcess(nextPid++);
        } else if (key == 'b') {
            blockProcess(currentProcess);
        }
    }

    public void mousePressed() {
        for (Map.Entry<Integer, ProcessView> entry : processViews.entrySet()) {
            if (entry.getValue().clicked(mouseX, mouseY)) {
                interruptAndUnblock(entry.getKey().intValue());
            }
        }
    }

    private void drawTitle() {
        fill(50);
        textSize(72);
        textAlign(CENTER);
        text("Press space to create a process.", width / 2, 100);
        text("Press B to cause the executing process to self-block.", width / 2, 180);
        textSize(32);
        textAlign(CENTER);
        text("Click on a blocked process to interrupt and place it back in the ready queue.", width / 2, 230);
    }


    private void tickTock() {
        if (!readyQueue.isEmpty() && roundRobinCycleLimitReached()) {
            switchContext();
        } else if (currentProcess != null) {
            execute(currentProcess);
        }
    }

    private boolean roundRobinCycleLimitReached() {
        return frameCount % ROUND_ROBIN_CYCLE_LIMIT == 0;
    }

    private void createNewProcess(int pid) {
        readyQueue.add(new ProcessControlBlock(pid));
        processViews.put(new Integer(pid), new ProcessView(this));
    }

    private void switchContext() {
        if (currentProcess != null) {
            readyQueue.add(currentProcess);
            currentProcess.state = ProcessState.READY;
        }
        if (!readyQueue.isEmpty()) {
            currentProcess = readyQueue.remove();
            currentProcess.state = ProcessState.RUNNING;
        }
    }

    private void execute(ProcessControlBlock pcb) {
        pcb.programCounter++;
        ProcessView view = processViews.get(new Integer(pcb.pid));
        if (view != null) view.update();
    }

    private void drawProcessViews() {
        for (ProcessView v : processViews.values()) {
            v.draw();
        }
    }

    private void drawQueues() {
        readyQueue.draw(processViews, this);
        waitQueue.draw(processViews, this);
    }

    private void blockProcess(ProcessControlBlock pcb) {
        if (pcb == null) return;
        pcb.state = ProcessState.WAITING;
        waitQueue.add(pcb);
        currentProcess = null;
        switchContext();
        ProcessView view = processViews.get(new Integer(pcb.pid));
        if (view != null) view.dim();
    }

    private void interruptAndUnblock(int pid) {
        ProcessControlBlock waitHead = waitQueue.peek();
        if (waitHead != null && pid == waitHead.pid) {
            ProcessControlBlock pcb = waitQueue.remove();
            pcb.state = ProcessState.READY;
            readyQueue.add(waitHead);
            processViews.get(new Integer(pid)).light();
        }
    }

}