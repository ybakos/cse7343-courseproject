/*
    Simulator.java
    @author Yong Joseph Bakos

    This class represents an abstract main entry point of the simulation, and
    prepares all of the necessary structures and instances. Processing's `PApplet.main`
    function will call `setup`, and then call `draw` over and over again.
*/

package edu.smu.cse7343.bakos.os;

import processing.core.*;

public class Simulator extends PApplet {
    
    ProcessView pv;

    public void setup() {
        size(displayWidth, displayHeight);
        pv = new ProcessView(this, width / 2, height / 2);
    }

    public void draw() {
        background(0);
        pv.update();
        pv.draw();
    }

}