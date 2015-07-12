/*
    Main.java
    @author Yong Joseph Bakos

    CSE 7343 Course Project, Summer 2015
    OS Process Management & Memory Management Simulation

    This program simulates operating system process management and
    memory management. See the README for full details.

    This class merely bootstraps the instantiation of a Simulator, passing
    it to `PApplet.main`. This `main` class method establishes a graphics
    context for Processing, and calls `Simulator#setup` once, and then calls
    `Simulator#draw` over and over again.
*/

package edu.smu.cse7343.bakos.os;

import processing.core.*;

public class Main {

    private static final String[] OPTIONS = new String[] { "--present", "edu.smu.cse7343.bakos.os.Simulator" };
    
    public static void main(String[] args) {
        PApplet.main(OPTIONS);
    }

}
