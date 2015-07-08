/*
    OperatingSystem.java
    @author Yong Joseph Bakos

    TODO
*/

package edu.smu.cse7343.bakos.os;

public class OperatingSystem {

    PApplet p;

    ProcessQueue readyQueue = new ProcessQueue();
    ProcessQueue waitingQueue = new ProcessQueue();

    public OperatingSystem(PApplet p) {
        this.p = simulator;
    }

}