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

    private ArrayList<Drawable> drawables = new ArrayList<Drawable>();
    
    CPU cpu;

    public void setup() {
        size(displayWidth, displayHeight);
        cpu = new CPU(this);
        drawables.add(cpu);
    }

    public void draw() {
        background(0);
        for (Drawable d : drawables) {
            d.update();
            d.draw();
        }
    }

}