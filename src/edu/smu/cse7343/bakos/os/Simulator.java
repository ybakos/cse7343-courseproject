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
    private OperatingSystem os;
    private OperatingSystemView osView;

    public void setup() {
        size(displayWidth, displayHeight);
        cpu = new CPU();
        cpuView = new CPUView(200, height - 500);
        os = new OperatingSystem();
        osView = new OperatingSystemView(100, height);
    }

    // Invoked automatically and over and over again by Processing. This provides an implicit
    // fetch/execute cycle or tick-tock of the CPU.
    // At the highest level of abstraciton, this is all the whole program really does.
    public void draw() {
        cpu.tickTock();
        // This simulates the execution of the OS, since this simulator is abstracting
        // kernel processes. In a nutshell, this represents the passage of time for the OS.
        os.syscall("wait", frameCount);
        background(0);
        drawTitle();
        cpuView.draw(this, cpu);
        osView.draw(this, os);
    }

    // This really is a true interrupt handler, and I use it to simulate interrupts. Pressing
    // the space bar will spawn a new process, adding it to the ready queue. Pressing the B
    // key will cause the currently executing process to self-block, to "fake" waiting for a
    // resource such as some abstract I/O.
    public void keyPressed() {
        if (key == ' ') {
            os.syscall("exec", 0);
        } else if (key == 'b') {
            os.syscall("block", 0);
        }
    }

    // This really is a true interrupt handler, and I use it to simulate an interrupt that
    // signals a process in the wait queue that its resource is ready and that the process
    // can be put back on the ready queue.
    public void mousePressed() {
        // for (Map.Entry<Integer, ProcessView> entry : processViews.entrySet()) {
        //     if (entry.getValue().isClicked(mouseX, mouseY)) {
        //         interruptAndUnblock(entry.getKey().intValue());
        //     }
        // }
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

}