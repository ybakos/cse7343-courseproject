/*
    Simulator.java
    @author Yong Joseph Bakos

    This class represents an abstract main entry point of the simulation, and
    prepares all of the necessary structures and instances. Processing's `PApplet.main`
    function will call `setup`, and then call `draw` over and over again.

    For the sake of this simulation, you can consider the repetitive calls to draw a
    complete fetch-execute cycle (or tick-tock) of the CPU.

    This simulation uses a very naive, implicit round-robin scheduler that lets each
    active process execute for 30 cycles before being placed at the back of the ready
    queue.

    The screen itself displays instructions for the user (see README.md) for details.
*/

package edu.smu.cse7343.bakos.os;

import processing.core.*;
import java.util.*;

public class Simulator extends PApplet {

    private CPU cpu;
    private CPUView cpuView;
    private Memory memory;
    private MemoryView memoryView;
    private OperatingSystem os;
    private OperatingSystemView osView;


    public void setup() {
        size(displayWidth, displayHeight);
        cpu = new CPU();
        memory = new Memory(width - 200); // simulated size, per screen width
        os = new OperatingSystem(cpu, memory);
        Program.initialize(this); // program code needs to use handy Processing functions
        memoryView = new MemoryView(memory, 100, height - 800, this);
        cpuView = new CPUView(cpu, 200, height - 500, this);
        osView = new OperatingSystemView(os, 100, height, this);
    }

    // Invoked automatically and over and over again by Processing. This provides an implicit
    // means of simulating a fetch/execute cycle or tick-tock of the CPU.
    // At the highest level of abstraction, this is all the whole simulation really does.
    public void draw() {
        cpu.tickTock();
        os.manageProcesses(); // simulates execution of kernel processes
        background(0);
        drawTitle();
        drawAllocationSelectionMenu();
        memoryView.draw();
        osView.draw();
        cpuView.draw();
    }

    // This really is a true interrupt handler, and I use it to simulate interrupts. Pressing
    // the space bar will spawn a new process, adding it to the ready queue. Pressing the B
    // key will cause the currently executing process to self-block, to "fake" waiting for a
    // resource such as some abstract I/O.
    public void keyPressed() {
        if (key == ' ') {
            os.exec();
        } else if (key == 'b') {
            os.blockCurrentProcess();
        } else if (key == 'k') {
            os.killCurrentProcess();
        }
        if (key == '1') {
            os.allocationAlgorithm = MemoryAllocationAlgorithm.FIRST_FIT;
        } else if (key == '2') {
            os.allocationAlgorithm = MemoryAllocationAlgorithm.BEST_FIT;
        } else if (key == '3') {
            os.allocationAlgorithm = MemoryAllocationAlgorithm.WORST_FIT;
        }
    }

    // This really is a true interrupt handler, and I use it to simulate an interrupt that
    // signals a process in the wait queue that its resource is ready and that the process
    // can be put back on the ready queue.
    public void mousePressed() {
        for (ProcessControlBlock pcb : os.waitQueue.queue) {
            if (pcb.state == ProcessState.WAITING && dist(mouseX, mouseY, pcb.registers[5], pcb.registers[6]) < (pcb.registers[1] / 2) ) {
                os.interruptAndUnblock(pcb);
                break;
            }
        }
    }

    private void drawTitle() {
        fill(50);
        textSize(72);
        textAlign(CENTER);
        text("Press space to create a process.", width / 2, 80);
        textSize(48);
        text("Press B to cause the executing process to self-block.", width / 2, 160);
        text("Press K to kill (terminate) the executing process.", width / 2, 220);
        textSize(32);
        textAlign(CENTER);
        text("Click on a blocked process to interrupt and place it back in the ready queue.", width / 2, 280);
    }

    private void drawAllocationSelectionMenu() {
        textAlign(CENTER);
        textSize(24);
        fill(50);
        text("Memory Allocation Algorithm:", width / 2, height / 2);
        if (os.allocationAlgorithm == MemoryAllocationAlgorithm.FIRST_FIT) fill(200, 255, 200);
        else fill(150);
        text("1. First Fit", width / 2, height / 2 + 40);
        if (os.allocationAlgorithm == MemoryAllocationAlgorithm.BEST_FIT) fill(200, 255, 200);
        else fill(150);
        text("2. Best Fit", width / 2, height / 2 + 80);
        if (os.allocationAlgorithm == MemoryAllocationAlgorithm.WORST_FIT) fill(200, 255, 200);
        else fill(150);
        text("3. Worst Fit", width / 2, height / 2 + 120);
    }

}